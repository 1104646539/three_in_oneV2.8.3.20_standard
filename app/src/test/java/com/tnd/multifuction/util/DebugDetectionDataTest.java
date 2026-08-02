package com.tnd.multifuction.util;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class DebugDetectionDataTest {

    @Test
    public void readingsMatchCurrentChannelCount() {
        float[] readings = DebugDetectionData.readings(10, 0.6f);

        assertEquals(10, readings.length);
        for (float reading : readings) {
            assertEquals(0.6f, reading, 0.0f);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void readingsRejectInvalidChannelCount() {
        DebugDetectionData.readings(0, 0.6f);
    }

    @Test
    public void colloidalGoldResultUsesExpectedControllerShape() {
        assertArrayEquals(new String[]{"0.2", "合格"},
                DebugDetectionData.colloidalGoldResult());
    }
}
