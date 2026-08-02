package com.tnd.multifuction.util;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.util.ArrayDeque;
import java.util.Queue;

/** Buffers newline-delimited lower-controller responses across serial reads. */
public final class SerialFrameParser {

    private static final Charset ASCII = Charset.forName("US-ASCII");
    private static final int MAX_FRAME_BYTES = 1024;

    private final ByteArrayOutputStream pending = new ByteArrayOutputStream();
    private final Queue<byte[]> frames = new ArrayDeque<>();

    public synchronized void append(byte[] data, int length) {
        if (data == null || length <= 0 || length > data.length) {
            return;
        }
        for (int i = 0; i < length; i++) {
            byte value = data[i];
            if (pending.size() >= MAX_FRAME_BYTES) {
                clear();
                return;
            }
            pending.write(value);
            if (value == '\n') {
                frames.offer(pending.toByteArray());
                pending.reset();
            }
        }
    }

    public synchronized byte[] pollFrame() {
        return frames.poll();
    }

    public synchronized void clear() {
        pending.reset();
        frames.clear();
    }

    public static float[] parseReadings(byte[] frame, int channelCount) {
        if (frame == null || channelCount <= 0) {
            return null;
        }
        String text = new String(frame, ASCII).trim();
        if (!text.startsWith("OK")) {
            return null;
        }
        String payload = text.substring(2);
        String[] values = payload.split(",", -1);
        if (values.length != channelCount) {
            return null;
        }
        float[] readings = new float[channelCount];
        try {
            for (int i = 0; i < values.length; i++) {
                readings[i] = Float.parseFloat(values[i].trim());
                if (Float.isNaN(readings[i]) || Float.isInfinite(readings[i])) {
                    return null;
                }
            }
        } catch (NumberFormatException e) {
            return null;
        }
        return readings;
    }

    public static boolean isAcknowledgement(byte[] frame) {
        return frame != null && "OK".equals(new String(frame, ASCII).trim());
    }
}
