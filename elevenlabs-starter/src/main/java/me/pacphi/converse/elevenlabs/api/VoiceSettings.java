package me.pacphi.converse.elevenlabs.api;

import java.util.List;

/**
 * Voice settings that can override stored settings for a voice.
 */
public record VoiceSettings(
        Double stability,
        Double similarityBoost,
        Double style,
        Boolean useSpeakerBoost,
        List<PronunciationDictionary> pronunciationDictionaries
) {}
