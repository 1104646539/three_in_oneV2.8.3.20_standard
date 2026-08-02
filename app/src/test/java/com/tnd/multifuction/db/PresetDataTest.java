package com.tnd.multifuction.db;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class PresetDataTest {

    @Test
    public void projectPresetsMatchV4Document() {
        assertEquals(21, PresetData.PROJECTS.length);
        assertEquals("农药残留", PresetData.PROJECTS[0].name);
        assertFalse(Arrays.stream(PresetData.PROJECTS)
                .anyMatch(project -> "过氧化物酶".equals(project.name)));

        PresetData.ProjectPreset borax = Arrays.stream(PresetData.PROJECTS)
                .filter(project -> "硼砂".equals(project.name))
                .findFirst().orElse(null);
        assertNotNull(borax);
        assertEquals(-5.30f, borax.b, 0.0001f);
    }

    @Test
    public void colloidalGoldPresetsMatchV4Document() {
        assertEquals("浩景A", PresetData.CARD_COMPANY);
        assertEquals("300", PresetData.SCAN_START);
        assertEquals("940", PresetData.SCAN_END);
        assertEquals("320", PresetData.CT_DISTANCE);
        assertEquals("120", PresetData.CT_WIDTH);
        assertEquals(7, PresetData.SAMPLE_TYPES.length);
        assertEquals(19, PresetData.SAMPLES.length);
        assertTrue(Arrays.asList(PresetData.SAMPLES).contains("豆芽"));
        assertEquals(10, PresetData.DISAPPEARING_LINE_PROJECTS.length);
        assertEquals(19, PresetData.COMPARISON_LINE_PROJECTS.length);
        assertEquals("重金属铅", PresetData.DISAPPEARING_LINE_PROJECTS[9].name);
        assertEquals("0.9", PresetData.DISAPPEARING_LINE_PROJECTS[9].threshold);
        assertTrue(Arrays.stream(PresetData.COMPARISON_LINE_PROJECTS)
                .allMatch(project -> "0.9".equals(project.threshold)));
    }
}
