package dev.stormy.client.module.impl.movement;

import dev.stormy.client.clickgui.ClickGui;
import dev.stormy.client.module.api.Category;
import dev.stormy.client.module.api.Module;
import dev.stormy.client.module.setting.impl.SliderSetting;
import dev.stormy.client.module.setting.impl.TickSetting;
import dev.stormy.client.utils.player.PlayerUtils;
import net.weavemc.loader.api.event.SubscribeEvent;
import net.weavemc.loader.api.event.TickEvent;

public class Timer extends Module {
    public static SliderSetting speed;
    public static TickSetting strafe;

    public Timer() {
        super("Timer", Category.Movement, 0);
        this.registerSetting(speed = new SliderSetting("Speed", 1.0D, 0.5D, 2.5D, 0.01D));
        this.registerSetting(strafe = new TickSetting("Strafe only", false));
    }

    @SubscribeEvent
    public void onTick(TickEvent e) {
        if (PlayerUtils.isPlayerInGame()) {
            if (strafe.isToggled() && mc.thePlayer.moveStrafing == 0.0F) {
                mc.timer.timerSpeed = 1.0f;
                return;
            }
            mc.timer.timerSpeed = (float) speed.getInput();
        }
    }

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1.0f;
    }
}
