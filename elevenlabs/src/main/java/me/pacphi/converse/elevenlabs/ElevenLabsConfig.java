package me.pacphi.converse.elevenlabs;

import me.pacphi.converse.elevenlabs.api.ElevenLabsApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "spring.ai.elevenlabs.enabled", havingValue = "true")
public class ElevenLabsConfig {

    @Bean
    public ElevenLabsApi elevenLabsApi(@Value("${spring.ai.elevenlabs.api-key}") String apiKey) {
        return new ElevenLabsApi(apiKey);
    }

    @Bean
    public ElevenLabsService elevenLabsService(ElevenLabsApi elevenLabsApi) {
        return new ElevenLabsService(elevenLabsApi);
    }
}
