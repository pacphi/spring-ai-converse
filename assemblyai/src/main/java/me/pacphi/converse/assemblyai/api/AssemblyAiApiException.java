package me.pacphi.converse.assemblyai.api;

/**
 * Defines a runtime exception attributable to AssemblyAI API invocation
 */
public class AssemblyAiApiException extends RuntimeException {

    public AssemblyAiApiException(String message) {
        super(message);
    }

    public AssemblyAiApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
