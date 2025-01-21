package me.pacphi.converse.elevenlabs.api;

/**
 * Defines a runtime exception attributable to an ElevenLabs API invocation+
 */
public class ElevenLabsApiException extends RuntimeException {

    public ElevenLabsApiException(String message) {
        super(message);
    }

    public ElevenLabsApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
