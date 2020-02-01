package cmds;

import Core.Main;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;

import java.awt.*;

public class SaysCommand extends Command {

    public SaysCommand () {
        this.name = "says";
        this.help = "бот говорит";
        this.arguments = "<message>";
        //this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
        this.guildOnly = false;
    }

    @Override
    protected void execute(CommandEvent event) {

        if (!event.getArgs().isEmpty()) {
            event.reply(event.getArgs());

            //event.reply(event.getAuthor().get));
        } else {
            event.replyWarning(Main.getPrefix() + this.name + " " + this.arguments);
        }
        event.getMessage().delete().queue();


    }
}
