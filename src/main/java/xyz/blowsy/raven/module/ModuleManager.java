package xyz.blowsy.raven.module;

import net.minecraft.client.gui.FontRenderer;
import xyz.blowsy.raven.module.modules.client.*;
import xyz.blowsy.raven.module.modules.client.ArrayListModule;
import xyz.blowsy.raven.module.modules.combat.*;
import xyz.blowsy.raven.module.modules.movement.*;
import xyz.blowsy.raven.module.modules.movement.Timer;
import xyz.blowsy.raven.module.modules.player.*;
import xyz.blowsy.raven.module.modules.render.*;
import xyz.blowsy.raven.utils.Utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ModuleManager {
   private final List<Module> modules = new ArrayList<>();
   public static boolean initialized = false;

   public ModuleManager() {
      if (initialized) return;

      addModule(new AutoClicker());
      addModule(new RightClicker());
      addModule(new AimAssist());
      addModule(new ClickAssist());
      addModule(new Reach());
      addModule(new Velocity());
      addModule(new InvMove());
      addModule(new KeepSprint());
      addModule(new NoSlow());
      addModule(new Timer());
      addModule(new AutoPlace());
      addModule(new BedNuker());
      addModule(new FastPlace());
      addModule(new SafeWalk());
      addModule(new AntiBot());
      addModule(new Chams());
      addModule(new ChestESP());
      addModule(new Nametags());
      addModule(new PlayerESP());
      addModule(new ArrayListModule());
      addModule(new ClickGuiModule());
      addModule(new ClosetSpeed());
      addModule(new Blink());
      addModule(new Bhop());
      addModule(new Killaura());
      addModule(new AntiVoid());

      initialized = true;
   }

   private void addModule(Module m) {
      modules.add(m);
      modules.sort(Comparator.comparing(module -> m.getName().toLowerCase()));
   }

   public Module getModuleByClazz(Class<? extends Module> c) {
      if (!initialized) return null;

      for (Module module : modules) {
         if (module.getClass().equals(c))
            return module;
      }
      return null;
   }


   public List<Module> getModules() {
      return modules;
   }

   public List<Module> getModulesInCategory(Module.ModuleCategory categ) {
      java.util.ArrayList<Module> modulesOfCat = new java.util.ArrayList<>();

      for (Module mod : modules) {
         if (mod.moduleCategory().equals(categ)) {
            modulesOfCat.add(mod);
         }
      }

      return modulesOfCat;
   }

   public void sort() {
      if (ArrayListModule.alphabeticalSort.isToggled()) {
         modules.sort(Comparator.comparing(Module::getName));
      } else {
         modules.sort((o1, o2) -> Utils.mc.fontRendererObj.getStringWidth(o2.getName()) - Utils.mc.fontRendererObj.getStringWidth(o1.getName()));
      }

   }

   public void sortLongShort() {
      modules.sort(Comparator.comparingInt(o2 -> Utils.mc.fontRendererObj.getStringWidth(o2.getName())));
   }

   public void sortShortLong() {
      modules.sort((o1, o2) -> Utils.mc.fontRendererObj.getStringWidth(o2.getName()) - Utils.mc.fontRendererObj.getStringWidth(o1.getName()));
   }

   public int getLongestActiveModule(FontRenderer fr) {
      int length = 0;
      for(Module mod : modules) {
         if(mod.isEnabled()){
            if(fr.getStringWidth(mod.getName()) > length){
               length = fr.getStringWidth(mod.getName());
            }
         }
      }
      return length;
   }

   public int getBoxHeight(FontRenderer fr, int margin) {
      int length = 0;
      for(Module mod : modules) {
         if(mod.isEnabled()){
            length += fr.FONT_HEIGHT + margin;
         }
      }
      return length;
   }
}