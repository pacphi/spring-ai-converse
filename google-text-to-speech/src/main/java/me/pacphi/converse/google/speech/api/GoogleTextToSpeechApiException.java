package me.pacphi.converse.google.speech.api;

public class GoogleTextToSpeechApiException extends RuntimeException {

    public GoogleTextToSpeechApiException(String message) {
        super(message);
    }

    public GoogleTextToSpeechApiException(String message, Throwable cause) {
        super(message, cause);
    }
}