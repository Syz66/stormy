package dev.stormy.client.clickgui.components;

import dev.stormy.client.clickgui.Component;
import dev.stormy.client.clickgui.Theme;
import dev.stormy.client.module.impl.client.ClickGuiModule;
import dev.stormy.client.utils.client.ComponentUtils;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class BindComponent implements Component {
    private final ModuleComponent parent;
    private boolean binding;
    private int x, y, offset;

    public BindComponent(ModuleComponent module, int offset) {
        this.parent = module;
        this.x = module.category.getX() + module.category.getWidth();
        this.y = module.category.getY() + module.offset;
        this.offset = offset;
    }

    @Override
    public void draw() {
        GL11.glPushMatrix();
        GL11.glScaled(0.5D, 0.5D, 0.5D);

        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(
                this.binding ? "Select a key..." : "Bind: " + Keyboard.getKeyName(this.parent.mod.getBind()),
                (this.parent.category.getX() + 4f) * 2f,
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
        if (ComponentUtils.isHovering(x, y, this.x, this.y, parent) && button == 0 && this.parent.po) {
            this.binding = !this.binding;
        }
    }

    @Override
    public void mouseReleased(int x, int y, int button) {
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (this.binding) {
            if (keyCode == 11) {
                if (this.parent.mod instanceof ClickGuiModule) {
                    this.parent.mod.setBind(28);
                } else {
                    this.parent.mod.setBind(0);
                }
            } else {
                this.parent.mod.setBind(keyCode);
            }

            this.binding = false;
        }

    }

    @Override
    public void setComponentStartAt(int position) {
        this.offset = position;
    }

    @Override
    public int getHeight() {
        return 0;
    }
}
