package me.pacphi.converse.picovoice;

import me.pacphi.converse.AudioTranscriptionService;
import me.pacphi.converse.picovoice.api.LeopardApi;
import me.pacphi.converse.picovoice.api.LeopardApiException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class LeopardService implements AudioTranscriptionService {

    private final LeopardApi cheetahApi;

    public LeopardService(LeopardApi cheetahApi) {
        this.cheetahApi = cheetahApi;
    }

    @Override
    public String transcribe(byte[] audio) {
        try (InputStream inputStream = new ByteArrayInputStream(audio)) {
            return cheetahApi.transcribe(inputStream);
        } catch (IOException e) {
            throw new LeopardApiException("Trouble creating stream from audio bytes", e);
        }
    }
}