package dev.stormy.client.utils.world;

import dev.stormy.client.utils.IMethods;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class DistanceUtils implements IMethods {
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
