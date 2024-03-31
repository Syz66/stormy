package dev.stormy.client.utils.player;

import net.minecraft.block.*;
import net.minecraft.init.Items;
import net.minecraft.item.*;

import java.util.Arrays;
import java.util.List;

public class ItemUtils {
    private static final List<Item> WHITELISTED_ITEMS = Arrays.asList(Items.fishing_rod, Items.water_bucket, Items.bucket, Items.arrow, Items.bow, Items.snowball, Items.egg, Items.ender_pearl);

    public static boolean isBad(final ItemStack stack) {
        final Item item = stack.getItem();

        if (item instanceof ItemPotion potion) {
            return !ItemPotion.isSplash(stack.getMetadata()) || !PotionUtils.goodPotion(potion.getEffects(stack).get(0).getPotionID());
        }

        if (item instanceof ItemBlock) {
            final Block block = ((ItemBlock) item).getBlock();
            if (block instanceof BlockGlass || block instanceof BlockStainedGlass || (block.isFullBlock() && !(block instanceof BlockTNT || block instanceof BlockSlime || block instanceof BlockFalling))) {
                return false;
            }
        }

        return !(item instanceof ItemSword) &&
                !(item instanceof ItemTool) &&
                !(item instanceof ItemArmor) &&
                !(item instanceof ItemFood) &&
                !WHITELISTED_ITEMS.contains(item);
    }
}