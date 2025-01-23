package me.pacphi.converse.picovoice.api;

import ai.picovoice.leopard.Leopard;
import ai.picovoice.leopard.LeopardException;
import ai.picovoice.leopard.LeopardTranscript;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class LeopardApi {

    private static final Logger log = LoggerFactory.getLogger(LeopardApi.class);

    private final String accessKey;
    private final String modelPath;
    private final boolean enableAutomaticPunctuation;
    private final boolean enableDiarization;
    private final boolean verbose;

    public LeopardApi(
            String accessKey, String modelPath,
            boolean enableAutomaticPunctuation, boolean enableDiarization,
            boolean verbose) {
        this.accessKey = accessKey;
        this.modelPath = modelPath;
        this.enableAutomaticPunctuation = enableAutomaticPunctuation;
        this.enableDiarization = enableDiarization;
        this.verbose = verbose;
    }

    public String transcribe(InputStream audioStream) {
        File tempDir = new File(System.getProperty("java.io.tmpdir"), "leopard_" + System.currentTimeMillis());
        tempDir.mkdirs();
        File audioFile = new File(tempDir, "audio.wav");

        try {
            java.nio.file.Files.copy(audioStream, audioFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new LeopardApiException("Could not load audio input", e);
        }

        Leopard leopard = null;
        try {
            Leopard.Builder builder = new Leopard.Builder()
                    .setAccessKey(accessKey)
                    .setEnableAutomaticPunctuation(enableAutomaticPunctuation)
                    .setEnableDiarization(enableDiarization);
            if (modelPath != null && !modelPath.isBlank()) {
                   builder.setModelPath(modelPath);
            }
            leopard = builder.build();

            StringBuilder result = new StringBuilder();
            LeopardTranscript transcript = leopard.processFile(audioFile.getPath());
            result.append(transcript.getTranscriptString());
            if (verbose) {
                LeopardTranscript.Word[] words = transcript.getWordArray();
                result.append(String.format(
                        "%14s | %5s | %5s | %10s | %11s\n",
                        "word",
                        "start",
                        "end",
                        "confidence",
                        "speaker tag")
                );
                for (int i = 0; i < words.length; i++) {
                    result.append(String.format(
                            "%2d: %10s | %5.2f | %5.2f | %10.2f | %11d\n",
                            i,
                            words[i].getWord(),
                            words[i].getStartSec(),
                            words[i].getEndSec(),
                            words[i].getConfidence(),
                            words[i].getSpeakerTag())
                    );
                }
            }
            return result.toString();

        } catch (LeopardException e) {
            throw new LeopardApiException("Failed to process audio transcription", e);
        } finally {
            if (leopard != null) {
                leopard.delete();
            }
            if (audioFile.exists()) {
                audioFile.delete();
            }
            if (tempDir.exists()) {
                tempDir.delete();
            }
        }

    }
}