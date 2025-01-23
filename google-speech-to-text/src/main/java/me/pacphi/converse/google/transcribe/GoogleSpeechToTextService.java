package me.pacphi.converse.google.transcribe;

import com.google.cloud.speech.v2.SpeechRecognitionAlternative;
import com.google.cloud.speech.v2.SpeechRecognitionResult;
import me.pacphi.converse.AudioTranscriptionService;
import me.pacphi.converse.google.transcribe.api.GoogleSpeechToTextApi;
import me.pacphi.converse.google.transcribe.api.GoogleSpeechToTextApiException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class GoogleSpeechToTextService implements AudioTranscriptionService {

    private final String defaultLanguageCode;
    private final String defaultModel;
    private final GoogleSpeechToTextApi googleSpeechToTextApi;

    public GoogleSpeechToTextService(String defaultLanguageCode, String defaultModel, GoogleSpeechToTextApi googleSpeechToTextApi) {
        this.googleSpeechToTextApi = googleSpeechToTextApi;
        this.defaultLanguageCode = defaultLanguageCode;
        this.defaultModel = defaultModel;
    }

    @Override
    public String transcribe(byte[] audio) {
        return transcribe(null, null, audio);
    }

    public String transcribe(String languageCode, String model, byte[] audio) {
        try (InputStream inputStream = new ByteArrayInputStream(audio)) {
            List<SpeechRecognitionResult> results =
                    googleSpeechToTextApi.speechToText(
                            languageCode == null ? defaultLanguageCode: languageCode,
                            model == null ? defaultModel: model,
                            inputStream);
            StringBuilder sb = new StringBuilder();
            for (SpeechRecognitionResult result : results) {
                if (result.getAlternativesCount() > 0) {
                    SpeechRecognitionAlternative alternative = result.getAlternativesList().getFirst();
                    sb.append(alternative.getTranscript());
                    sb.append(" ");
                }
            }
            return sb.toString().trim();
        } catch (IOException e) {
            throw new GoogleSpeechToTextApiException("Trouble creating stream from audio bytes", e);
        }
    }
}
