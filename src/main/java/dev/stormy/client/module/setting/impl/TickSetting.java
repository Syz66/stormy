package dev.stormy.client.module.setting.impl;

import com.google.gson.JsonObject;
import dev.stormy.client.module.setting.Setting;
import lombok.Getter;

public class TickSetting extends Setting {
    @Getter
    private final String name;
    private final boolean defaultValue;
    private boolean isEnabled;

    public TickSetting(String name, boolean isEnabled) {
        super(name);
        this.name = name;
        this.isEnabled = isEnabled;
        this.defaultValue = isEnabled;
    }

    @Override
    public void resetToDefaults() {
        this.isEnabled = defaultValue;
    }

    @Override
    public JsonObject getConfigAsJson() {
        JsonObject data = new JsonObject();
        data.addProperty("type", getSettingType());
        data.addProperty("value", isToggled());
        return data;
    }

    @Override
    public String getSettingType() {
        return "tick";
    }

    @Override
    public void applyConfigFromJson(JsonObject data) {
        if (!data.get("type").getAsString().equals(getSettingType()))
            return;

        setEnabled(data.get("value").getAsBoolean());
    }

    public boolean isToggled() {
        return this.isEnabled;
    }

    public void toggle() {
        this.isEnabled = !this.isEnabled;
    }

    public void enable() {
        this.isEnabled = true;
    }

    public void disable() {
        this.isEnabled = false;
    }

    public void setEnabled(boolean b) {
        this.isEnabled = b;
    }
}
