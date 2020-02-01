package cmds.Music;

import cmds.Music.core.GuildMusicManager;
import cmds.Music.core.Music;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.entities.VoiceChannel;

public class MstopCommand extends Music {

    public MstopCommand () {
        this.name = "mstop";
        this.help = "остановить музыку";
        //this.arguments = "<Track URL>";
        this.guildOnly = true;
    }

    @Override
    protected void execute(CommandEvent event) {
        GuildMusicManager musicManager = getGuildAudioPlayer(event.getTextChannel().getGuild());
        musicManager.scheduler.stopTrack();

        event.reply("Музыка остановлена");

        if (event.getGuild().getAudioManager().isConnected()) {
            event.getGuild().getAudioManager().closeAudioConnection();
            /*for (VoiceChannel voiceChannel : audioManager.getGuild().getVoiceChannels()) {
                audioManager.openAudioConnection(voiceChannel);
                break;
            }*/
        }
    }
}
