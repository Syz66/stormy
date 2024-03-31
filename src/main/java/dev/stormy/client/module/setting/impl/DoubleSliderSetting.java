package dev.stormy.client.module.setting.impl;

import com.google.gson.JsonObject;
import dev.stormy.client.clickgui.Component;
import dev.stormy.client.clickgui.components.ModuleComponent;
import dev.stormy.client.module.setting.Setting;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DoubleSliderSetting extends Setting {
    @Getter
    private final String name;
    @Getter
    private final double max;
    @Getter
    private final double min;
    private final double interval;
    private final double defaultValMin, defaultValMax;
    private double valMax, valMin;

    public DoubleSliderSetting(String settingName, double defaultValueMin, double defaultValueMax, double min, double max, double intervals) {
        super(settingName);
        this.name = settingName;
        this.valMin = defaultValueMin;
        this.valMax = defaultValueMax;
        this.min = min;
        this.max = max;
        this.interval = intervals;
        this.defaultValMin = valMin;
        this.defaultValMax = valMax;
    }

    public static double correct(double val, double min, double max) {
        val = Math.max(min, val);
        val = Math.min(max, val);
        return val;
    }

    public static double round(double val, int p) {
        if (p < 0) {
            return 0.0D;
        } else {
            BigDecimal bd = new BigDecimal(val);
            bd = bd.setScale(p, RoundingMode.HALF_UP);
            return bd.doubleValue();
        }
    }

    @Override
    public void resetToDefaults() {
        this.setValueMin(defaultValMin);
        this.setValueMax(defaultValMax);
    }

    @Override
    public JsonObject getConfigAsJson() {
        JsonObject data = new JsonObject();
        data.addProperty("type", getSettingType());
        data.addProperty("valueMin", getInputMin());
        data.addProperty("valueMax", getInputMax());
        return data;
    }

    @Override
    public String getSettingType() {
        return "doubleslider";
    }

    @Override
    public void applyConfigFromJson(JsonObject data) {
        if (!data.get("type").getAsString().equals(getSettingType()))
            return;

        setValueMax(data.get("valueMax").getAsDouble());
        setValueMin(data.get("valueMin").getAsDouble());
    }

    @Override
    public Component createComponent(ModuleComponent moduleComponent) {
        return null;
    }

    public double getInputMin() {
        return round(this.valMin, 2);
    }

    public double getInputMax() {
        return round(this.valMax, 2);
    }

    public void setValueMin(double n) {
        n = correct(n, this.min, this.valMax);
        n = (double) Math.round(n * (1.0D / this.interval)) / (1.0D / this.interval);
        this.valMin = n;
    }

    public void setValueMax(double n) {
        n = correct(n, this.valMin, this.max);
        n = (double) Math.round(n * (1.0D / this.interval)) / (1.0D / this.interval);
        this.valMax = n;
    }
}
