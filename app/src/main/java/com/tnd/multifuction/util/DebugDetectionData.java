package com.tnd.multifuction.util;

import java.util.Arrays;

/** Deterministic detector responses used when no lower controller is connected. */
public final class DebugDetectionData {

    private DebugDetectionData() {
    }

    public static float[] readings(int channelCount, float value) {
        if (channelCount <= 0) {
            throw new IllegalArgumentException("channelCount must be positive");
        }
        float[] readings = new float[channelCount];
        Arrays.fill(readings, value);
        return readings;
    }

    public static String[] colloidalGoldResult() {
        return new String[]{"0.2", "合格"};
    }
}
