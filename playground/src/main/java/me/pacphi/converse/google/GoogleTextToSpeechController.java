package me.pacphi.converse.google;

import com.google.cloud.texttospeech.v1.AudioEncoding;
import com.google.cloud.texttospeech.v1.SsmlVoiceGender;
import me.pacphi.converse.AudioResponse;
import me.pacphi.converse.elevenlabs.api.OutputFormat;
import me.pacphi.converse.elevenlabs.api.TextToSpeechRequest;
import me.pacphi.converse.google.speech.GoogleTextToSpeechService;
import me.pacphi.converse.util.Mp3Player;
import me.pacphi.converse.util.OggPlayer;
import me.pacphi.converse.util.PcmPlayer;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sound.sampled.AudioFormat;
import java.util.Base64;
import java.util.Set;

@Profile("google")
@RestController
@RequestMapping("/api/google")
public class GoogleTextToSpeechController {

    private final GoogleTextToSpeechService googleTextToSpeechService;

    public GoogleTextToSpeechController(GoogleTextToSpeechService googleTextToSpeechService) {
        this.googleTextToSpeechService = googleTextToSpeechService;
    }

    @PostMapping(value = "/speak")
    public ResponseEntity<AudioResponse> speak(
            @RequestBody String text,
            @RequestParam(required = false) String languageCode,
            @RequestParam(required = false) SsmlVoiceGender voiceGender,
            @RequestParam(required = false, defaultValue = "MP3") AudioEncoding audioEncoding) {
        AudioResponse response = googleTextToSpeechService.speak(languageCode, voiceGender, audioEncoding, text);
        UniversalPlayer.create().play(response, audioEncoding);
        return ResponseEntity.ok(response);
    }


}
