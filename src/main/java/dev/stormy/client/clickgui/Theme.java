package dev.stormy.client.clickgui;

import dev.stormy.client.module.impl.client.ClickGuiModule;

import java.awt.*;

public class Theme {
    public static Color getMainColor() {
        String themeColor = ClickGuiModule.clientTheme.getMode().toString();

        return switch (themeColor.toLowerCase()) {
            case "princess" -> new Color(237, 138, 209);
            case "rose" -> new Color(232, 100, 195);
            case "paradise" -> new Color(216, 65, 100);
            case "red" -> new Color(255, 105, 105);
            case "gold" -> new Color(255, 215, 0);
            case "steel" -> new Color(52, 152, 219);
            case "emerald" -> new Color(46, 204, 113);
            case "orange" -> new Color(255, 165, 0);
            case "amethyst" -> new Color(155, 89, 182);
            case "koamaru" -> new Color(76, 56, 108);
            case "violet" -> new Color(118, 105, 201);
            default -> // UNUSED
                    new Color(255, 255, 255);
        };
    }

    public static Color getBackColor() {
        return new Color(0, 0, 0, 100);
    }
}