package me.pacphi.converse.assemblyai;

import me.pacphi.converse.AudioTranscriptionService;
import me.pacphi.converse.assemblyai.api.AssemblyAiApi;
import me.pacphi.converse.assemblyai.api.AssemblyAiApiException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;

public class AssemblyAiService implements AudioTranscriptionService {

    private final AssemblyAiApi assemblyAiApi;

    public AssemblyAiService(AssemblyAiApi assemblyAiApi) {
        this.assemblyAiApi = assemblyAiApi;
    }

    @Override
    public String transcribe(byte[] audio) {
        try (InputStream inputStream = new ByteArrayInputStream(audio)) {
            return assemblyAiApi.speechToText(inputStream).getText().orElseThrow();
        } catch (IOException | NoSuchElementException e) {
            throw new AssemblyAiApiException("Trouble processing AssemblyAI API request", e);
        }
    }
}
