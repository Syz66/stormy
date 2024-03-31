package dev.stormy.client.utils.render;

import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class Render2DUtils {
    public static void drawBorderedRoundedRect(float x, float y, float x1, float y1, float radius, float borderSize, int borderC, int insideC) {
        drawRoundedRect(x, y, x1, y1, radius, insideC);
        drawRoundedOutline(x, y, x1, y1, radius, borderSize, borderC);
    }

    public static void drawRoundedRect(float x, float y, float x1, float y1, float radius, int color) {
        GL11.glPushAttrib(0);
        GL11.glScaled(0.5, 0.5, 0.5);
        x *= 2.0f;
        y *= 2.0f;
        x1 *= 2.0f;
        y1 *= 2.0f;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GLUtils.setColor(color);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glBegin(GL11.GL_POLYGON);
        round(x, y, x1, y1, radius);
        GL11.glEnd();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glScaled(2.0, 2.0, 2.0);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glPopAttrib();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static void drawRoundedOutline(float x, float y, float x1, float y1, float radius, float borderSize, int color) {
        GL11.glPushAttrib(0);
        GL11.glScaled(0.5, 0.5, 0.5);
        x *= 2.0f;
        y *= 2.0f;
        x1 *= 2.0f;
        y1 *= 2.0f;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GLUtils.setColor(color);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glLineWidth(borderSize);
        GL11.glBegin(GL11.GL_LINE_LOOP);
        round(x, y, x1, y1, radius);
        GL11.glEnd();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glScaled(2.0, 2.0, 2.0);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glPopAttrib();
        GL11.glLineWidth(1.0f);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static void roundHelper(float x, float y, float radius, int pn, int pn2, int originalRotation, int finalRotation) {
        for (int i = originalRotation; i <= finalRotation; i += 3) {
            GL11.glVertex2d(
                    x + (radius * -pn) + (Math.sin((i * Math.PI) / 180.0) * radius * pn),
                    y + (radius * pn2) + (Math.cos((i * Math.PI) / 180.0) * radius * pn)
            );
        }
    }

    public static void round(float x, float y, float x1, float y1, float radius) {
        roundHelper(x, y, radius, -1, 1, 0, 90);
        roundHelper(x, y1, radius, -1, -1, 90, 180);
        roundHelper(x1, y1, radius, 1, -1, 0, 90);
        roundHelper(x1, y, radius, 1, 1, 90, 180);
    }
}