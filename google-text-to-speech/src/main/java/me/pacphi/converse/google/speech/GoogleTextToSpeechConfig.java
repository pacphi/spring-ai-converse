package me.pacphi.converse.google.speech;

import com.google.cloud.texttospeech.v1.AudioEncoding;
import com.google.cloud.texttospeech.v1.SsmlVoiceGender;
import me.pacphi.converse.google.speech.api.GoogleTextToSpeechApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "spring.ai.google.text-to-speech.enabled", havingValue = "true")
public class GoogleTextToSpeechConfig {

    @Bean
    public GoogleTextToSpeechApi googleTextToSpeechApi() {
        return new GoogleTextToSpeechApi();
    }

    @Bean GoogleTextToSpeechService googleTextToSpeechService(
            @Value("${spring.ai.google.text-to-speech.defaults.language-code}") String languageCode,
            @Value("${spring.ai.google.text-to-speech.defaults.voice-gender}") SsmlVoiceGender voiceGender,
            @Value("${spring.ai.google.text-to-speech.defaults.audio-encoding}") AudioEncoding audioEncoding,
            GoogleTextToSpeechApi googleTextToSpeechApi) {
        return new GoogleTextToSpeechService(languageCode, voiceGender, audioEncoding, googleTextToSpeechApi);
    }
}
