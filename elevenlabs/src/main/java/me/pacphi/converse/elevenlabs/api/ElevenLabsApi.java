package me.pacphi.converse.elevenlabs.api;

import org.springframework.web.client.RestClient;
import org.springframework.http.MediaType;
import org.springframework.core.io.Resource;

/**
 * Client for the Elevenlabs Text to Speech API
 */
public class ElevenLabsApi {

    private final RestClient restClient;

    public ElevenLabsApi(String apiKey) {
        this.restClient = RestClient.builder()
                .baseUrl("https://api.elevenlabs.io/v1")
                .defaultHeader("xi-api-key", apiKey)
                .build();
    }

    /**
     * Converts text to speech using specified voice and parameters
     *
     * @param voiceId The ID of the voice to use, you can use https://api.elevenlabs.io/v1/voices to list all the available voices
     * @param request The text to speech request parameters
     * @param enableLogging When false, full privacy mode is used
     * @param outputFormat The desired output audio format
     * @return The generated audio as a Resource
     */
    public Resource textToSpeech(
            String voiceId,
            TextToSpeechRequest request,
            Boolean enableLogging,
            OutputFormat outputFormat
    ) {
        final OutputFormat format = outputFormat != null ? outputFormat : OutputFormat.MP3_44100_64;
        final Boolean logging = enableLogging != null ? enableLogging : Boolean.TRUE;
        return restClient
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path("/text-to-speech/{voiceId}")
                        .queryParam("enable_logging", logging)
                        .queryParam("output_format", format.name().toLowerCase())
                        .build(voiceId))
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(Resource.class);
    }

}
