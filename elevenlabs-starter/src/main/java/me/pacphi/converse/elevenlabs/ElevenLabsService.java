package me.pacphi.converse.elevenlabs;

import me.pacphi.converse.AudioResponse;
import me.pacphi.converse.TextToSpeechService;
import me.pacphi.converse.elevenlabs.api.ElevenLabsApi;
import me.pacphi.converse.elevenlabs.api.OutputFormat;
import me.pacphi.converse.elevenlabs.api.TextToSpeechRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import java.io.IOException;

public class ElevenLabsService implements TextToSpeechService {

    private static Logger log = LoggerFactory.getLogger(ElevenLabsService.class);
    private final ElevenLabsApi elevenLabsApi;

    public ElevenLabsService(ElevenLabsApi elevenLabsApi) {
        this.elevenLabsApi = elevenLabsApi;
    }

    @Override
    public AudioResponse speak(String text) {
        TextToSpeechRequest request = new TextToSpeechRequest.Builder(text).build();
        return speak(null, request, null, null);
    }

    public AudioResponse speak(String voiceId, TextToSpeechRequest request, Boolean enableLogging, OutputFormat outputFormat) {
        Resource resource = elevenLabsApi.textToSpeech(voiceId, request, enableLogging, outputFormat);
        try {
            byte[] audioData = resource.getContentAsByteArray();
            return new AudioResponse(request.text(), audioData);
        } catch (IOException e) {
            log.error("Trouble processing ElevenLabs API request", e);
            throw new RuntimeException(e);
        }
    }

}
