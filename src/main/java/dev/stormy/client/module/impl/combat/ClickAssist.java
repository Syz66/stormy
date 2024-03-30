package dev.stormy.client.module.impl.combat;

import dev.stormy.client.module.Module;
import dev.stormy.client.module.setting.impl.*;
import dev.stormy.client.utils.math.MathUtils;
import dev.stormy.client.utils.math.TimerUtils;
import dev.stormy.client.utils.player.PlayerUtils;
import dev.stormy.client.events.UpdateEvent;
import net.minecraft.util.ChatComponentText;
import net.weavemc.loader.api.event.SubscribeEvent;
import net.weavemc.loader.api.event.MouseEvent;

import java.awt.*;

@SuppressWarnings("unused")
public class ClickAssist extends Module {
    public static SliderSetting chance, threshold;
    public static DoubleSliderSetting delay;
    public static ComboSetting<modes> mode;
    private boolean allowClick = false;
    public Robot robot;
    private int cps;

    public TimerUtils timer = new TimerUtils();

    public ClickAssist() {
        super("ClickAssist", ModuleCategory.Combat, 0);
        this.registerSetting(new DescriptionSetting("Chance to double click."));
        this.registerSetting(chance = new SliderSetting("Chance", 50.0D, 0.0D, 100.0D, 1.0D));
        this.registerSetting(delay = new DoubleSliderSetting("Delay", 40, 100, 0, 300, 1));
        this.registerSetting(threshold = new SliderSetting("Threshold", 5.0D, 0.0D, 10.0D, 1.0D));
        this.registerSetting(mode = new ComboSetting<>("Mode", modes.LMB));
    }

    public void onEnable() {
        try {
            this.robot = new Robot();
        } catch (AWTException ex) {
            mc.thePlayer.addChatMessage(new ChatComponentText("An error has occurred: Stacktrace printed to console."));
            this.disable();
            throw new RuntimeException(ex);
        }
    }

    @SubscribeEvent
    public void onClick(MouseEvent e) {
        if (!PlayerUtils.isPlayerInGame()) return;

        int button = 0;

        if (e.getButton() == 0 && (mode.getMode().equals(modes.LMB) || mode.getMode().equals(modes.BOTH))) button = 16;
        if (e.getButton() == 1 && (mode.getMode().equals(modes.RMB) || mode.getMode().equals(modes.BOTH))) button = 4;

        if (button > 0) {
            cps++;
            if (allowClick) {
                double ch = Math.random() * 100;
                if (ch >= chance.getInput()) {
                    return;
                }
            }

            if (cps < threshold.getInput()) return;

            int d = MathUtils.randomInt(delay.getInputMin(), delay.getInputMax());
            if (timer.hasReached(d)) {
                if (allowClick) {
                    this.robot.mouseRelease(button);
                    this.robot.mousePress(button);
                    this.robot.mouseRelease(button);
                    allowClick = false;
                } else {
                    allowClick = true;
                }
                timer.reset();
            }
        }
    }

    @SubscribeEvent
    public void onUpdate(UpdateEvent e) {
        if (timer.hasReached(1000) && cps > 0) {
            cps = 0;
            timer.reset();
        }
    }

    public void onDisable() {
        cps = 0;
        this.robot = null;
    }

    public enum modes {
        LMB, RMB, BOTH
    }
}