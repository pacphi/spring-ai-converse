package me.pacphi.converse.google;

import com.google.cloud.texttospeech.v1.AudioEncoding;
import me.pacphi.converse.AudioResponse;
import me.pacphi.converse.util.Mp3Player;
import me.pacphi.converse.util.OggPlayer;
import me.pacphi.converse.util.PcmPlayer;

import javax.sound.sampled.AudioFormat;
import java.util.Base64;
import java.util.Set;

class UniversalPlayer {

    private Set<AudioEncoding> mp3CompatibleEncodings = Set.of(AudioEncoding.MP3);
    private Set<AudioEncoding> oggCompatibleEncodings = Set.of(AudioEncoding.OGG_OPUS);
    private Set<AudioEncoding> pcmCompatibleEncodings = Set.of(AudioEncoding.LINEAR16, AudioEncoding.MULAW, AudioEncoding.ALAW, AudioEncoding.PCM);

    static UniversalPlayer create() {
        return new UniversalPlayer();
    }

    void play(AudioResponse response, AudioEncoding audioEncoding) {
        if (mp3CompatibleEncodings.contains(audioEncoding)) {
            Mp3Player.create().play(Base64.getDecoder().decode(response.audioBase64()));
        } else if (oggCompatibleEncodings.contains(audioEncoding)) {
            OggPlayer.create().play(Base64.getDecoder().decode(response.audioBase64()));
        } else if (pcmCompatibleEncodings.contains(audioEncoding)) {
            PcmPlayer.create().play(
                    Base64.getDecoder().decode(response.audioBase64()),
                    new AudioFormat(
                            8000,
                            16,
                            1,
                            true,
                            false
                    )
            );
        }
    }
}
