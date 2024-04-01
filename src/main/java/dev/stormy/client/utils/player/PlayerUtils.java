package dev.stormy.client.utils.player;

import dev.stormy.client.utils.IMethods;
import dev.stormy.client.utils.client.ClientUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemSword;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;

/**
 * @author sassan
 * 23.11.2023, 2023
 */
public class PlayerUtils implements IMethods {
    public static boolean isPlayerInGame() {
        return mc.thePlayer != null && mc.theWorld != null;
    }

    public static boolean isPlayerMoving() {
        return mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F;
    }

    public static boolean lookingAtPlayer(EntityPlayer viewer, EntityPlayer targetPlayer, double maxDistance) {
        double deltaX = targetPlayer.posX - viewer.posX;
        double deltaY = targetPlayer.posY - viewer.posY + viewer.getEyeHeight();
        double deltaZ = targetPlayer.posZ - viewer.posZ;
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
        return distance < maxDistance;
    }

    public static boolean isPlayerHoldingWeapon() {
        if (mc.thePlayer.getCurrentEquippedItem() == null) {
            return false;
        } else {
            Item item = mc.thePlayer.getCurrentEquippedItem().getItem();
            return item instanceof ItemSword || item instanceof ItemAxe;
        }
    }
}
