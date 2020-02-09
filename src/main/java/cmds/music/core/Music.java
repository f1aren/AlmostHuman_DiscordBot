package cmds.music.core;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import net.dv8tion.jda.api.entities.Guild;

import java.util.HashMap;
import java.util.Map;

public class Music extends Command {

    protected static AudioPlayerManager audioPlayerManager = new DefaultAudioPlayerManager();

    protected static final Map<Long, GuildMusicManager> musicManagers = new HashMap<>();

    @Override
    protected void execute(CommandEvent event) {
    }

    protected synchronized GuildMusicManager getGuildAudioPlayer(Guild guild) {
        long guildId = Long.parseLong(guild.getId());
        GuildMusicManager musicManager = musicManagers.get(guildId);

        if (musicManager == null) { // при первом запуске не находит в musicManagerS - запись
            musicManager = new GuildMusicManager(audioPlayerManager, guild);
            musicManagers.put(guildId, musicManager);
        }

        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

        return musicManager;
    }


}
