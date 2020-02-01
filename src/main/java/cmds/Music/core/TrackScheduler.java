package cmds.Music.core;

import Core.Keys;
import Core.Main;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.api.entities.Guild;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason.*;

/**
 * This class schedules tracks for the audio player. It contains the queue of tracks.
 */
public class TrackScheduler extends AudioEventAdapter {
    //public static
    private AudioPlayer player;
    private BlockingQueue<AudioTrack> queue;
    private Guild guild = null;

    /**
     * @param player The audio player this scheduler uses
     */
    public TrackScheduler(AudioPlayer player, Guild guild) {
        this.player = player;
        this.queue = new LinkedBlockingQueue<>();
        this.guild = guild;
    }

    /**
     * Add the next track to queue or play right away if nothing is in the queue.
     *
     * @param track The track to play or add to queue.
     */
    public void queue(AudioTrack track) {
        // Calling startTrack with the noInterrupt set to true will start the track only if nothing is currently playing. If
        // something is playing, it returns false and does nothing. In that case the player was already playing so this
        // track goes to the queue instead.
        if (!player.startTrack(track, true)) {
            queue.offer(track);
        }
    }

    /**
     * Start the next track, stopping the current one if it is playing.
     */
    public void nextTrack() {

        //player.stopTrack();

        if (queue.peek() != null) { // проверяем, есть ли в очереди следующий трек
            player.startTrack(queue.poll(), false);
        } else {
            stopTrack();
            disconnectFromVoice();
        }

    }

   /* public BlockingQueue<AudioTrack> getQueue() {
        return queue;
    }*/

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        // endReason == FINISHED: A track finished or died by an exception (mayStartNext = true).
        // endReason == LOAD_FAILED: Loading of a track failed (mayStartNext = true).
        // endReason == STOPPED: The player was stopped.
        // endReason == REPLACED: Another track started playing while this had not finished
        // endReason == CLEANUP: Player hasn't been queried for a while, if you want you can put a
        //                       clone of this back to your queue

        // Only start the next track if the end reason is suitable for it (FINISHED or LOAD_FAILED)

        if (endReason.mayStartNext) {
            nextTrack();

            //System.out.println("endReason.mayStartNext" + queue.peek());
            /*if (queue.peek() != null) {
                nextTrack();
            } else {
                //System.out.println("player.destroy");
                stopTrack();
                disconnectFromVoice();
            }*/


        }


    }


    public void stopTrack() {
        //player.stopTrack();
        player.destroy();
        queue.clear();
    }

    public String getCurrentTrack() {
        String track;
        long hour, min, sec, ms;
        ms = player.getPlayingTrack().getPosition();
        hour = TimeUnit.MILLISECONDS.toHours(ms);
        min = TimeUnit.MILLISECONDS.toMinutes(ms) % 60;
        sec = TimeUnit.MILLISECONDS.toSeconds(ms) % 60;

        String currentTimePosition = String.format("[%02dч:%02dм:%02dс]", hour, min, sec);

        track = player.getPlayingTrack().getInfo().title + " " + currentTimePosition + " "
                + " \n" + player.getPlayingTrack().getInfo().uri;
        return track;
    }

    public void setVolume(int value) {

        if (value > 150) {
            value = 150;
        } else if (value < 0) {
            value = 0;
        }

        player.setVolume(value);
    }

    public String getQueueList() {
        String tracklist = new String();
        //queue.
       // queue.element().
        for (AudioTrack track : queue) {
            tracklist += track.getInfo().title + "\n";
        }
        return tracklist;
    }

    public AudioPlayer getPlayer() {
        return player;
    }

    private void disconnectFromVoice() {
        //System.out.println(guild.getAudioManager());
        if (guild.getAudioManager().isConnected()) {
            guild.getAudioManager().closeAudioConnection();

        }
    }

    /*private void connectToVoice() {
        if (getGuild().getAudioManager().isConnected()) {
            getGuild().getAudioManager().closeAudioConnection();
            //for (VoiceChannel voiceChannel : audioManager.getGuild().getVoiceChannels()) {
            //    audioManager.openAudioConnection(voiceChannel);
            //    break;
            //}
        }
    }*/
}