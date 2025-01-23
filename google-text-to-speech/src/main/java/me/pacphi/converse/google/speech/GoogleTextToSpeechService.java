package me.pacphi.converse.google.speech;

import com.google.cloud.texttospeech.v1.AudioEncoding;
import com.google.cloud.texttospeech.v1.SsmlVoiceGender;
import com.google.protobuf.ByteString;
import me.pacphi.converse.AudioResponse;
import me.pacphi.converse.TextToSpeechService;
import me.pacphi.converse.google.speech.api.GoogleTextToSpeechApi;

public class GoogleTextToSpeechService implements TextToSpeechService {

    private final String defaultLanguageCode;
    private final SsmlVoiceGender defaultVoiceGender;
    private final AudioEncoding defaultAudioEncoding;
    private final GoogleTextToSpeechApi googleTextToSpeechApi;

    public GoogleTextToSpeechService(
            String defaultLanguageCode, SsmlVoiceGender defaultVoiceGender,
            AudioEncoding defaultAudioEncoding, GoogleTextToSpeechApi googleTextToSpeechApi) {
        this.defaultLanguageCode = defaultLanguageCode;
        this.defaultVoiceGender = defaultVoiceGender;
        this.defaultAudioEncoding = defaultAudioEncoding;
        this.googleTextToSpeechApi = googleTextToSpeechApi;
    }

    @Override
    public AudioResponse speak(String text) {
        return speak(null, null, null, text);
    }

    public AudioResponse speak(
            String languageCode, SsmlVoiceGender voiceGender,
            AudioEncoding audioEncoding, String text) {
        ByteString audioContent = googleTextToSpeechApi.textToSpeech(
                languageCode == null ? defaultLanguageCode: languageCode,
                voiceGender == null ? defaultVoiceGender: voiceGender,
                audioEncoding == null ? defaultAudioEncoding: audioEncoding,
                text);
        return new AudioResponse(text, audioContent.toByteArray());
    }
}
