package dev.stormy.client.utils.render;

import java.awt.*;

public class ColorUtils {
    private static Color getColor(Color color, double percent) {
        final double inverse_percent = 1.0 - percent;
        final int redPart = (int) (color.getRed() * percent + 255 * inverse_percent);
        final int greenPart = (int) (color.getGreen() * percent + 255 * inverse_percent);
        final int bluePart = (int) (color.getBlue() * percent + 255 * inverse_percent);
        return new Color(redPart, greenPart, bluePart);
    }

    public static Color gradientDraw(Color color, int yLocation) {
        final double percent = Math.sin(System.currentTimeMillis() / 600.0D + yLocation * 0.06D) * 0.5D + 0.5D;
        return getColor(color, percent);
    }

    public static Color reverseGradientDraw(Color color, int yLocation) {
        final double percent = Math.sin(System.currentTimeMillis() / 600.0D - yLocation * 0.06D) * 0.5D + 0.5D;
        return getColor(color, percent);
    }

    public static int rainbowDraw(long speed, long... delay) {
        long time = System.currentTimeMillis() + (delay.length > 0 ? delay[0] : 0L);
        return Color.getHSBColor((float) (time % (15000L / speed)) / (15000.0F / (float) speed), 1.0F, 1.0F).getRGB();
    }
}