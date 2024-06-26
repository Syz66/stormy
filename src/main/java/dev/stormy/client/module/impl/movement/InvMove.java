package dev.stormy.client.module.impl.movement;

import dev.stormy.client.module.api.Category;
import dev.stormy.client.module.api.Module;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.weavemc.loader.api.event.SubscribeEvent;
import net.weavemc.loader.api.event.TickEvent;

public class InvMove extends Module {
    private final KeyBinding[] moveKeys = new KeyBinding[]{
            mc.gameSettings.keyBindForward, mc.gameSettings.keyBindBack, mc.gameSettings.keyBindRight, mc.gameSettings.keyBindLeft, mc.gameSettings.keyBindJump
    };

    public InvMove() {
        super("InvMove", Category.Movement, 0);
    }

    @SubscribeEvent
    public void onTick(TickEvent e) {
        if (mc.currentScreen != null) {
            if (mc.currentScreen instanceof GuiChat) {
                return;
            }

            for (KeyBinding bind : moveKeys) {
                bind.pressed = GameSettings.isKeyDown(bind);
            }
        }
    }

    @Override
    public void onDisable() {
        if (mc.currentScreen != null) {
            for (KeyBinding bind : moveKeys) {
                if (bind.pressed) {
                    bind.pressed = false;
                }
            }
        }
    }
}
