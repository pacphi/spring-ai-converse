package me.pacphi.converse.picovoice;

import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("picovoice")
@RestController
@RequestMapping("/api/picovoice/leopard")
public class LeopardController {

    private LeopardService leopardService;

    public LeopardController(LeopardService leopardService) {
        this.leopardService = leopardService;
    }

    @PostMapping(value = "/transcribe", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<String> transcribe(@RequestBody byte[] audio) {
        return ResponseEntity.ok(leopardService.transcribe(audio));
    }

}
