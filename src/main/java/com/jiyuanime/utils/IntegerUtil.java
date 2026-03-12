package com.jiyuanime.utils;

public class IntegerUtil {

    private static final int[] SIZE_TABLE = {0, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, Integer.MAX_VALUE};

    public static boolean isSizeTable(int value) {
        for (int j : SIZE_TABLE) {
            if (value == j) {
                return true;
            }
        }
        return false;
    }
}
