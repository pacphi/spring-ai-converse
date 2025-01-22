package me.pacphi.converse.google.transcribe;

import me.pacphi.converse.google.transcribe.api.GoogleSpeechToTextApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "spring.google.speech-to-text.enabled", havingValue = "true")
public class GoogleSpeechToTextConfig {

    @Bean
    public GoogleSpeechToTextApi googleSpeechToTextApi(
            @Value("${spring.google.speech-to-text.project}") String projectId,
            @Value("${spring.google.speech-to-text.defaults.language-code}") String languageCode,
            @Value("${spring.google.speech-to-text.defaults.model}") String model) {
        return new GoogleSpeechToTextApi(projectId, languageCode, model);
    }

    @Bean
    public GoogleSpeechToTextService googleSpeechToTextService(GoogleSpeechToTextApi googleSpeechToTextApi) {
        return new GoogleSpeechToTextService(googleSpeechToTextApi);
    }
}
