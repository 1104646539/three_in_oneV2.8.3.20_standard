package com.tnd.multifuction.util;

import java.util.Arrays;
import java.util.Random;

/**
 * Deterministic detector responses used when no lower controller is connected.
 */
public final class DebugDetectionData {

    private DebugDetectionData() {
    }

    public static float[] readings(int channelCount, float value) {
        if (channelCount <= 0) {
            throw new IllegalArgumentException("channelCount must be positive");
        }
        float[] readings = new float[channelCount];
        for (int i = 0; i < readings.length; i++) {
            readings[i] = (float) Math.abs(new Random().nextDouble() * 100000 % value);
        }
//        Arrays.fill(readings, value);
        return readings;
    }

    public static String[] colloidalGoldResult() {
        return new String[]{"0.2", "合格"};
    }
}
