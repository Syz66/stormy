package dev.stormy.client.module.impl.client;

import dev.stormy.client.Stormy;
import dev.stormy.client.clickgui.ArrayListPosition;
import dev.stormy.client.clickgui.Theme;
import dev.stormy.client.module.api.Category;
import dev.stormy.client.module.api.Module;
import dev.stormy.client.module.setting.impl.ComboSetting;
import dev.stormy.client.module.setting.impl.TickSetting;
import dev.stormy.client.utils.client.ArrayListUtils;
import dev.stormy.client.utils.player.PlayerUtils;
import dev.stormy.client.utils.render.ColorUtils;
import net.weavemc.loader.api.event.RenderGameOverlayEvent;
import net.weavemc.loader.api.event.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class ArrayListModule extends Module {
    public static final String HUDX_prefix = "HUDX~ ";
    public static final String HUDY_prefix = "HUDY~ ";
    public static TickSetting editPosition, alphabeticalSort;
    public static ComboSetting<ColorModes> colorMode;
    public static int hudX = 5;
    public static int hudY = 5;
    public static ArrayListUtils.PositionMode positionMode;

    public ArrayListModule() {
        super("ArrayList", Category.Client, 0);
        this.registerSetting(colorMode = new ComboSetting<>("Mode", ColorModes.Fade));
        this.registerSetting(editPosition = new TickSetting("Edit position", false));
        this.registerSetting(alphabeticalSort = new TickSetting("Alphabetical sort", false));
    }

    public static void setHudX(int hudX) {
        ArrayListModule.hudX = hudX;
    }

    public static void setHudY(int hudY) {
        ArrayListModule.hudY = hudY;
    }

    @Override
    public void onEnable() {
        Stormy.moduleManager.sort();
    }

    @Override
    public void guiButtonToggled(TickSetting tick) {
        if (tick == editPosition) {
            editPosition.disable();
            mc.displayGuiScreen(new ArrayListPosition());
        } else if (tick == alphabeticalSort) {
            Stormy.moduleManager.sort();
        }
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post ev) {
        if (PlayerUtils.isPlayerInGame()) {
            if (mc.currentScreen != null || mc.gameSettings.showDebugInfo) {
                return;
            }

            int margin = 2;
            int y = hudY;

            if (!alphabeticalSort.isToggled()) {
                if (positionMode == ArrayListUtils.PositionMode.UPLEFT || positionMode == ArrayListUtils.PositionMode.UPRIGHT) {
                    Stormy.moduleManager.sortShortLong();
                } else if (positionMode == ArrayListUtils.PositionMode.DOWNLEFT || positionMode == ArrayListUtils.PositionMode.DOWNRIGHT) {
                    Stormy.moduleManager.sortLongShort();
                }
            }

            List<Module> en = new ArrayList<>(Stormy.moduleManager.getModules());

            if (en.isEmpty()) return;

            int textBoxWidth = Stormy.moduleManager.getLongestActiveModule(mc.fontRendererObj);
            int textBoxHeight = Stormy.moduleManager.getBoxHeight(mc.fontRendererObj, margin);

            if (hudX < 0) {
                hudX = margin;
            }
            if (hudY < 0) {
                {
                    hudY = margin;
                }
            }

            if (hudX + textBoxWidth > mc.displayWidth / 2) {
                hudX = mc.displayWidth / 2 - textBoxWidth - margin;
            }

            if (hudY + textBoxHeight > mc.displayHeight / 2) {
                hudY = mc.displayHeight / 2 - textBoxHeight;
            }

            for (Module m : en) {
                if (m.isEnabled() && m != this) {
                    if (ArrayListModule.positionMode == ArrayListUtils.PositionMode.DOWNRIGHT || ArrayListModule.positionMode == ArrayListUtils.PositionMode.UPRIGHT) {
                        switch (colorMode.getMode()) {
                            case Static:
                                mc.fontRendererObj.drawString(m.getName(), (float) hudX + (textBoxWidth - mc.fontRendererObj.getStringWidth(m.getName())), (float) y, Theme.getMainColor().getRGB(), true);
                                y += mc.fontRendererObj.FONT_HEIGHT + margin;
                                break;

                            case Fade:
                                mc.fontRendererObj.drawString(m.getName(), (float) hudX + (textBoxWidth - mc.fontRendererObj.getStringWidth(m.getName())), (float) y, ColorUtils.reverseGradientDraw(Theme.getMainColor(), y).getRGB(), true);
                                y += mc.fontRendererObj.FONT_HEIGHT + margin;
                                break;

                            case Breathe:
                                mc.fontRendererObj.drawString(m.getName(), (float) hudX + (textBoxWidth - mc.fontRendererObj.getStringWidth(m.getName())), (float) y, ColorUtils.gradientDraw(Theme.getMainColor(), 0).getRGB(), true);
                                y += mc.fontRendererObj.FONT_HEIGHT + margin;
                                break;
                        }
                    } else {
                        switch (colorMode.getMode()) {
                            case Static:
                                mc.fontRendererObj.drawString(m.getName(), (float) hudX, (float) y, Theme.getMainColor().getRGB(), true);
                                y += mc.fontRendererObj.FONT_HEIGHT + margin;
                                break;

                            case Fade:
                                mc.fontRendererObj.drawString(m.getName(), (float) hudX, (float) y, ColorUtils.reverseGradientDraw(Theme.getMainColor(), y).getRGB(), true);
                                y += mc.fontRendererObj.FONT_HEIGHT + margin;
                                break;

                            case Breathe:
                                mc.fontRendererObj.drawString(m.getName(), (float) hudX, (float) y, ColorUtils.gradientDraw(Theme.getMainColor(), 0).getRGB(), true);
                                y += mc.fontRendererObj.FONT_HEIGHT + margin;
                                break;

                        }
                    }
                }
            }
        }

    }

    public static int getHudX() {
        return hudX;
    }

    public static int getHudY() {
        return hudY;
    }

    public enum ColorModes {
        Static, Fade, Breathe
    }
}
