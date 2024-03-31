package dev.stormy.client.utils.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class JavaUtils {
    public static ArrayList<String> toArrayList(String[] strings) {
        return new ArrayList<>(Arrays.asList(strings));
    }

    public static List<String> arrayToList(String[] strings) {
        List<String> list = new ArrayList<>();
        Collections.addAll(list, strings);
        return list;
    }
}
