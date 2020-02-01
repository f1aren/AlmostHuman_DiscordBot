package cmds.Music;

import cmds.Music.core.GuildMusicManager;
import cmds.Music.core.Music;
import com.jagrosh.jdautilities.command.CommandEvent;

public class MtrackinfoCommand extends Music {
    public MtrackinfoCommand() {
        this.name = "mtrackinfo";
        this.help = "получить информацию о текущем треке";
        //this.arguments = "<0 - 100>";
        this.guildOnly = true;
    }

    @Override
    protected void execute(CommandEvent event) {
        GuildMusicManager musicManager = getGuildAudioPlayer(event.getTextChannel().getGuild());
        //musicManager.scheduler.setVolume(Integer.parseInt(event.getArgs()));

        event.reply("Сейчас играет: \n" + musicManager.scheduler.getCurrentTrack());
    }
}
