package me.pacphi.converse.picovoice;

import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Profile("picovoice")
@RestController
@RequestMapping("/api/picovoice")
public class CheetahController {

    private CheetahService cheetahService;

    public CheetahController(CheetahService cheetahService) {
        this.cheetahService = cheetahService;
    }

    @PostMapping(value = "/transcribe", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<String> transcribe(@RequestBody byte[] audio) {
        return ResponseEntity.ok(cheetahService.transcribe(audio));
    }

}
