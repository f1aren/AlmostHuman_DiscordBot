package cmds;

import core.Main;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.awt.*;

public class AnnounceCommand extends Command {
    public AnnounceCommand () {
        this.name = "announce";
        this.help = "сделать объявление";
        this.arguments = "<message>";
        this.botPermissions = new Permission[]{Permission.MESSAGE_MENTION_EVERYONE};
        this.guildOnly = false;
    }

    @Override
    protected void execute(CommandEvent event) {
        MessageChannel channel = event.getChannel();


        if (!event.getArgs().isEmpty()) {
            channel.sendMessage("\u0040" + "everyone " + event.getArgs()).queue();

        } else {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(Color.decode("#eb3a1e"));
            embed.addField("Cинтаксис:",Main.getPrefix() + this.name + " " + this.arguments, false);

            event.reply(embed.build());;
        }
        event.getMessage().delete().queue();

    }
}
