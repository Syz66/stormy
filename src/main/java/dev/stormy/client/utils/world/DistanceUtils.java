package dev.stormy.client.utils.world;

import dev.stormy.client.utils.IMethods;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class DistanceUtils implements IMethods {
    /**
     * Calculates the distance to the entity.
     *
     * @param entity the target entity.
     * @return the distance to the entity.
     * @author AriaJackie/Fractal
     */
    public static double distanceToEntity(final EntityPlayer entity) {
        double offsetX = entity.posX - mc.thePlayer.posX;
        double offsetZ = entity.posZ - mc.thePlayer.posZ;

        return MathHelper.sqrt_double(offsetX * offsetX + offsetZ * offsetZ);
    }

    /**
     * Calculates the distance to the specified positions.
     *
     * @param posX the target posX.
     * @param posZ the target posZ.
     * @return the distance to the positions.
     * @author AriaJackie/Fractal
     */
    public static double distanceToPoses(final double posX, final double posZ) {
        double offsetX = posX - mc.thePlayer.posX;
        double offsetZ = posZ - mc.thePlayer.posZ;

        return MathHelper.sqrt_double(offsetX * offsetX + offsetZ * offsetZ);
    }

    /**
     * Calculates the distance to the block position.
     *
     * @param pos the target block position.
     * @return the distance to the block position.
     * @author Syz66
     */
    public static double distanceToBlockPos(BlockPos pos) {
        double x = mc.thePlayer.posX - pos.getX();
        double y = mc.thePlayer.posY - pos.getY();
        double z = mc.thePlayer.posZ - pos.getZ();
        return MathHelper.sqrt_double(x * x + y * y + z * z);
    }
}
