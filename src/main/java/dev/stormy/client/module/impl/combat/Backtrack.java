package dev.stormy.client.module.impl.combat;

import dev.stormy.client.module.Module;
import dev.stormy.client.module.setting.impl.DescriptionSetting;
import dev.stormy.client.module.setting.impl.SliderSetting;
import dev.stormy.client.module.setting.impl.TickSetting;
import dev.stormy.client.utils.packet.PacketUtils;
import dev.stormy.client.utils.packet.TimedPacket;
import dev.stormy.client.utils.player.PlayerUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.weavemc.loader.api.event.PacketEvent;
import net.weavemc.loader.api.event.RenderHandEvent;
import net.weavemc.loader.api.event.SubscribeEvent;
import net.weavemc.loader.api.event.TickEvent;

import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

@SuppressWarnings("unused")
// TODO: Rewrite
public class Backtrack extends Module {
    public static final ConcurrentLinkedQueue<TimedPacket> incomingPackets = new ConcurrentLinkedQueue<>();
    public static SliderSetting spoofms, range;
    public static TickSetting useRange;
    private Optional<EntityPlayer> target = Optional.empty();

    public Backtrack() {
        super("Backtrack", ModuleCategory.Combat, 0);
        this.registerSetting(new DescriptionSetting("Delays inbound packets"));
        this.registerSetting(spoofms = new SliderSetting("Ping in ms", 50.0, 0.0, 500.0, 5.0));
        this.registerSetting(range = new SliderSetting("Range", 5.0, 0.0, 7.0, 0.1));
        this.registerSetting(useRange = new TickSetting("Use Range", false));
    }

    @SubscribeEvent
    public void onTick(TickEvent.Pre ev) {
        if (PlayerUtils.isPlayerInGame()) {
            target = mc.theWorld != null
                    ? mc.theWorld.playerEntities.stream()
                    .filter(player -> player.getEntityId() != mc.thePlayer.getEntityId() &&
                            player.getDistanceToEntity(mc.thePlayer) <= range.getInput())
                    .findFirst() : Optional.empty();
        } else {
            this.disable();
        }
    }

    @SubscribeEvent
    public void onPacket(PacketEvent.Receive ev) {
        if (PlayerUtils.isPlayerInGame() && target.isPresent()) {
            try {
                Packet<?> packet = ev.getPacket();
                incomingPackets.add(new TimedPacket(packet, System.currentTimeMillis()));
                ev.setCancelled(true);
            } catch (Exception ignored) {
            }
        }
    }

    @SubscribeEvent
    public void onRenderHand(RenderHandEvent ev) {
        if (!PlayerUtils.isPlayerInGame()) return;
        final long time = System.currentTimeMillis();
        incomingPackets.removeIf(timedPacket -> {
            try {
                if (time - timedPacket.time() >= getSpoofMS()) {
                    PacketUtils.handle(timedPacket.packet(), false);
                    return true;
                }
                return false;
            } catch (Exception ignored) {
            }
            return false;
        });
    }

    public int getSpoofMS() {
        if (target.isPresent()) return (int) spoofms.getInput();
        else return 0;
    }

    @Override
    public void onEnable() {
        incomingPackets.clear();
    }

    @Override
    public void onDisable() {
        if (PlayerUtils.isPlayerInGame()) {
            try {
                incomingPackets.removeIf(timedPacket -> {
                    PacketUtils.handle(timedPacket.packet(), false);
                    return true;
                });
            } catch (Exception ignored) {
            }
        }
        incomingPackets.clear();
    }
}