package dev.stormy.client.module.impl.combat;

import dev.stormy.client.events.PacketEvent;
import dev.stormy.client.mixins.IS12PacketEntityVelocity;
import dev.stormy.client.module.api.Category;
import dev.stormy.client.module.api.Module;
import dev.stormy.client.module.setting.impl.ComboSetting;
import dev.stormy.client.module.setting.impl.SliderSetting;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.weavemc.loader.api.event.SubscribeEvent;

@SuppressWarnings("unused")
public class Velocity extends Module {
    public static SliderSetting horizontal, vertical, chance;
    public static ComboSetting<modes> mode;

    public Velocity() {
        super("Velocity", Category.Combat, 0);
        this.registerSetting(horizontal = new SliderSetting("Horizontal", 90.0D, 0.0D, 200.0D, 1.0D));
        this.registerSetting(vertical = new SliderSetting("Vertical", 100.0D, 0.0D, 200.0D, 1.0D));
        this.registerSetting(chance = new SliderSetting("Chance", 100.0D, 0.0D, 100.0D, 1.0D));
        this.registerSetting(mode = new ComboSetting<>("Mode", modes.Normal));
    }

    @SubscribeEvent
    public void onPacket(PacketEvent e) {
        if (mc.thePlayer == null) return;
        if (mode.getMode() == modes.Minemen && mc.thePlayer.onGround) return;
        if (e.getPacket() instanceof S12PacketEntityVelocity s12) {
            if (Math.random() * 100 >= chance.getInput()) return;

            if (mode.getMode() == modes.Cancel) e.setCancelled(true);

            if (s12.getEntityID() == mc.thePlayer.entityId) {
                IS12PacketEntityVelocity packet = (IS12PacketEntityVelocity) s12;
                packet.setMotionX((int) (s12.getMotionX() * horizontal.getInput() / 100));
                packet.setMotionZ((int) (s12.getMotionZ() * horizontal.getInput() / 100));
                packet.setMotionY((int) (s12.getMotionY() * vertical.getInput() / 100));
                e.setPacket(packet);
            }
        }
    }

    public enum modes {
        Normal, Cancel, Minemen
    }
}