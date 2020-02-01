package cmds.Music;

import cmds.Music.core.GuildMusicManager;
import cmds.Music.core.Music;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

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
