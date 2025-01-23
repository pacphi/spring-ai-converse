package me.pacphi.converse.google;

import me.pacphi.converse.google.transcribe.GoogleSpeechToTextService;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Profile("google")
@RestController
@RequestMapping("/api/google")
public class GoogleSpeechToTextController {

    private final GoogleSpeechToTextService googleSpeechToTextService;

    public GoogleSpeechToTextController(GoogleSpeechToTextService googleSpeechToTextService) {
        this.googleSpeechToTextService = googleSpeechToTextService;
    }

    @PostMapping(value = "/transcribe", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<String> transcribe(@RequestBody byte[] audio,
                                             @RequestParam(required = false) String languageCode,
                                             @RequestParam(required = false) String model) {
        return ResponseEntity.ok(googleSpeechToTextService.transcribe(languageCode, model, audio));
    }
}
