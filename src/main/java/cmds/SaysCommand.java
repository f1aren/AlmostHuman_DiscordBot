package cmds;

import core.Main;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

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
