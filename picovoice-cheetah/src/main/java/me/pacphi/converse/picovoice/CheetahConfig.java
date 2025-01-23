package me.pacphi.converse.picovoice;

import me.pacphi.converse.picovoice.api.CheetahApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "spring.picovoice.cheetah.enabled", havingValue = "true")
public class CheetahConfig {

    @Bean
    public CheetahApi cheetahApi(
            @Value("${spring.picovoice.cheetah.access-key}") String accessKey,
            @Value("${spring.picovoice.cheetah.defaults.model-path}") String modelPath,
            @Value("${spring.picovoice.cheetah.defaults.enable-automatic-punctuation}") boolean enableAutomaticPunctuation) {
        return new CheetahApi(accessKey, modelPath, enableAutomaticPunctuation);
    }

    @Bean
    public CheetahService cheetahService(CheetahApi cheetahApi) {
        return new CheetahService(cheetahApi);
    }
}
