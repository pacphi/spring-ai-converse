package me.pacphi.converse.assemblyai;

import me.pacphi.converse.assemblyai.api.AssemblyAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "spring.assemblyai.enabled", havingValue = "true")
public class AssemblyAiConfig {

    @Bean
    public AssemblyAiApi assemblyAiApi(@Value("${spring.assemblyai.api-key}") String apiKey) {
        return new AssemblyAiApi(apiKey);
    }

    @Bean
    public AssemblyAiService assemblyAiService(AssemblyAiApi assemblyAiApi) {
        return new AssemblyAiService(assemblyAiApi);
    }
}