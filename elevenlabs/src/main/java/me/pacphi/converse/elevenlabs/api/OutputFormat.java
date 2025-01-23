package me.pacphi.converse.elevenlabs.api;

import javax.sound.sampled.*;

/**
 * Available output formats for the generated audio
 */
public enum OutputFormat {
    MP3_22050_32,
    MP3_44100_32,
    MP3_44100_64,
    MP3_44100_96,
    MP3_44100_128,
    MP3_44100_192,
    PCM_16000,
    PCM_22050,
    PCM_24000,
    PCM_44100,
    ULAW_8000;

    public boolean isMp3Compatible() {
        return this == MP3_22050_32 || this == MP3_44100_32 ||
                this == MP3_44100_64 || this == MP3_44100_96 ||
                this == MP3_44100_128 || this == MP3_44100_192;
    }

    public boolean isPcmCompatible() {
        return this == PCM_16000 || this == PCM_22050 ||
                this == PCM_24000 || this == PCM_44100;
    }

    public boolean isUlawCompatible() {
        return this == ULAW_8000;
    }

    public AudioFormat getAudioFormat() {
        if (isPcmCompatible()) {
            float sampleRate = Float.parseFloat(name().split("_")[1]);
            return new AudioFormat(
                    sampleRate,
                    16,
                    1,
                    true,
                    false
            );
        }

        if (isUlawCompatible()) {
            return new AudioFormat(
                    AudioFormat.Encoding.ULAW,
                    8000,
                    8,
                    1,
                    1,
                    8000,
                    false
            );
        }

        if (isMp3Compatible()) {
            String[] parts = name().split("_");
            float sampleRate = Float.parseFloat(parts[1]);
            return new AudioFormat(
                    sampleRate,
                    16,
                    2,
                    true,
                    false
            );
        }

        throw new IllegalStateException("Unknown format: " + name());
    }

    public int getSampleRate() {
        String[] parts = name().split("_");
        return Integer.parseInt(parts[1]);
    }

    public int getBitRate() {
        if (isMp3Compatible()) {
            String[] parts = name().split("_");
            return Integer.parseInt(parts[2]);
        }
        return 0;
    }
}