package dev.stormy.client.utils.world;

import dev.stormy.client.utils.IMethods;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

public class WorldUtils implements IMethods {
    public static BlockPos findNearestBedPos(BlockPos pos, int radius) {
        BlockPos nearestBedPos = null;
        double nearestDistanceSq = Double.MAX_VALUE;

        int minX = pos.getX() - radius;
        int minY = Math.max(0, pos.getY() - radius);
        int minZ = pos.getZ() - radius;
        int maxX = pos.getX() + radius;
        int maxY = Math.min(mc.theWorld.getHeight(), pos.getY() + radius);
        int maxZ = pos.getZ() + radius;

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    BlockPos currentPos = new BlockPos(x, y, z);
                    IBlockState blockState = mc.theWorld.getBlockState(currentPos);
                    Block block = blockState.getBlock();
                    if (block == Blocks.bed) {
                        double distanceSq = pos.distanceSq(currentPos);
                        if (distanceSq < nearestDistanceSq) {
                            nearestBedPos = currentPos;
                            nearestDistanceSq = distanceSq;
                        }
                    }
                }
            }
        }

        return nearestBedPos;
    }
}