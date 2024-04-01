package dev.stormy.client.utils.render;

import dev.stormy.client.utils.IMethods;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class Render3DUtils implements IMethods {
    /**
     * Draws ESP on a block.
     *
     * @param pos the target block.
     * @param type the type of ESP.
     *             0 outline, 1 shaded, 2 both
     * @param color the color.
     * @author Syz66
     */
    public static void drawBlockPos(final BlockPos pos, final int type, final int color) {
        if (pos != null) {
            double x = pos.getX() - mc.getRenderManager().viewerPosX;
            double y = pos.getY() - mc.getRenderManager().viewerPosY;
            double z = pos.getZ() - mc.getRenderManager().viewerPosZ;

            AxisAlignedBB aabb = new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D);

            drawBoundingBox(aabb, type, color);
        }
    }

    /**
     * Draws ESP on 2 block positions. (Made for beds)
     *
     * @param pos the target block.
     * @param pos2 the second target block.
     * @param type the type of ESP.
     *             0 outline, 1 shaded, 2 both
     * @param color the color.
     * @author Syz66
     */
    public static void drawBlockPoses(final BlockPos pos, final BlockPos pos2, final int type, final int color) {
        if (pos != null && pos2 != null) {
            double minX = Math.min(pos.getX(), pos2.getX()) - mc.getRenderManager().viewerPosX;
            double minY = Math.min(pos.getY(), pos2.getY()) - mc.getRenderManager().viewerPosY;
            double minZ = Math.min(pos.getZ(), pos2.getZ()) - mc.getRenderManager().viewerPosZ;

            double maxX = Math.max(pos.getX(), pos2.getX()) + 1.0D - mc.getRenderManager().viewerPosX;
            double maxY = Math.max(pos.getY(), pos2.getY()) + 1.0D - mc.getRenderManager().viewerPosY;
            double maxZ = Math.max(pos.getZ(), pos2.getZ()) + 1.0D - mc.getRenderManager().viewerPosZ;

            AxisAlignedBB aabb = new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);

            drawBoundingBox(aabb, type, color);
        }
    }


    /**
     * Draws ESP on an entity.
     *
     * @param en the target entity.
     * @param type the type of ESP.
     *             0 outline, 1 shaded, 2 both
     * @param color the color.
     * @author Syz66
     */
    public static void drawEntity(final Entity en, final int type, int color, final boolean damage) {
        if (en instanceof EntityLivingBase living) {
            double x = en.lastTickPosX + (en.posX - en.lastTickPosX) * mc.timer.renderPartialTicks - mc.getRenderManager().viewerPosX;
            double y = en.lastTickPosY + (en.posY - en.lastTickPosY) * mc.timer.renderPartialTicks - mc.getRenderManager().viewerPosY;
            double z = en.lastTickPosZ + (en.posZ - en.lastTickPosZ) * mc.timer.renderPartialTicks - mc.getRenderManager().viewerPosZ;

            AxisAlignedBB aabb = new AxisAlignedBB(x - 0.4D, y, z - 0.4D, x + 0.4D, y + 1.9D, z + 0.4D);

            if (damage && living.hurtTime != 0) color = Color.RED.getRGB();

            drawBoundingBox(aabb, type, color);
        }
    }

    /**
     * Draws ESP on a bounding box.
     *
     * @param aabb the target bounding box.
     * @param type the type of ESP.
     *             0 outline, 1 shaded, 2 both
     * @param color the color.
     * @author Syz66
     */
    public static void drawBoundingBox(final AxisAlignedBB aabb, final int type, final int color) {
        GL11.glPushMatrix();
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);

        if (type == 0) {
            outlineBoundingBox(aabb, color);
        } else if (type == 1) {
            shadeBoundingBox(aabb, color);
        } else if (type == 2) {
            outlineBoundingBox(aabb, color);
            shadeBoundingBox(aabb, color);
        }

        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    /**
     * Draws a shaded box on a bounding box.
     *
     * @param aabb the target bounding box.
     * @param color the color.
     * @author Syz66
     */
    public static void shadeBoundingBox(final AxisAlignedBB aabb, final int color) {
        GL11.glBegin(GL11.GL_QUADS);

        GLUtils.setColorTransparent(color);

        GL11.glVertex3d(aabb.minX, aabb.minY, aabb.minZ);
        GL11.glVertex3d(aabb.minX, aabb.maxY, aabb.minZ);
        GL11.glVertex3d(aabb.maxX, aabb.minY, aabb.minZ);
        GL11.glVertex3d(aabb.maxX, aabb.maxY, aabb.minZ);

        GL11.glVertex3d(aabb.minX, aabb.minY, aabb.maxZ);
        GL11.glVertex3d(aabb.minX, aabb.maxY, aabb.maxZ);
        GL11.glVertex3d(aabb.maxX, aabb.minY, aabb.maxZ);
        GL11.glVertex3d(aabb.maxX, aabb.maxY, aabb.maxZ);

        GL11.glVertex3d(aabb.minX, aabb.maxY, aabb.minZ);
        GL11.glVertex3d(aabb.minX, aabb.minY, aabb.minZ);
        GL11.glVertex3d(aabb.maxX, aabb.maxY, aabb.minZ);
        GL11.glVertex3d(aabb.maxX, aabb.minY, aabb.minZ);

        GL11.glVertex3d(aabb.minX, aabb.maxY, aabb.maxZ);
        GL11.glVertex3d(aabb.minX, aabb.minY, aabb.maxZ);
        GL11.glVertex3d(aabb.maxX, aabb.maxY, aabb.maxZ);
        GL11.glVertex3d(aabb.maxX, aabb.minY, aabb.maxZ);

        GL11.glVertex3d(aabb.minX, aabb.minY, aabb.minZ);
        GL11.glVertex3d(aabb.minX, aabb.maxY, aabb.minZ);
        GL11.glVertex3d(aabb.minX, aabb.minY, aabb.maxZ);
        GL11.glVertex3d(aabb.minX, aabb.maxY, aabb.maxZ);

        GL11.glVertex3d(aabb.maxX, aabb.minY, aabb.minZ);
        GL11.glVertex3d(aabb.maxX, aabb.maxY, aabb.minZ);
        GL11.glVertex3d(aabb.maxX, aabb.minY, aabb.maxZ);
        GL11.glVertex3d(aabb.maxX, aabb.maxY, aabb.maxZ);

        GL11.glVertex3d(aabb.minX, aabb.maxY, aabb.minZ);
        GL11.glVertex3d(aabb.minX, aabb.minY, aabb.minZ);
        GL11.glVertex3d(aabb.minX, aabb.maxY, aabb.maxZ);
        GL11.glVertex3d(aabb.minX, aabb.minY, aabb.maxZ);

        GL11.glVertex3d(aabb.maxX, aabb.maxY, aabb.minZ);
        GL11.glVertex3d(aabb.maxX, aabb.minY, aabb.minZ);
        GL11.glVertex3d(aabb.maxX, aabb.maxY, aabb.maxZ);
        GL11.glVertex3d(aabb.maxX, aabb.minY, aabb.maxZ);

        GL11.glVertex3d(aabb.minX, aabb.minY, aabb.minZ);
        GL11.glVertex3d(aabb.maxX, aabb.minY, aabb.minZ);
        GL11.glVertex3d(aabb.maxX, aabb.minY, aabb.maxZ);
        GL11.glVertex3d(aabb.minX, aabb.minY, aabb.maxZ);

        GL11.glVertex3d(aabb.maxX, aabb.minY, aabb.minZ);
        GL11.glVertex3d(aabb.minX, aabb.minY, aabb.minZ);
        GL11.glVertex3d(aabb.minX, aabb.minY, aabb.maxZ);
        GL11.glVertex3d(aabb.maxX, aabb.minY, aabb.maxZ);

        GL11.glVertex3d(aabb.minX, aabb.maxY, aabb.minZ);
        GL11.glVertex3d(aabb.maxX, aabb.maxY, aabb.minZ);
        GL11.glVertex3d(aabb.maxX, aabb.maxY, aabb.maxZ);
        GL11.glVertex3d(aabb.minX, aabb.maxY, aabb.maxZ);

        GL11.glVertex3d(aabb.maxX, aabb.maxY, aabb.minZ);
        GL11.glVertex3d(aabb.minX, aabb.maxY, aabb.minZ);
        GL11.glVertex3d(aabb.minX, aabb.maxY, aabb.maxZ);
        GL11.glVertex3d(aabb.maxX, aabb.maxY, aabb.maxZ);

        GLUtils.resetColor();

        GL11.glEnd();
    }

    /**
     * Draws a box on a bounding box.
     *
     * @param aabb the target bounding box.
     * @param color the color.
     * @author Syz66
     */
    public static void outlineBoundingBox(final AxisAlignedBB aabb, final int color) {
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glBegin(GL11.GL_LINES);

        GLUtils.setColor(color);

        GL11.glVertex3d(aabb.minX, aabb.minY, aabb.minZ);
        GL11.glVertex3d(aabb.maxX, aabb.minY, aabb.minZ);
        GL11.glVertex3d(aabb.maxX, aabb.minY, aabb.minZ);
        GL11.glVertex3d(aabb.maxX, aabb.minY, aabb.maxZ);
        GL11.glVertex3d(aabb.maxX, aabb.minY, aabb.maxZ);
        GL11.glVertex3d(aabb.minX, aabb.minY, aabb.maxZ);
        GL11.glVertex3d(aabb.minX, aabb.minY, aabb.maxZ);
        GL11.glVertex3d(aabb.minX, aabb.minY, aabb.minZ);

        GL11.glVertex3d(aabb.minX, aabb.maxY, aabb.minZ);
        GL11.glVertex3d(aabb.maxX, aabb.maxY, aabb.minZ);
        GL11.glVertex3d(aabb.maxX, aabb.maxY, aabb.minZ);
        GL11.glVertex3d(aabb.maxX, aabb.maxY, aabb.maxZ);
        GL11.glVertex3d(aabb.maxX, aabb.maxY, aabb.maxZ);
        GL11.glVertex3d(aabb.minX, aabb.maxY, aabb.maxZ);
        GL11.glVertex3d(aabb.minX, aabb.maxY, aabb.maxZ);
        GL11.glVertex3d(aabb.minX, aabb.maxY, aabb.minZ);

        GL11.glVertex3d(aabb.minX, aabb.minY, aabb.minZ);
        GL11.glVertex3d(aabb.minX, aabb.maxY, aabb.minZ);

        GL11.glVertex3d(aabb.maxX, aabb.minY, aabb.minZ);
        GL11.glVertex3d(aabb.maxX, aabb.maxY, aabb.minZ);

        GL11.glVertex3d(aabb.maxX, aabb.minY, aabb.maxZ);
        GL11.glVertex3d(aabb.maxX, aabb.maxY, aabb.maxZ);

        GL11.glVertex3d(aabb.minX, aabb.minY, aabb.maxZ);
        GL11.glVertex3d(aabb.minX, aabb.maxY, aabb.maxZ);

        GLUtils.resetColor();

        GL11.glEnd();
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
    }
}
