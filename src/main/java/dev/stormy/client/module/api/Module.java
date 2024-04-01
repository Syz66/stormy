package dev.stormy.client.module.api;

import com.google.gson.JsonObject;
import dev.stormy.client.module.setting.Setting;
import dev.stormy.client.module.setting.impl.TickSetting;
import net.minecraft.client.Minecraft;
import net.weavemc.loader.api.event.EventBus;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public abstract class Module {
    protected static Minecraft mc = Minecraft.getMinecraft();
    private final String moduleName;
    private final Category moduleCategory;
    protected ArrayList<Setting> settings;
    protected boolean enabled = false;
    protected boolean defaultEnabled = false;
    protected int bind;
    protected int defaultKeycode;
    private boolean isToggled = false;

    public Module(String name, Category category, int bind) {
        this.moduleName = name;
        this.moduleCategory = category;
        this.settings = new ArrayList<>();
        this.bind = bind;
        this.defaultKeycode = bind;
    }

    public JsonObject getConfigAsJson() {
        JsonObject settings = new JsonObject();

        for (Setting setting : this.settings) {
            JsonObject settingData = setting.getConfigAsJson();
            settings.add(setting.settingName, settingData);
        }

        JsonObject data = new JsonObject();
        data.addProperty("enabled", enabled);
        data.addProperty("keycode", bind);
        data.add("settings", settings);

        return data;
    }

    public void applyConfigFromJson(JsonObject data) {
        try {
            this.bind = data.get("keycode").getAsInt();
            setToggled(data.get("enabled").getAsBoolean());
            JsonObject settingsData = data.get("settings").getAsJsonObject();
            for (Setting setting : this.getSettings()) {
                if (settingsData.has(setting.getName())) {
                    setting.applyConfigFromJson(
                            settingsData.get(setting.getName()).getAsJsonObject()
                    );
                }
            }
        } catch (NullPointerException ignored) {

        }
    }

    public void keybind() {
        if (this.bind != 0 && this.canBeEnabled()) {
            if (!this.isToggled && Keyboard.isKeyDown(this.bind)) {
                this.toggle();
                this.isToggled = true;
            } else if (!Keyboard.isKeyDown(this.bind)) {
                this.isToggled = false;
            }
        }
    }

    public boolean canBeEnabled() {
        return true;
    }

    public ArrayList<Setting> getSettings() {
        return settings;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void enable() {
        this.enabled = true;
        EventBus.subscribe(this);
        this.onEnable();
    }

    public void disable() {
        this.enabled = false;
        EventBus.unsubscribe(this);
        this.onDisable();
    }

    public void toggle() {
        if (this.enabled) {
            this.disable();
        } else {
            this.enable();
        }
    }

    public void setToggled(boolean enabled) {
        if (enabled) {
            enable();
        } else {
            disable();
        }
    }

    public String getName() {
        return this.moduleName;
    }

    public void registerSetting(Setting Setting) {
        this.settings.add(Setting);
    }

    public Category moduleCategory() {
        return this.moduleCategory;
    }

    public void onEnable() {
    }

    public void onDisable() {
    }
    public void guiUpdate() {
    }

    public void guiButtonToggled(TickSetting b) {
    }

    public int getBind() {
        return bind;
    }

    public void setBind(int bind) {
        this.bind = bind;
    }

    public void resetToDefaults() {
        this.bind = defaultKeycode;
        this.setToggled(defaultEnabled);

        for (Setting setting : this.settings) {
            setting.resetToDefaults();
        }
    }
}
