package me.pacphi.converse.elevenlabs.api;

/**
 * Controls how text normalization is applied.
 */
public enum TextNormalization {
    /**
     * System automatically decides whether to apply text normalization
     */
    AUTO,

    /**
     * Always apply text normalization
     */
    ON,

    /**
     * Skip text normalization
     */
    OFF
}
