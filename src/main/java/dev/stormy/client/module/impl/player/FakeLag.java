package dev.stormy.client.module.impl.player;

import dev.stormy.client.module.Module;
import dev.stormy.client.module.setting.impl.DescriptionSetting;
import dev.stormy.client.module.setting.impl.SliderSetting;
import dev.stormy.client.utils.packet.TimedPacket;

import java.util.concurrent.ConcurrentLinkedQueue;

// TODO: Add logic
public class FakeLag extends Module {
    public static SliderSetting spoofms;

    public final ConcurrentLinkedQueue<TimedPacket> incomingPackets = new ConcurrentLinkedQueue<>();
    public final ConcurrentLinkedQueue<TimedPacket> outgoingPackets = new ConcurrentLinkedQueue<>();

    public FakeLag() {
        super("FakeLag", ModuleCategory.Player, 0);
        this.registerSetting(new DescriptionSetting("Delays packets."));
        this.registerSetting(spoofms = new SliderSetting("Ping in ms", 80.0, 30.0, 1000.0, 5.0));
    }
}
