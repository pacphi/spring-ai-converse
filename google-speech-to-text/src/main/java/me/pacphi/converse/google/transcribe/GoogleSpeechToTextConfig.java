package me.pacphi.converse.google.transcribe;

import me.pacphi.converse.google.transcribe.api.GoogleSpeechToTextApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "spring.ai.google.speech-to-text.enabled", havingValue = "true")
public class GoogleSpeechToTextConfig {

    @Bean
    public GoogleSpeechToTextApi googleSpeechToTextApi(
            @Value("${spring.ai.google.speech-to-text.project}") String projectId) {
        return new GoogleSpeechToTextApi(projectId);
    }

    @Bean
    public GoogleSpeechToTextService googleSpeechToTextService(@Value("${spring.ai.google.speech-to-text.defaults.language-code}") String languageCode,
                                                               @Value("${spring.ai.google.speech-to-text.defaults.model}") String model,
                                                               GoogleSpeechToTextApi googleSpeechToTextApi) {
        return new GoogleSpeechToTextService(languageCode, model, googleSpeechToTextApi);
    }
}
