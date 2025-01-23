package me.pacphi.converse.util;

import de.jarnbjo.ogg.FileStream;
import de.jarnbjo.ogg.LogicalOggStream;
import de.jarnbjo.vorbis.VorbisStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Collection;

public class OggPlayer {

    private static Logger log = LoggerFactory.getLogger(OggPlayer.class);

    private static final int BUFFER_SIZE = 4096;
    private volatile boolean isPaused = false;

    public static OggPlayer create() {
        return new OggPlayer();
    }

    public void play(byte[] audioData) {
        File tempFile = null;
        RandomAccessFile randomAccessFile = null;

        try {
            tempFile = File.createTempFile("audio", ".ogg");
            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                fos.write(audioData);
            }

            randomAccessFile = new RandomAccessFile(tempFile, "r");
            FileStream oggStream = new FileStream(randomAccessFile);

            Collection<LogicalOggStream> logicalStreams = oggStream.getLogicalStreams();
            if (logicalStreams.isEmpty()) {
                throw new IOException("No Ogg logical stream found");
            }

            LogicalOggStream oggLogicalStream = logicalStreams.iterator().next();
            VorbisStream vorbisStream = new VorbisStream(oggLogicalStream);

            AudioFormat audioFormat = new AudioFormat(
                    vorbisStream.getIdentificationHeader().getSampleRate(),
                    16,
                    vorbisStream.getIdentificationHeader().getChannels(),
                    true,
                    false
            );

            DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
            SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(audioFormat);
            line.start();

            byte[] buffer = new byte[BUFFER_SIZE];

            while (!isPaused) {
                try {
                    int bytesRead = vorbisStream.readPcm(buffer, 0, buffer.length);
                    if (bytesRead <= 0) break;

                    line.write(buffer, 0, bytesRead);

                } catch (Exception e) {
                    System.err.println("Error during playback: " + e.getMessage());
                    break;
                }
            }

            line.drain();
            line.stop();
            line.close();

        } catch (IOException | LineUnavailableException e) {
            log.error("Error playing audio", e);
        } finally {
            // Clean up resources
            if (randomAccessFile != null) {
                try {
                    randomAccessFile.close();
                } catch (IOException e) {
                    log.error("Error closing audio stream", e);
                }
            }
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
        }
    }

    public void pause() {
        isPaused = true;
    }

    public void resume() {
        isPaused = false;
    }

}