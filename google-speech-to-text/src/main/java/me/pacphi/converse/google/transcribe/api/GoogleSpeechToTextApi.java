package me.pacphi.converse.google.transcribe.api;

import com.google.api.gax.longrunning.OperationFuture;
import com.google.cloud.speech.v2.*;
import com.google.protobuf.ByteString;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class GoogleSpeechToTextApi {

    private final String projectId;

    public GoogleSpeechToTextApi(String projectId) {
        this.projectId = projectId;
    }

    public List<SpeechRecognitionResult> speechToText(String languageCode, String model, InputStream stream) {
        try (SpeechClient client = SpeechClient.create()) {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String recognizerId = "a" + uuid.substring(0, Math.min(61, uuid.length()));
            String parent = String.format("projects/%s/locations/global", projectId);
            RecognitionConfig recognitionConfig =
                    RecognitionConfig.newBuilder()
                        .setAutoDecodingConfig(AutoDetectDecodingConfig.newBuilder().build())
                        .setModel(model)
                        .addLanguageCodes(languageCode)
                        .build();
            Recognizer recognizer =
                    Recognizer.newBuilder().setDefaultRecognitionConfig(recognitionConfig).build();
            CreateRecognizerRequest createRecognizerRequest =
                    CreateRecognizerRequest.newBuilder()
                        .setParent(parent)
                        .setRecognizerId(recognizerId)
                        .setRecognizer(recognizer)
                        .build();
            OperationFuture<Recognizer, OperationMetadata> operationFuture =
                    client.createRecognizerAsync(createRecognizerRequest);
            recognizer = operationFuture.get();
            ByteString audioBytes = ByteString.readFrom(stream);
            RecognizeRequest request = RecognizeRequest.newBuilder()
                    .setConfig(recognitionConfig)
                    .setRecognizer(recognizer.getName())
                    .setContent(audioBytes)
                    .build();
            RecognizeResponse response = client.recognize(request);
            return response.getResultsList();
        } catch (ExecutionException | InterruptedException | IOException e) {
            throw new GoogleSpeechToTextApiException("Transcription request failed", e);
        }
    }

}
