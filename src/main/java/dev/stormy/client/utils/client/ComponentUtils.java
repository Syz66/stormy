package dev.stormy.client.utils.client;

import dev.stormy.client.clickgui.components.ModuleComponent;

public class ComponentUtils {
    public static boolean isHovering(int x, int y, int x2, int y2, ModuleComponent parent) {
        return x > x2 &&
                x < x2 + parent.category.getWidth() &&
                y > y2 - 1 &&
                y < y2 + 12;
    }
}
