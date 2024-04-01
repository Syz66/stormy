package dev.stormy.client.clickgui.components;

import dev.stormy.client.clickgui.Component;
import dev.stormy.client.clickgui.Theme;
import dev.stormy.client.module.setting.impl.DescriptionSetting;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class DescriptionComponent implements Component {
    private final DescriptionSetting desc;
    private final ModuleComponent parent;
    private int offset;

    public DescriptionComponent(DescriptionSetting desc, ModuleComponent parent, int offset) {
        this.desc = desc;
        this.parent = parent;
        this.offset = offset;
    }

    @Override
    public void draw() {
        GL11.glPushMatrix();
        GL11.glScaled(0.5D, 0.5D, 0.5D);

        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(
                this.desc.getDesc(),
                (this.parent.category.getX() + 4f) * 2f,
                (this.parent.category.getY() + this.offset + 3f) * 2f,
                Theme.getMainColor().getRGB()
        );

        GL11.glPopMatrix();
    }

    @Override
    public void update(int mousePosX, int mousePosY) {
    }

    @Override
    public void mouseDown(int x, int y, int button) {
    }

    @Override
    public void mouseReleased(int x, int y, int button) {
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
    }

    @Override
    public void setComponentStartAt(int position) {
        this.offset = position;
    }

    @Override
    public int getHeight() {
        return 12;
    }
}
