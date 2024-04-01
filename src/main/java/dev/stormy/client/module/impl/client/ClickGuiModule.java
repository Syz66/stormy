package dev.stormy.client.module.impl.client;

import dev.stormy.client.Stormy;
import dev.stormy.client.clickgui.ClickGui;
import dev.stormy.client.module.api.Category;
import dev.stormy.client.module.api.Module;
import dev.stormy.client.module.setting.impl.ComboSetting;
import dev.stormy.client.utils.player.PlayerUtils;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.weavemc.loader.api.event.SubscribeEvent;
import net.weavemc.loader.api.event.TickEvent;

public class ClickGuiModule extends Module {
    public static ComboSetting<Colors> clientTheme;
    private final KeyBinding[] moveKeys = new KeyBinding[]{
            mc.gameSettings.keyBindForward, mc.gameSettings.keyBindBack, mc.gameSettings.keyBindRight, mc.gameSettings.keyBindLeft, mc.gameSettings.keyBindJump, mc.gameSettings.keyBindSprint
    };

    public ClickGuiModule() {
        super("ClickGui", Category.Client, 28);
        this.registerSetting(clientTheme = new ComboSetting<>("Theme", Colors.Steel));
    }

    @Override
    public void onEnable() {
        if (PlayerUtils.isPlayerInGame() && mc.currentScreen != Stormy.clickGui) {
            mc.displayGuiScreen(Stormy.clickGui);
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent e) {
        for (KeyBinding bind : moveKeys) {
            bind.pressed = GameSettings.isKeyDown(bind);
        }
    }

    @Override
    public void onDisable() {
        if (PlayerUtils.isPlayerInGame() && mc.currentScreen instanceof ClickGui) {
            mc.displayGuiScreen(null);
        }
    }

    public enum Colors {
        Tryfle, Sassan, Gold, Steel, Emerald, Orange, Amethyst, Lily, PastelPink, Pink, Syz
    }
}
