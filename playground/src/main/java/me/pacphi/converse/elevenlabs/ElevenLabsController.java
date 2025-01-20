package me.pacphi.converse.elevenlabs;

import me.pacphi.converse.AudioResponse;
import me.pacphi.converse.elevenlabs.api.OutputFormat;
import me.pacphi.converse.elevenlabs.api.TextToSpeechRequest;
import me.pacphi.converse.elevenlabs.util.Audio;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@Profile("elevenlabs")
@RestController
@RequestMapping("/api/elevenlabs")
public class ElevenLabsController {

    private final String voiceId;
    private final ElevenLabsService elevenLabsService;

    public ElevenLabsController(@Value("${spring.elevenlabs.defaults.voiceId}") String voiceId, ElevenLabsService elevenLabsService) {
        this.voiceId = voiceId;
        this.elevenLabsService = elevenLabsService;
    }

    @PostMapping(value = "/speak")
    public ResponseEntity<AudioResponse> speak(
            @RequestBody TextToSpeechRequest request,
            @RequestParam(required = false, defaultValue = "true") Boolean enableLogging,
            @RequestParam(required = false, defaultValue = "MP3_44100_64") OutputFormat outputFormat) {
        return speak(null, request, enableLogging, outputFormat);
    }

    @PostMapping(value = "/speak/{voiceId}")
    public ResponseEntity<AudioResponse> speak(
            @PathVariable String voiceId,
            @RequestBody TextToSpeechRequest request,
            @RequestParam(required = false, defaultValue = "true") Boolean enableLogging,
            @RequestParam(required = false, defaultValue = "MP3_44100_64") OutputFormat outputFormat) {
        AudioResponse response = elevenLabsService.speak(voiceId, request, enableLogging, outputFormat);
        Audio.playback(Base64.getDecoder().decode(response.audioBase64()));
        return ResponseEntity.ok(response);
    }
}
