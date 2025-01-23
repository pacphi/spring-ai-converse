
package me.pacphi.converse.picovoice.api;

public class LeopardApiException extends RuntimeException {

    public LeopardApiException(String message) {
        super(message);
    }

    public LeopardApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
