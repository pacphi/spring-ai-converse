package me.pacphi.converse.picovoice;

import me.pacphi.converse.AudioTranscriptionService;
import me.pacphi.converse.picovoice.api.CheetahApi;
import me.pacphi.converse.picovoice.api.CheetahApiException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CheetahService implements AudioTranscriptionService {

    private final CheetahApi cheetahApi;

    public CheetahService(CheetahApi cheetahApi) {
        this.cheetahApi = cheetahApi;
    }

    @Override
    public String transcribe(byte[] audio) {
        try (InputStream inputStream = new ByteArrayInputStream(audio)) {
            return cheetahApi.transcribe(inputStream);
        } catch (IOException e) {
            throw new CheetahApiException("Trouble creating stream from audio bytes", e);
        }
    }
}