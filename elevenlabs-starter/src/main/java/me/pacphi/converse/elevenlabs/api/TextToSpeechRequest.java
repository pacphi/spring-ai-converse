package me.pacphi.converse.elevenlabs.api;

import java.util.List;

/**
 * Request object for the Elevenlabs Text to Speech API.
 * Contains all possible parameters for the text-to-speech conversion.
 */
public record TextToSpeechRequest(
    /**
     * The text that will get converted into speech.
     */
    String text,

    /**
     * Identifier of the model that will be used.
     * Can be queried using GET /v1/models.
     * The model needs to have support for text to speech (can_do_text_to_speech property).
     */
    String modelId,

    /**
     * Language code (ISO 639-1) used to enforce a language for the model.
     * Currently only supported by Turbo v2.5.
     */
    String languageCode,

    /**
     * Voice settings overriding stored settings for the given voice.
     * Applied only on the given request.
     */
    VoiceSettings voiceSettings,

    /**
     * If specified, system will attempt to generate deterministically.
     * Must be between 0 and 4294967295.
     */
    Long seed,

    /**
     * Text that came before the current request.
     * Used to improve prosody flow when concatenating generations.
     */
    String previousText,

    /**
     * Text that comes after the current request.
     * Used to improve prosody flow when concatenating generations.
     */
    String nextText,

    /**
     * Request IDs of previously generated samples.
     * Maximum of 3 IDs allowed.
     */
    List<String> previousRequestIds,

    /**
     * Request IDs of next generated samples.
     * Maximum of 3 IDs allowed.
     */
    List<String> nextRequestIds,

    /**
     * Controls text normalization behavior.
     */
    TextNormalization textNormalization
) {
    /**
     * Builder pattern implementation for TextToSpeechRequest
     */
    public static class Builder {
        private String text;
        private String modelId = "eleven_monolingual_v1"; // default value
        private String languageCode;
        private VoiceSettings voiceSettings;
        private Long seed;
        private String previousText;
        private String nextText;
        private List<String> previousRequestIds;
        private List<String> nextRequestIds;
        private TextNormalization textNormalization = TextNormalization.AUTO; // default value

        public Builder(String text) {
            this.text = text;
        }

        public Builder modelId(String modelId) {
            this.modelId = modelId;
            return this;
        }

        public Builder languageCode(String languageCode) {
            this.languageCode = languageCode;
            return this;
        }

        public Builder voiceSettings(VoiceSettings voiceSettings) {
            this.voiceSettings = voiceSettings;
            return this;
        }

        public Builder seed(Long seed) {
            this.seed = seed;
            return this;
        }

        public Builder previousText(String previousText) {
            this.previousText = previousText;
            return this;
        }

        public Builder nextText(String nextText) {
            this.nextText = nextText;
            return this;
        }

        public Builder previousRequestIds(List<String> previousRequestIds) {
            this.previousRequestIds = previousRequestIds;
            return this;
        }

        public Builder nextRequestIds(List<String> nextRequestIds) {
            this.nextRequestIds = nextRequestIds;
            return this;
        }

        public Builder textNormalization(TextNormalization textNormalization) {
            this.textNormalization = textNormalization;
            return this;
        }

        public TextToSpeechRequest build() {
            return new TextToSpeechRequest(
                    text, modelId, languageCode, voiceSettings, seed,
                    previousText, nextText, previousRequestIds, nextRequestIds,
                    textNormalization
            );
        }

    }
}

