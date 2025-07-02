package me.pacphi.converse.picovoice;

import me.pacphi.converse.picovoice.api.LeopardApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "spring.ai.picovoice.leopard.enabled", havingValue = "true")
public class LeopardConfig {

    @Bean
    public LeopardApi leopardApi(
            @Value("${spring.ai.picovoice.leopard.access-key}") String accessKey,
            @Value("${spring.ai.picovoice.leopard.defaults.model-path}") String modelPath,
            @Value("${spring.ai.picovoice.leopard.defaults.enable-automatic-punctuation}") boolean enableAutomaticPunctuation,
            @Value("${spring.ai.picovoice.leopard.defaults.enable-diarization}") boolean enableDiarization,
            @Value("${spring.ai.picovoice.leopard.defaults.verbose}") boolean verbose) {
        return new LeopardApi(accessKey, modelPath, enableAutomaticPunctuation, enableDiarization, verbose);
    }

    @Bean
    public LeopardService leopardService(LeopardApi leopardApi) {
        return new LeopardService(leopardApi);
    }
}
