package cmds.music;

import cmds.music.core.GuildMusicManager;
import cmds.music.core.Music;
import com.jagrosh.jdautilities.command.CommandEvent;

public class MqueueCommand extends Music {
    public MqueueCommand() {
        this.name = "mqueue";
        this.help = "список плейлиста";
        this.guildOnly = true;
    }

    @Override
    protected void execute(CommandEvent event) {
        GuildMusicManager musicManager = getGuildAudioPlayer(event.getTextChannel().getGuild());
        //musicManager.scheduler.setVolume(Integer.parseInt(event.getArgs()));

        event.reply("Плейлист:\n" + (musicManager.scheduler.getQueueList().isEmpty() ? "Пуст" : musicManager.scheduler.getQueueList()));
    }
}
