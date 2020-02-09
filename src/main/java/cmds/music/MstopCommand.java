package cmds.music;

import cmds.music.core.GuildMusicManager;
import cmds.music.core.Music;
import com.jagrosh.jdautilities.command.CommandEvent;

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
