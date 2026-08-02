package com.tnd.multifuction.util;

import org.junit.Test;

import java.nio.charset.Charset;
import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class SerialFrameParserTest {

    private static final Charset ASCII = Charset.forName("US-ASCII");
    private static final String NORMAL_FRAME =
            "OK0.600,0.500,0.400,0.300,0.200,0.100,0.900,0.800,0.700,0.600," +
            "0.500,0.400,0.300,0.200,0.100,0.900,0.800,0.700,0.600,0.500\n";

    @Test
    public void parsesNormalTwentyChannelFrameWithoutIndexSwaps() {
        float[] values = SerialFrameParser.parseReadings(NORMAL_FRAME.getBytes(ASCII), 20);
        assertNotNull(values);
        assertEquals(20, values.length);
        assertEquals(0.6f, values[0], 0.0001f);
        assertEquals(0.1f, values[5], 0.0001f);
        assertEquals(0.5f, values[19], 0.0001f);
    }

    @Test
    public void parsesAllZeroDetectionFrame() {
        float[] values = SerialFrameParser.parseReadings(makeZeroFrame().getBytes(ASCII), 20);
        assertNotNull(values);
        for (float value : values) {
            assertEquals(0.0f, value, 0.0f);
        }
    }

    @Test
    public void buffersFragmentedFrameAndSeparatesStickyFrames() {
        SerialFrameParser parser = new SerialFrameParser();
        byte[] first = "OK0.600,0.500,".getBytes(ASCII);
        parser.append(first, first.length);
        assertNull(parser.pollFrame());

        String remainder = NORMAL_FRAME.substring("OK0.600,0.500,".length());
        byte[] sticky = (remainder + "OK\n").getBytes(ASCII);
        parser.append(sticky, sticky.length);
        assertArrayEquals(NORMAL_FRAME.getBytes(ASCII), parser.pollFrame());
        assertTrue(SerialFrameParser.isAcknowledgement(parser.pollFrame()));
        assertNull(parser.pollFrame());
    }

    @Test
    public void rejectsMalformedDetectionFrames() {
        assertNull(SerialFrameParser.parseReadings("0.1,0.2\n".getBytes(ASCII), 20));
        assertNull(SerialFrameParser.parseReadings(makeFrame(19, false).getBytes(ASCII), 20));
        assertNull(SerialFrameParser.parseReadings(makeFrame(21, false).getBytes(ASCII), 20));
        assertNull(SerialFrameParser.parseReadings(makeFrame(20, true).getBytes(ASCII), 20));
        assertNull(SerialFrameParser.parseReadings(makeFrame(20, false)
                .replace("10.1", "ERR").getBytes(ASCII), 20));
        assertNull(SerialFrameParser.parseReadings("OK0.1,0.2".getBytes(ASCII), 20));
        assertFalse(SerialFrameParser.isAcknowledgement("ERR\n".getBytes(ASCII)));
    }

    @Test
    public void fakeLowerControllerSupportsChunksDelaysAcknowledgementsAndOrder() {
        FakeLowerController lower = new FakeLowerController();
        lower.scriptGetAllData(
                FakeLowerController.chunk(0, "OK0.600,0.500,"),
                FakeLowerController.chunk(490, NORMAL_FRAME.substring("OK0.600,0.500,".length())));

        lower.write("Light410");
        assertTrue(SerialFrameParser.isAcknowledgement(lower.read()));
        lower.write("BSTest");
        assertTrue(SerialFrameParser.isAcknowledgement(lower.read()));
        lower.write("GetAllData");

        SerialFrameParser parser = new SerialFrameParser();
        byte[] chunk = lower.read();
        parser.append(chunk, chunk.length);
        assertNull(parser.pollFrame());
        lower.advanceTime(490);
        chunk = lower.read();
        parser.append(chunk, chunk.length);
        assertNotNull(SerialFrameParser.parseReadings(parser.pollFrame(), 20));
        assertEquals(Arrays.asList("Light410", "BSTest", "GetAllData"), lower.getCommands());
    }

    @Test
    public void fakeLowerControllerCanSimulateTimeout() {
        FakeLowerController lower = new FakeLowerController();
        lower.scriptGetAllData(FakeLowerController.chunk(1000, NORMAL_FRAME));
        lower.write("GetAllData");
        lower.advanceTime(999);
        assertNull(lower.read());
        lower.advanceTime(1);
        assertNotNull(lower.read());
    }

    @Test
    public void fakeLowerControllerCanSimulateClosedPortAndReconnect() {
        FakeLowerController lower = new FakeLowerController();
        lower.setPortOpen(false);
        assertFalse(lower.write("GetAllData"));
        lower.setPortOpen(true);
        assertTrue(lower.write("GetAllData"));
    }

    private static String makeFrame(int count, boolean invalidToken) {
        StringBuilder result = new StringBuilder("OK");
        for (int i = 0; i < count; i++) {
            if (i > 0) {
                result.append(',');
            }
            result.append(invalidToken && i == 10 ? "NaN" : i + 0.1f);
        }
        return result.append('\n').toString();
    }

    private static String makeZeroFrame() {
        StringBuilder result = new StringBuilder("OK");
        for (int i = 0; i < 20; i++) {
            if (i > 0) {
                result.append(',');
            }
            result.append("0.0");
        }
        return result.append('\n').toString();
    }
}
