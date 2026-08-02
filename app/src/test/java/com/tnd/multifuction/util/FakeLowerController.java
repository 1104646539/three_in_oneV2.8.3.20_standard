package com.tnd.multifuction.util;

import java.nio.charset.Charset;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

/** Scriptable lower-controller simulator used by serial integration tests. */
final class FakeLowerController {

    private static final Charset ASCII = Charset.forName("US-ASCII");

    private final Queue<Chunk> responseChunks = new ArrayDeque<>();
    private final List<String> commands = new ArrayList<>();
    private List<Chunk> getAllDataScript = new ArrayList<>();
    private long nowMillis;
    private boolean portOpen = true;

    void scriptGetAllData(Chunk... chunks) {
        getAllDataScript = Arrays.asList(chunks);
    }

    boolean write(String command) {
        if (!portOpen) {
            return false;
        }
        commands.add(command);
        if ("GetAllData".equals(command)) {
            for (Chunk chunk : getAllDataScript) {
                responseChunks.offer(new Chunk(nowMillis + chunk.delayMillis, chunk.text));
            }
        } else {
            responseChunks.offer(new Chunk(nowMillis, "OK\n"));
        }
        return true;
    }

    byte[] read() {
        Chunk chunk = responseChunks.peek();
        if (chunk == null || chunk.delayMillis > nowMillis) {
            return null;
        }
        responseChunks.remove();
        return chunk.text.getBytes(ASCII);
    }

    void advanceTime(long millis) {
        nowMillis += millis;
    }

    void setPortOpen(boolean portOpen) {
        this.portOpen = portOpen;
    }

    List<String> getCommands() {
        return commands;
    }

    static Chunk chunk(long delayMillis, String text) {
        return new Chunk(delayMillis, text);
    }

    static final class Chunk {
        final long delayMillis;
        final String text;

        Chunk(long delayMillis, String text) {
            this.delayMillis = delayMillis;
            this.text = text;
        }
    }
}
