package cmds.music;

import core.Main;
import cmds.music.core.GuildMusicManager;
import cmds.music.core.Music;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class MplayCommand extends Music {

    public MplayCommand () {
        this.name = "mplay";
        this.help = "поставить музыку (YouTube, BandCamp)";
        this.arguments = "<URL трека или плейлиста / название трека из YouTube>";
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
        this.guildOnly = true;

        AudioSourceManagers.registerRemoteSources(audioPlayerManager);

        //audioPlayerManager.getConfiguration().setResamplingQuality(AudioConfiguration.ResamplingQuality.LOW);

        //audioPlayerManager.registerSourceManager(new HttpAudioSourceManager());
        //audioPlayerManager.registerSourceManager(new LocalAudioSourceManager());

    }

    @Override
    protected void execute(CommandEvent event) {
        if (!event.getArgs().isEmpty()) {
            Member member = event.getGuild().getMember(event.getAuthor());

            if (member.getVoiceState().inVoiceChannel()) {
                long voiceId = member.getVoiceState().getChannel().getIdLong();
                VoiceChannel voiceChannel = event.getGuild().getVoiceChannelById(voiceId);

                // если не ссылка, то добавляем префикс поиска в yt
                loadAndPlay(event.getTextChannel(), event.getArgs().startsWith("http") ? event.getArgs() : "ytsearch: " + event.getArgs(), voiceChannel);
            } else {
                event.reply(member.getAsMention() + " ты сейчас не находишься в голосовом канале");
            }

        } else {
            event.reply(Main.getPrefix() + this.name + " " + this.arguments);
        }
    }


    private void loadAndPlay(final TextChannel channel, final String trackUrl, VoiceChannel voiceChannel) {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());

        audioPlayerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                //channel.sendMessage("trackLoaded");

                play(channel.getGuild(), musicManager, track, voiceChannel);

                channel.sendMessage("Добавил в очередь: " + track.getInfo().title).queue();

            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                /*AudioTrack firstTrack = playlist.getSelectedTrack();

                if (firstTrack == null) {
                    firstTrack = playlist.getTracks().get(0);
                }*/

                if(trackUrl.startsWith("ytsearch: ")) { // если происходит поиск трека по названию, то берем первый по списку и добавляем его в очередь
                    play(channel.getGuild(), musicManager, playlist.getTracks().get(0), voiceChannel);
                    channel.sendMessage("Добавил в очередь: " + playlist.getTracks().get(0).getInfo().title).queue();
                } else {
                    playPlaylist(channel.getGuild(), musicManager, playlist, voiceChannel);
                    channel.sendMessage("Добавил в очередь плейлист: " + playlist.getName()).queue();
                }

            }

            @Override
            public void noMatches() {
                channel.sendMessage("Не найдено: " + trackUrl).queue();
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                channel.sendMessage("Ошибка: " + exception.getMessage()).queue();
            }
        });
    }

    private void play(Guild guild, GuildMusicManager musicManager, AudioTrack track, VoiceChannel voiceChannel) {
        connectToVoiceChannel(guild.getAudioManager(), voiceChannel);

        musicManager.scheduler.queue(track);

    }

    private void playPlaylist (Guild guild, GuildMusicManager musicManager, AudioPlaylist playlist, VoiceChannel voiceChannel) {
        connectToVoiceChannel(guild.getAudioManager(), voiceChannel);

        for (AudioTrack track : playlist.getTracks()) { // добавляем каждый трек плейлиста в очередь
            musicManager.scheduler.queue(track);
        }
    }

    private void connectToVoiceChannel(AudioManager audioManager, VoiceChannel voiceChannel) {
        if (!audioManager.isConnected() && !audioManager.isAttemptingToConnect()) {

            audioManager.openAudioConnection(voiceChannel);

        }
    }

    private static void connectToFirstVoiceChannel(AudioManager audioManager) {
        if (!audioManager.isConnected() && !audioManager.isAttemptingToConnect()) {
            for (VoiceChannel voiceChannel : audioManager.getGuild().getVoiceChannels()) {
                audioManager.openAudioConnection(voiceChannel);
                break;
            }
        }
    }
}
