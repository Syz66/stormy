package xyz.blowsy.raven.module.modules.movement;

import net.weavemc.loader.api.event.SubscribeEvent;
import net.weavemc.loader.api.event.TickEvent;
import xyz.blowsy.raven.clickgui.ClickGui;
import xyz.blowsy.raven.module.Module;
import xyz.blowsy.raven.module.setting.impl.SliderSetting;
import xyz.blowsy.raven.module.setting.impl.TickSetting;

public class Timer extends Module {
   public static SliderSetting speed;
   public static TickSetting strafe;

   public Timer() {
      super("Timer", ModuleCategory.Movement, 0);
      this.registerSetting(speed = new SliderSetting("Speed", 1.0D, 0.5D, 2.5D, 0.01D));
      this.registerSetting(strafe = new TickSetting("Strafe only", false));
   }

   @SubscribeEvent
   public void onTick(TickEvent e) {
      if (!(mc.currentScreen instanceof ClickGui)) {
         if (strafe.isToggled() && mc.thePlayer.moveStrafing == 0.0F) {
            mc.timer.timerSpeed = 1.0f;
            return;
         }

         mc.timer.timerSpeed = (float) speed.getInput();
      } else {
         mc.timer.timerSpeed = 1.0f;
      }

   }

   @Override
   public void onDisable() {
      mc.timer.timerSpeed = 1.0f;
   }
}