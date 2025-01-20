package me.pacphi.converse.elevenlabs.util;

import javazoom.jl.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class Audio {

    private static Logger log = LoggerFactory.getLogger(Audio.class);

    public static void playback(byte[] audioData) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(audioData);
            Player player = new Player(bis);

            Thread playerThread = Thread.ofVirtual().start(() -> {
                try {
                    player.play();
                } catch (Exception e) {
                    log.error("Error during audio playback", e);
                } finally {
                    player.close();
                    try {
                        bis.close();
                    } catch (IOException e) {
                        log.error("Error closing audio stream", e);
                    }
                }
            });

            // Wait for playback to complete if needed
            playerThread.join();

        } catch (Exception e) {
            log.error("Error initializing audio playback", e);
            throw new RuntimeException("Failed to play audio", e);
        }
    }
}