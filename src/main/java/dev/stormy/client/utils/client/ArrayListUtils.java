package dev.stormy.client.utils.client;

public class ArrayListUtils {
    public static PositionMode getPostitionMode(int marginX, int marginY, double height, double width) {
        int halfHeight = (int) (height / 4);
        int halfWidth = (int) width;
        PositionMode positionMode = null;

        if (marginY < halfHeight) {
            if (marginX < halfWidth) {
                positionMode = PositionMode.UPLEFT;
            }
            if (marginX > halfWidth) {
                positionMode = PositionMode.UPRIGHT;
            }
        }

        if (marginY > halfHeight) {
            if (marginX < halfWidth) {
                positionMode = PositionMode.DOWNLEFT;
            }
            if (marginX > halfWidth) {
                positionMode = PositionMode.DOWNRIGHT;
            }
        }

        return positionMode;
    }

    public enum PositionMode {
        UPLEFT, UPRIGHT, DOWNLEFT, DOWNRIGHT
    }
}
