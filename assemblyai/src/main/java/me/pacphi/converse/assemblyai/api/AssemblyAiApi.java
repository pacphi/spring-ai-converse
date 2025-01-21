package me.pacphi.converse.assemblyai.api;

import com.assemblyai.api.AssemblyAI;
import com.assemblyai.api.resources.transcripts.types.Transcript;
import com.assemblyai.api.resources.transcripts.types.TranscriptStatus;

import java.io.IOException;
import java.io.InputStream;

/**
 * Client for AssemblyAI speech to text
 */
public class AssemblyAiApi {

    private final AssemblyAI client;

    public AssemblyAiApi(String apiKey) {
        this.client =
            AssemblyAI
                .builder()
                    .apiKey(apiKey)
                    .build();
    }

    public Transcript speechToText(String url) {
        Transcript transcript = client.transcripts().transcribe(url);
        if (transcript.getStatus() == TranscriptStatus.ERROR) {
            throw new AssemblyAiApiException("Transcript failed with error: " + transcript.getError().orElseThrow());
        }
        return transcript;
    }

    public Transcript speechToText(InputStream stream) {
        try {
            Transcript transcript = client.transcripts().transcribe(stream);
            if (transcript.getStatus() == TranscriptStatus.ERROR) {
                throw new AssemblyAiApiException("Transcription failed with error: " + transcript.getError().orElseThrow());
            }
            return transcript;
        } catch (IOException ioe) {
            throw new AssemblyAiApiException("Transcription request failed", ioe);
        }
    }
}
