package me.pacphi.converse.elevenlabs;

import me.pacphi.converse.AudioResponse;
import me.pacphi.converse.elevenlabs.api.OutputFormat;
import me.pacphi.converse.elevenlabs.api.TextToSpeechRequest;
import me.pacphi.converse.util.Mp3Player;
import me.pacphi.converse.util.PcmPlayer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Profile("elevenlabs")
@RestController
@RequestMapping("/api/elevenlabs")
public class ElevenLabsController {

    private final String voiceId;
    private final ElevenLabsService elevenLabsService;

    public ElevenLabsController(@Value("${spring.ai.elevenlabs.defaults.voiceId}") String voiceId, ElevenLabsService elevenLabsService) {
        this.voiceId = voiceId;
        this.elevenLabsService = elevenLabsService;
    }

    @PostMapping(value = "/speak")
    public ResponseEntity<?> speak(
            @RequestBody TextToSpeechRequest request,
            @RequestParam(required = false, defaultValue = "true") Boolean enableLogging,
            @RequestParam(required = false, defaultValue = "MP3_44100_64") OutputFormat outputFormat,
            @RequestParam(required = false, defaultValue = "false") Boolean toFile) {
        return speak(voiceId, request, enableLogging, outputFormat, toFile);
    }

    @PostMapping(value = "/speak/{voiceId}")
    public ResponseEntity<?> speak(
            @PathVariable String voiceId,
            @RequestBody TextToSpeechRequest request,
            @RequestParam(required = false, defaultValue = "true") Boolean enableLogging,
            @RequestParam(required = false, defaultValue = "MP3_44100_64") OutputFormat outputFormat,
            @RequestParam(required = false, defaultValue = "false") Boolean toFile) {

        AudioResponse response = elevenLabsService.speak(voiceId, request, enableLogging, outputFormat);
        byte[] audioData = Base64.getDecoder().decode(response.audioBase64());

        if (toFile) {
            String filename = generateFilename(request.text(), outputFormat);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .contentType(getMediaType(outputFormat))
                    .contentLength(audioData.length)
                    .body(audioData);
        }

        if (outputFormat.isMp3Compatible())
            Mp3Player.create().play(audioData);
        if (outputFormat.isPcmCompatible() || outputFormat.isUlawCompatible())
            PcmPlayer.create().play(audioData, outputFormat.getAudioFormat());
        return ResponseEntity.ok(response);
    }

    private String generateFilename(String text, OutputFormat format) {
        // Create a filename-safe version of the text (first few words)
        String safeText = text.substring(0, Math.min(text.length(), 30))
                .replaceAll("[^a-zA-Z0-9]", "_")
                .replaceAll("_+", "_")
                .toLowerCase();

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        return String.format("%s_%s.%s", safeText, timestamp, getFileExtension(format));
    }

    private String getFileExtension(OutputFormat format) {
        if (format.isMp3Compatible()) {
            return "mp3";
        } else if (format.isPcmCompatible()) {
            return "wav";
        } else if (format.isUlawCompatible()) {
            return "ulaw";
        }
        throw new IllegalStateException("Unknown format: " + format.name());
    }

    private MediaType getMediaType(OutputFormat format) {
        if (format.isMp3Compatible()) {
            return MediaType.parseMediaType("audio/mpeg");
        } else if (format.isPcmCompatible()) {
            return MediaType.parseMediaType("audio/wav");
        } else if (format.isUlawCompatible()) {
            return MediaType.parseMediaType("audio/basic");
        }
        throw new IllegalStateException("Unknown format: " + format.name());
    }
}
