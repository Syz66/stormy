package dev.stormy.client.module.impl.player;

import dev.stormy.client.clickgui.Theme;
import dev.stormy.client.events.UpdateEvent;
import dev.stormy.client.module.api.Category;
import dev.stormy.client.module.api.Module;
import dev.stormy.client.module.setting.impl.ComboSetting;
import dev.stormy.client.module.setting.impl.DescriptionSetting;
import dev.stormy.client.module.setting.impl.SliderSetting;
import dev.stormy.client.module.setting.impl.TickSetting;
import dev.stormy.client.utils.player.PlayerUtils;
import dev.stormy.client.utils.render.Render3DUtils;
import dev.stormy.client.utils.world.DistanceUtils;
import dev.stormy.client.utils.world.WorldUtils;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.weavemc.loader.api.event.RenderWorldEvent;
import net.weavemc.loader.api.event.SubscribeEvent;

public class BedNuker extends Module {

    public static ComboSetting<Modes> espMode;
    public static SliderSetting range;
    public static TickSetting instant;

    public BlockPos bedPos;
    public boolean breaking;

    public BedNuker() {
        super("BedNuker", Category.Player, 0);
        this.registerSetting(new DescriptionSetting("Breaks beds."));
        this.registerSetting(espMode = new ComboSetting<>("ESP", Modes.None));
        this.registerSetting(range = new SliderSetting("Range", 4.0D, 3.0D, 6.0D, 1.0D));
        this.registerSetting(instant = new TickSetting("Instant break", false));
    }

    @SubscribeEvent
    public void onUpdate(UpdateEvent ev) {
        if (mc.thePlayer != null) {
            if (!breaking) {
                bedPos = WorldUtils.findNearestBedPos(mc.thePlayer.getPosition(), (int) range.getInput());
                breaking = true;
            }

            if (bedPos != null) {
                if (DistanceUtils.distanceToBlockPos(bedPos) > range.getInput()) {
                    breaking = false;
                } else if (mc.theWorld.getBlockState(bedPos).getBlock() == Blocks.bed) {
                    if (instant.isToggled()) {
                        mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, bedPos, EnumFacing.NORTH));
                        mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, bedPos, EnumFacing.NORTH));
                    } else {
                        mc.playerController.onPlayerDamageBlock(bedPos, EnumFacing.NORTH);
                    }
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

        if (PlayerUtils.isPlayerInGame() && bedPos != null && espMode.getMode().ordinal() - 1 != -1) {
            if (mc.theWorld.getBlockState(bedPos).getBlock() == Blocks.bed) {
                BlockPos bedPos2 = null;

                IBlockState state = mc.theWorld.getBlockState(bedPos);

                if (state.getValue(BlockBed.PART) == BlockBed.EnumPartType.FOOT) {
                    bedPos2 = bedPos.offset(state.getValue(BlockBed.FACING));
                } else if (state.getValue(BlockBed.PART) == BlockBed.EnumPartType.HEAD) {
                    bedPos2 = bedPos.offset(state.getValue(BlockBed.FACING).getOpposite());
                }

                if (bedPos2 != null) {
                    Render3DUtils.drawBlockPoses(
                            bedPos,
                            bedPos2,
                            espMode.getMode().ordinal() - 1,
                            Theme.getMainColor().getRGB()
                    );
                }
            }
        }
    }

    public enum Modes {
        None, Box, Shaded, Both
    }
}