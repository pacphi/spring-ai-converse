package me.pacphi.converse;

public abstract class ConverseService {

    private final AudioTranscriptionService transcriptionService;
    private final ChatInquiryService inquiryService;
    private final TextToSpeechService speechService;

    protected ConverseService(
            AudioTranscriptionService transcriptionService,
            ChatInquiryService inquiryService,
            TextToSpeechService speechService
    ) {
        this.transcriptionService = transcriptionService;
        this.inquiryService = inquiryService;
        this.speechService = speechService;
    }

    public abstract AudioResponse respond(String textInquiry);

    public abstract AudioResponse respond(byte[] audibleInquiry);

    protected AudioTranscriptionService getAudioTranscriptionService() {
        return this.transcriptionService;
    }

    protected ChatInquiryService getChatInquiryService() {
        return this.inquiryService;
    }

    protected TextToSpeechService getTextToSpeechService() {
        return this.speechService;
    }
}
