package dev.stormy.client.mixins;

import dev.stormy.client.Stormy;
import dev.stormy.client.module.impl.combat.NoHitDelay;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Shadow public int leftClickCounter;

    @Inject(method = "clickMouse", at = @At("HEAD"))
    public void clickMouseAfter(final CallbackInfo ci) {
        if (Stormy.moduleManager.getModuleByClazz(NoHitDelay.class).isEnabled()) {
            leftClickCounter = 0;
        }
    }

    @Inject(method = "startGame", at = @At("TAIL"))
    private void startGame(CallbackInfo ci) {
        Stormy.init();
    }
}
