package dev.stormy.client.module.impl.render;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import dev.stormy.client.clickgui.Theme;
import dev.stormy.client.module.api.Category;
import dev.stormy.client.module.api.Module;
import dev.stormy.client.module.setting.impl.ComboSetting;
import dev.stormy.client.module.setting.impl.DescriptionSetting;
import dev.stormy.client.module.setting.impl.SliderSetting;
import dev.stormy.client.utils.player.PlayerUtils;
import dev.stormy.client.utils.render.Render3DUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.weavemc.loader.api.event.RenderWorldEvent;
import net.weavemc.loader.api.event.SubscribeEvent;
import net.weavemc.loader.api.event.TickEvent;

public class BedESP extends Module {
    public static ComboSetting<Modes> mode;
    public static SliderSetting range, delay;

    public BiMap<BlockPos, BlockPos> beds = HashBiMap.create();

    public BedESP() {
        super("BedESP", Category.Render, 0);
        this.registerSetting(new DescriptionSetting("Having a low delay & a high range"));
        this.registerSetting(new DescriptionSetting("may cause performance issues."));
        this.registerSetting(mode = new ComboSetting<>("Mode", Modes.Box));
        this.registerSetting(delay = new SliderSetting("Delay (ticks)", 4, 1, 40, 1));
        this.registerSetting(range = new SliderSetting("Range (blocks)", 20, 5, 100, 1));
    }

    public int delayTicks;

    @SubscribeEvent
    public void onTick(TickEvent ev) {
        if (delayTicks > delay.getInput()) {
            delayTicks = 0;
            beds.clear();

            int rangeInt = (int) range.getInput();

            for (int y = rangeInt; y >= -rangeInt; --y) {
                for (int x = rangeInt; x >= -rangeInt; --x) {
                    for (int z = rangeInt; z >= -rangeInt; --z) {
                        if (PlayerUtils.isPlayerInGame()) {
                            BlockPos pos = new BlockPos(mc.thePlayer.posX + x,
                                    mc.thePlayer.posY + y,
                                    mc.thePlayer.posZ + z
                            );

                            Block block = mc.theWorld.getBlockState(pos).getBlock();
                            IBlockState state = mc.theWorld.getBlockState(pos);

                            if (block == Blocks.bed) {
                                if (state.getValue(BlockBed.PART) == BlockBed.EnumPartType.FOOT) {
                                    BlockPos pos2 = pos.offset(state.getValue(BlockBed.FACING));
                                    if (mc.theWorld.getBlockState(pos2).getBlock() == Blocks.bed) {
                                        beds.put(pos, pos2);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            delayTicks++;
        }
    }

    @SubscribeEvent
    public void onRenderWorld(RenderWorldEvent ev) {
        if (PlayerUtils.isPlayerInGame()) {
            for (BlockPos pos : beds.keySet()) {
                BlockPos pos2 = beds.get(pos);
                Render3DUtils.drawBlockPoses(
                        pos,
                        pos2,
                        mode.getMode().ordinal(),
                        Theme.getMainColor().getRGB()
                );
            }
        }
    }

    @Override
    public void onDisable() {
        beds.clear();
    }

    public enum Modes {
        Box, Shaded, Both
    }
}
