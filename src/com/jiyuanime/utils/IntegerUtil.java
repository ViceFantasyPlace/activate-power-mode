package com.jiyuanime.utils;

public class IntegerUtil {

    final static int[] sSizeTable = {0, 10, 100, 1000, 10000, 100000, 1000000,
            10000000, 100000000, Integer.MAX_VALUE};

    public static boolean isSizeTable(int integerValue) {
        for (int i = 0; i < sSizeTable.length; i++) {
            if (integerValue == sSizeTable[i]) {
                return true;
            }
        }
        return false;
    }
}
