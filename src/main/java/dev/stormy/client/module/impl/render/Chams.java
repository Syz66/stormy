package dev.stormy.client.module.impl.render;

import dev.stormy.client.clickgui.Theme;
import dev.stormy.client.module.api.Category;
import dev.stormy.client.module.api.Module;
import dev.stormy.client.module.setting.impl.ComboSetting;
import dev.stormy.client.module.setting.impl.DescriptionSetting;
import dev.stormy.client.utils.render.GLUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.weavemc.loader.api.event.RenderLivingEvent;
import net.weavemc.loader.api.event.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class Chams extends Module {
    public static ComboSetting<Modes> mode;

    public Chams() {
        super("Chams", Category.Render, 0);
        this.registerSetting(new DescriptionSetting("Show players through walls."));
        this.registerSetting(mode = new ComboSetting<>("Mode", Modes.Normal));
    }

    @SubscribeEvent
    public void onPreLivingRender(RenderLivingEvent.Pre e) {
        if (e.getEntity() instanceof EntityPlayer) {
            GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
            GL11.glPolygonOffset(1.0F, -1100000.0F);

            if (mode.getMode().equals(Modes.Colored)) {
                GLUtils.setColor(Theme.getMainColor().getRGB());
            }
        }
    }

    @SubscribeEvent
    public void onPostLivingRender(RenderLivingEvent.Post e) {
        if (e.getEntity() instanceof EntityPlayer) {
            GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
            GL11.glPolygonOffset(1.0F, 1100000.0F);

            if (mode.getMode().equals(Modes.Colored)) {
                GLUtils.resetColor();
            }
        }
    }

    public enum Modes {
        Normal, Colored
    }
}
