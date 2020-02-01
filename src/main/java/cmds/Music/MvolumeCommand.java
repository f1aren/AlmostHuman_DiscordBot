package cmds.Music;

import cmds.Music.core.GuildMusicManager;
import cmds.Music.core.Music;
import com.jagrosh.jdautilities.command.CommandEvent;

public class MvolumeCommand extends Music {

    public MvolumeCommand() {
        this.name = "mvolume";
        this.help = "установка громкости музыки";
        this.arguments = "<0 - 150>";
        this.guildOnly = true;
    }

    @Override
    protected void execute(CommandEvent event) {
        GuildMusicManager musicManager = getGuildAudioPlayer(event.getTextChannel().getGuild());
        musicManager.scheduler.setVolume(Integer.parseInt(event.getArgs()));

        event.reply("Громкость установлена на " + musicManager.scheduler.getPlayer().getVolume() + "%");
    }
}
