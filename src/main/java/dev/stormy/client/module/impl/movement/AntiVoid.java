package dev.stormy.client.module.impl.movement;

import dev.stormy.client.module.api.Category;
import dev.stormy.client.module.api.Module;
import dev.stormy.client.module.setting.impl.ComboSetting;
import dev.stormy.client.module.setting.impl.DescriptionSetting;
import dev.stormy.client.module.setting.impl.SliderSetting;
import dev.stormy.client.module.setting.impl.TickSetting;
import dev.stormy.client.utils.player.PlayerUtils;
import net.weavemc.loader.api.event.SubscribeEvent;
import net.weavemc.loader.api.event.TickEvent;

public class AntiVoid extends Module {

    public static ComboSetting<modes> mode;
    public static TickSetting AutoDisable;
    public final SliderSetting fallDist;

    public AntiVoid() {
        super("AntiVoid", Category.Movement, 0);
        this.registerSetting(new DescriptionSetting("Prevents falling in the void."));
        this.registerSetting(mode = new ComboSetting<>("Mode", modes.NCP));
        this.registerSetting(fallDist = new SliderSetting("Fall Distance", 5.0D, 1.0D, 40.0D, 1.0D));
        this.registerSetting(AutoDisable = new TickSetting("AutoDisable", true));
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public void antiVoid(TickEvent e) {
        if (PlayerUtils.isPlayerInGame() && mode.getMode() == modes.NCP) {
            if (mc.thePlayer.fallDistance >= fallDist.getInput()) {
                mc.thePlayer.motionY = 0;
                mc.thePlayer.fallDistance = 0;
                if (AutoDisable.isToggled()) {
                    this.toggle();
                }
            }
        }
        if (PlayerUtils.isPlayerInGame() && mode.getMode() == modes.Karhu) {
            if (mc.thePlayer.fallDistance > fallDist.getInput()) {
                mc.thePlayer.motionY = -0.09800000190735147;
            }
            if (AutoDisable.isToggled()) {
                this.toggle();
            }
        }
    }

    public enum modes {
        NCP, Karhu
    }
}