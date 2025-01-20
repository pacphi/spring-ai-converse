package me.pacphi.converse.elevenlabs.api;

/**
 * Locator for pronunciation dictionaries.
 * Up to 3 can be applied per request.
 */
public record PronunciationDictionary(
        String id,
        String versionId
) {}
