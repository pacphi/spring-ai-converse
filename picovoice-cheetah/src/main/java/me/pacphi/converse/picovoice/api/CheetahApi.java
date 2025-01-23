package me.pacphi.converse.picovoice.api;

import ai.picovoice.cheetah.Cheetah;
import ai.picovoice.cheetah.CheetahException;
import ai.picovoice.cheetah.CheetahTranscript;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class CheetahApi {

    private static final Logger log = LoggerFactory.getLogger(CheetahApi.class);

    private final String accessKey;
    private final String modelPath;
    private final boolean enableAutomaticPunctuation;

    public CheetahApi(String accessKey, String modelPath, boolean enableAutomaticPunctuation) {
        this.accessKey = accessKey;
        this.modelPath = modelPath;
        this.enableAutomaticPunctuation = enableAutomaticPunctuation;
    }

    public String transcribe(InputStream audioStream) {
        AudioInputStream audioInputStream;
        File tempDir = new File(System.getProperty("java.io.tmpdir"), "cheetah_" + System.currentTimeMillis());
        tempDir.mkdirs();
        File audioFile = new File(tempDir, "audio.wav");

        try {
            java.nio.file.Files.copy(audioStream, audioFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            audioInputStream = AudioSystem.getAudioInputStream(audioFile);
        } catch (UnsupportedAudioFileException e) {
            throw new CheetahApiException("Audio format not supported. Please provide an input file of .au, .aiff or .wav format");
        } catch (IOException e) {
            throw new CheetahApiException("Could not load audio input", e);
        }

        Cheetah cheetah = null;
        StringBuilder transcript = new StringBuilder();
        try {
            Cheetah.Builder builder = new Cheetah.Builder()
                    .setAccessKey(accessKey)
                    .setEnableAutomaticPunctuation(enableAutomaticPunctuation);
            if (modelPath != null && !modelPath.isBlank()) {
                builder.setModelPath(modelPath);
            }
            cheetah = builder.build();

            AudioFormat audioFormat = audioInputStream.getFormat();

            if (audioFormat.getSampleRate() != 16000.0f || audioFormat.getSampleSizeInBits() != 16) {
                throw new IllegalArgumentException(String.format("Invalid input audio file format. " +
                        "Input file must be a %dkHz, 16-bit audio file.", cheetah.getSampleRate()));
            }

            if (audioFormat.getChannels() > 1) {
                log.warn("Picovoice processes single-channel audio, but a multi-channel file was provided. " +
                        "Processing leftmost channel only.");
            }

            int frameIndex = 0;
            short[] cheetahFrame = new short[cheetah.getFrameLength()];

            ByteBuffer sampleBuffer = ByteBuffer.allocate(audioFormat.getFrameSize());
            sampleBuffer.order(ByteOrder.LITTLE_ENDIAN);
            while (audioInputStream.available() != 0) {
                int numBytesRead = audioInputStream.read(sampleBuffer.array());
                if (numBytesRead < 2) {
                    break;
                }

                cheetahFrame[frameIndex++] = sampleBuffer.getShort(0);

                if (frameIndex == cheetahFrame.length) {
                    CheetahTranscript transcriptObj = cheetah.process(cheetahFrame);
                    transcript.append(transcriptObj.getTranscript());
                    frameIndex = 0;
                }
            }

            CheetahTranscript endpointTranscriptObj = cheetah.flush();
            transcript.append(endpointTranscriptObj.getTranscript());
            return transcript.toString();
        } catch (CheetahException | IOException e) {
            throw new CheetahApiException("Failed to process audio transcription", e);
        } finally {
            if (cheetah != null) {
                cheetah.delete();
            }
            if (audioFile.exists()) {
                audioFile.delete();
            }
            if (tempDir.exists()) {
                tempDir.delete();
            }
            if (audioInputStream != null) {
                try {
                    audioInputStream.close();
                } catch (IOException e) {
                    throw new CheetahApiException("Failed to close audio input stream", e);
                }
            }
        }
    }
}