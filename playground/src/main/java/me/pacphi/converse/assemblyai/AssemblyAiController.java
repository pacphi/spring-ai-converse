package me.pacphi.converse.assemblyai;

import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("assemblyai")
@RestController
@RequestMapping("/api/assemblyai")
public class AssemblyAiController {

    private final AssemblyAiService assemblyAiService;

    public AssemblyAiController(AssemblyAiService assemblyAiService) {
        this.assemblyAiService = assemblyAiService;
    }

    @PostMapping(value = "/transcribe", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<String> transcribe(@RequestBody byte[] audio) {
        return ResponseEntity.ok(assemblyAiService.transcribe(audio));
    }

}
