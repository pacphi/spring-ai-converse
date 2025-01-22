package me.pacphi.converse.google.transcribe.api;

/**
 * Defines a runtime exception attributable to Google's Speech to Text API invocation
 */
public class GoogleSpeechToTextApiException extends RuntimeException {

    public GoogleSpeechToTextApiException(String message) {
        super(message);
    }

    public GoogleSpeechToTextApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
