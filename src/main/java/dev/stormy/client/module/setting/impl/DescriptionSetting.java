package dev.stormy.client.module.setting.impl;

import com.google.gson.JsonObject;
import dev.stormy.client.module.setting.Setting;
import lombok.Getter;
import lombok.Setter;

public class DescriptionSetting extends Setting {
    private final String defaultDesc;
    @Getter
    @Setter
    private String desc;

    public DescriptionSetting(String t) {
        super(t);
        this.desc = t;
        this.defaultDesc = t;
    }

    @Override
    public void resetToDefaults() {
        this.desc = defaultDesc;
    }

    @Override
    public JsonObject getConfigAsJson() {
        JsonObject data = new JsonObject();
        data.addProperty("type", getSettingType());
        data.addProperty("value", getDesc());
        return data;
    }

    @Override
    public String getSettingType() {
        return "desc";
    }

    @Override
    public void applyConfigFromJson(JsonObject data) {
        if (!data.get("type").getAsString().equals(getSettingType()))
            return;

        setDesc(data.get("value").getAsString());
    }
}
