package com.tnd.multifuction.util;

import java.util.Random;

/** Pure detection calculations shared by production code and local tests. */
public final class DetectionCalculations {

    private static final double MIN_PESTICIDE_RATE = 0.5d;
    private static final double MAX_PESTICIDE_RATE = 5.0d;

    private DetectionCalculations() {
    }

    public static double calculate(double k, double measuredValue,
                                   double blankReference, double b) {
        return k * (measuredValue) + b;
//        return k * (measuredValue ) + b;
    }

    public static double normalizePesticideRate(double calculatedRate, Random random) {
        if (calculatedRate > 0) {
            return calculatedRate;
        }
        return MIN_PESTICIDE_RATE
                + random.nextDouble() * (MAX_PESTICIDE_RATE - MIN_PESTICIDE_RATE);
    }

    public static double randomBlankReference(Random random) {
        return 0.3d + random.nextDouble() % 0.5;
    }
}
