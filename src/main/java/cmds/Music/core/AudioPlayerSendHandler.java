package cmds.Music.core;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame;
import net.dv8tion.jda.api.audio.AudioSendHandler;

import java.nio.ByteBuffer;

public class AudioPlayerSendHandler implements AudioSendHandler {
    private final AudioPlayer audioPlayer;
    private final MutableAudioFrame  frame;
    private final ByteBuffer buffer;

    public AudioPlayerSendHandler(AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
        this.buffer = ByteBuffer.allocate(1024);
        this.frame = new MutableAudioFrame();
        this.frame.setBuffer(buffer);
    }

    @Override
    public boolean canProvide() {
       //lastFrame = audioPlayer.provide();
       return audioPlayer.provide(frame);
    }

    @Override
    public ByteBuffer provide20MsAudio() {
       return ByteBuffer.wrap(frame.getData());
    }

    @Override
    public boolean isOpus() {
       return true;
    }
}