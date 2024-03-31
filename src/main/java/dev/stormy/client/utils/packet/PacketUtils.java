package dev.stormy.client.utils.packet;

import dev.stormy.client.utils.IMethods;
import net.minecraft.network.Packet;
import net.minecraft.network.ThreadQuickExitException;

@SuppressWarnings("unused")
public class PacketUtils implements IMethods {
    public static void Send(Packet packet) {
        mc.getNetHandler().addToSendQueue(packet);
    }

    public static void handle(Packet packet, boolean outgoing) {
        if (outgoing) {
            mc.getNetHandler().netManager.sendPacket(packet);
        } else {
            if (mc.getNetHandler().netManager.channel.isOpen()) {
                try {
                    packet.processPacket(mc.getNetHandler().netManager.packetListener);
                } catch (final ThreadQuickExitException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}
