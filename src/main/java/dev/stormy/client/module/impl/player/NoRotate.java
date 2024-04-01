package dev.stormy.client.module.impl.player;

import dev.stormy.client.module.api.Category;
import dev.stormy.client.module.api.Module;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.weavemc.loader.api.event.PacketEvent;
import net.weavemc.loader.api.event.SubscribeEvent;

public class NoRotate extends Module {
    public NoRotate() {
        super("NoRotate", Category.Player, 0);
    }

    @SubscribeEvent
    public void onPacket(PacketEvent.Receive event) {
        final Packet<?> packet = event.getPacket();
        if (packet instanceof S08PacketPlayerPosLook s08) {
            s08.yaw = mc.thePlayer.rotationYaw;
            s08.pitch = mc.thePlayer.rotationPitch;
        }
    }
}
