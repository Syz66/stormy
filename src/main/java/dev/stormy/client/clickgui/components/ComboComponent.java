package dev.stormy.client.clickgui.components;

import dev.stormy.client.module.setting.impl.ComboSetting;
import dev.stormy.client.utils.client.ComponentUtils;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import dev.stormy.client.clickgui.Component;
import dev.stormy.client.clickgui.Theme;

public class ComboComponent implements Component {
    private final ComboSetting<?> combo;
    private final ModuleComponent parent;
    private int x, y, offset;

    public ComboComponent(ComboSetting<?> combo, ModuleComponent parent, int offset) {
        this.combo = combo;
        this.parent = parent;
        this.x = parent.category.getX() + parent.category.getWidth();
        this.y = parent.category.getY() + parent.offset;
        this.offset = offset;
    }

    @Override
    public void draw() {
        GL11.glPushMatrix();
        GL11.glScaled(0.5D, 0.5D, 0.5D);

        float width = Minecraft.getMinecraft().fontRendererObj.getStringWidth(this.combo.getName() + ": ") / 2f;

        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(
                this.combo.getName() + ": ",
                (this.parent.category.getX() + 4f) * 2f,
                (this.parent.category.getY() + this.offset + 3f) * 2f,
                0xffffffff
        );

        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(
                String.valueOf(this.combo.getMode()),
                (this.parent.category.getX() + 4f + width) * 2f,
                (this.parent.category.getY() + this.offset + 3f) * 2f,
                Theme.getMainColor().getRGB()
        );

        GL11.glPopMatrix();
    }

    @Override
    public void update(int mousePosX, int mousePosY) {
        this.y = this.parent.category.getY() + this.offset;
        this.x = this.parent.category.getX();
    }

    @Override
    public void mouseDown(int x, int y, int button) {
        if(ComponentUtils.isHovering(x, y, this.x, this.y, this.parent)) {
            if (button == 0) {
                this.combo.nextMode();
            } else if (button == 1) {
                this.combo.prevMode();
            }
        }
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