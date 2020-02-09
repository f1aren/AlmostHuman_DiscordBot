package cmds.music;

import cmds.music.core.GuildMusicManager;
import cmds.music.core.Music;
import com.jagrosh.jdautilities.command.CommandEvent;

public class MskipCommand extends Music {

    public MskipCommand() {
        this.name = "mskip";
        this.help = "пропустить трек";
        this.guildOnly = true;
    }

    @Override
    protected void execute(CommandEvent event) {

        GuildMusicManager musicManager = getGuildAudioPlayer(event.getTextChannel().getGuild());
        musicManager.scheduler.nextTrack();


        if (musicManager.scheduler.getPlayer().getPlayingTrack() == null) {
            event.reply("Закончил играть"); // если в плеере нет установленной музыки, следовательно бот вышел с войса
        } else {
            event.reply("Пропускаю трек. Сейчас играет: " + musicManager.scheduler.getPlayer().getPlayingTrack().getInfo().title);
        }

    }

}
