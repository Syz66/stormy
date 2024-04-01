package dev.stormy.client.module.impl.render;

import dev.stormy.client.clickgui.Theme;
import dev.stormy.client.module.api.Category;
import dev.stormy.client.module.api.Module;
import dev.stormy.client.module.setting.impl.ComboSetting;
import dev.stormy.client.module.setting.impl.TickSetting;
import dev.stormy.client.utils.player.PlayerUtils;
import dev.stormy.client.utils.render.Render3DUtils;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.weavemc.loader.api.event.RenderWorldEvent;
import net.weavemc.loader.api.event.SubscribeEvent;

public class ChestESP extends Module {
    public static ComboSetting<modes> mode;
    public static TickSetting chest, ender;

    public ChestESP() {
        super("ChestESP", Category.Render, 0);
        this.registerSetting(mode = new ComboSetting<>("Mode", modes.Shaded));
        this.registerSetting(chest = new TickSetting("Chest", true));
        this.registerSetting(ender = new TickSetting("Ender Chest", true));
    }

    @SubscribeEvent
    public void onRenderWorld(RenderWorldEvent ev) {
        if (PlayerUtils.isPlayerInGame()) {
            for (TileEntity te : mc.theWorld.loadedTileEntityList) {
                if (!chest.isToggled() && te instanceof TileEntityChest) continue;
                if (!ender.isToggled() && te instanceof TileEntityEnderChest) continue;

                if (te instanceof TileEntityChest || te instanceof TileEntityEnderChest) {
                    Render3DUtils.drawBlockPos(te.getPos(), mode.getMode().ordinal(), Theme.getMainColor().getRGB());
                }
            }
        }
    }

    public enum modes {
        Box, Shaded, Both
    }
}
