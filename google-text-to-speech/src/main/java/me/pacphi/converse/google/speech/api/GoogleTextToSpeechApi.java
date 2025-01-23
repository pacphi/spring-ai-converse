package me.pacphi.converse.google.speech.api;

import com.google.cloud.texttospeech.v1.*;
import com.google.protobuf.ByteString;

import java.io.IOException;

public class GoogleTextToSpeechApi {

    /**
     * Converts text to speech using specified gender and parameters
     *
     * @param languageCode a valid java.util.Locale#languageTag, e.g., en-US
     * @param gender one of com.google.cloud.texttospeech.v1.SsmlVoiceGender, e.g., NEUTRAL
     * @param audioEncoding one of com.google.cloud.texttospeech.v1.AudioEncoding, e.g., MP3
     * @param text the text to convert to speech
     * @return a protobuf encapsulating the audio bytes
     */
    public ByteString textToSpeech(String languageCode,
                                   SsmlVoiceGender gender,
                                   AudioEncoding audioEncoding,
                                   String text) {
        try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create()) {
            SynthesisInput input = SynthesisInput.newBuilder().setText(text).build();
            VoiceSelectionParams voice =
                    VoiceSelectionParams.newBuilder()
                            .setLanguageCode(languageCode)
                            .setSsmlGender(gender)
                            .build();
            AudioConfig audioConfig =
                    AudioConfig.newBuilder().setAudioEncoding(audioEncoding).build();
            SynthesizeSpeechResponse response =
                    textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);

            return response.getAudioContent();
        } catch (IOException e) {
            throw new GoogleTextToSpeechApiException("Google Text-to-Speech API request failed", e);
        }
    }
}
