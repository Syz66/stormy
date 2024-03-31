package dev.stormy.client.module.impl.render;

import dev.stormy.client.clickgui.Theme;
import dev.stormy.client.module.Module;
import dev.stormy.client.module.impl.client.AntiBot;
import dev.stormy.client.module.setting.impl.ComboSetting;
import dev.stormy.client.module.setting.impl.TickSetting;
import dev.stormy.client.utils.player.PlayerUtils;
import dev.stormy.client.utils.render.Render3DUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.weavemc.loader.api.event.RenderWorldEvent;
import net.weavemc.loader.api.event.SubscribeEvent;


public class PlayerESP extends Module {
    public static TickSetting redDmg;
    public static ComboSetting<modes> mode;

    public PlayerESP() {
        super("PlayerESP", ModuleCategory.Render, 0);
        this.registerSetting(mode = new ComboSetting<>("Mode", modes.Shaded));
        this.registerSetting(redDmg = new TickSetting("Red on damage", true));
    }

    @SubscribeEvent
    public void onRenderWorld(RenderWorldEvent ev) {
        if (PlayerUtils.isPlayerInGame()) {
            for (EntityPlayer player : mc.theWorld.playerEntities) {
                if (!AntiBot.bot(player) && !(player instanceof EntityPlayerSP)) {
                    Render3DUtils.drawEntity(
                            player,
                            mode.getMode().ordinal(),
                            Theme.getMainColor().getRGB(),
                            redDmg.isToggled()
                    );
                }
            }
        }
    }

    public enum modes {
        Box, Shaded, Both
    }
}
