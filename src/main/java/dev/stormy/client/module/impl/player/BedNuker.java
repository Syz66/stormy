package dev.stormy.client.module.impl.player;

import dev.stormy.client.clickgui.Theme;
import dev.stormy.client.module.Module;
import dev.stormy.client.module.setting.impl.DescriptionSetting;
import dev.stormy.client.module.setting.impl.SliderSetting;
import dev.stormy.client.module.setting.impl.TickSetting;
import dev.stormy.client.utils.render.Render3DUtils;
import dev.stormy.client.utils.world.DistanceUtils;
import dev.stormy.client.utils.world.WorldUtils;
import dev.stormy.client.events.UpdateEvent;
import net.minecraft.block.BlockBed;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.weavemc.loader.api.event.RenderWorldEvent;
import net.weavemc.loader.api.event.SubscribeEvent;

public class BedNuker extends Module {
    public static SliderSetting range;
    public static TickSetting esp;

    public BlockPos bedPos;
    public boolean breaking;

    public BedNuker() {
        super("BedNuker", ModuleCategory.Player, 0);
        this.registerSetting(new DescriptionSetting("Breaks beds."));
        this.registerSetting(range = new SliderSetting("Range", 4.0D, 3.0D, 6.0D, 1.0D));
        this.registerSetting(esp = new TickSetting("ESP", true));
    }

    @SubscribeEvent
    public void onUpdate(UpdateEvent ev) {
        if (mc.thePlayer != null) {
            if (!breaking) {
                bedPos = WorldUtils.findNearestBedPos(mc.thePlayer.getPosition(), (int) range.getInput());
                breaking = true;
            } else if (bedPos != null) {
                if (DistanceUtils.distanceToBlockPos(bedPos) > range.getInput()) {
                    breaking = false;
                } else if (!(mc.theWorld.getBlockState(bedPos).getBlock() instanceof BlockBed)) {
                    mc.playerController.onPlayerDamageBlock(bedPos, EnumFacing.NORTH);
                    mc.thePlayer.swingItem();
                } else {
                    breaking = false;
                }
            } else {
                breaking = false;
            }
        }
    }

    @SubscribeEvent
    public void onRenderWorld(RenderWorldEvent ev) {
        // TODO: Highlight entire bed.
        Render3DUtils.drawBlockPos(bedPos, 1, Theme.getMainColor().getRGB());
    }
}