package dev.stormy.client.module.impl.player;

import dev.stormy.client.module.api.Category;
import dev.stormy.client.module.api.Module;
import dev.stormy.client.module.setting.impl.DoubleSliderSetting;
import dev.stormy.client.module.setting.impl.TickSetting;

// TODO: Rewrite
public class SafeWalk extends Module {
    public static TickSetting blocksOnly, shiftOnJump;
    public static TickSetting onHold, lookDown;
    public static DoubleSliderSetting shiftTime;

    public SafeWalk() {
        super("SafeWalk", Category.Player, 0);
        this.registerSetting(shiftOnJump = new TickSetting("Shift during jumps", false));
        this.registerSetting(shiftTime = new DoubleSliderSetting("Shift time: (s)", 140, 200, 0, 280, 5));
        this.registerSetting(onHold = new TickSetting("On shift hold", false));
        this.registerSetting(blocksOnly = new TickSetting("Blocks only", true));
        this.registerSetting(lookDown = new TickSetting("Only when looking down", true));
    }
}