package me.pacphi.converse.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class PcmPlayer {

    private static Logger log = LoggerFactory.getLogger(PcmPlayer.class);

    public static PcmPlayer create() {
        return new PcmPlayer();
    }

    public void play(byte[] audioData, AudioFormat format) {
        try {
            AudioInputStream audioInputStream = new AudioInputStream(
                    new ByteArrayInputStream(audioData),
                    format,
                    audioData.length / format.getFrameSize()
            );

            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();

            byte[] buffer = new byte[4096];
            int bytesRead = 0;
            while ((bytesRead = audioInputStream.read(buffer)) != -1) {
                line.write(buffer, 0, bytesRead);
            }

            line.drain();
            line.close();
        } catch (IOException | LineUnavailableException e) {
            log.error("Error playing audio", e);
        }
    }
}
