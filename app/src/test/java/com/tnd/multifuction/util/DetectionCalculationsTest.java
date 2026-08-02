package com.tnd.multifuction.util;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DetectionCalculationsTest {

    @Test
    public void calculationSubtractsBlankBeforeApplyingKAndB() {
        assertEquals(7.0d, DetectionCalculations.calculate(2.0d, 5.0d, 1.0d, -1.0d), 0.000001d);
        assertEquals(-5.3d, DetectionCalculations.calculate(106.07d, 0.25d, 0.25d, -5.3d), 0.000001d);
    }

    @Test
    public void positivePesticideRateIsNotChanged() {
        assertEquals(12.5d, DetectionCalculations.normalizePesticideRate(12.5d, new Random(1)), 0.0d);
    }

    @Test
    public void nonPositivePesticideRateIsAlwaysWithinRequiredRange() {
        Random random = new Random(2026);
        for (int i = 0; i < 1000; i++) {
            double value = DetectionCalculations.normalizePesticideRate(i % 2 == 0 ? 0 : -1, random);
            assertTrue(value >= 0.5d);
            assertTrue(value <= 5.0d);
        }
    }

    @Test
    public void testModeBlankReferenceUsesValidRange() {
        Random random = new Random(4);
        for (int i = 0; i < 100; i++) {
            double value = DetectionCalculations.randomBlankReference(random);
            assertTrue(value >= 0.3d);
            assertTrue(value <= 0.9d);
        }
    }
}
