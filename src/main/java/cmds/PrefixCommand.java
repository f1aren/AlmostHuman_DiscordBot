package cmds;

import Core.Main;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.Permission;

public class PrefixCommand extends Command {

    public PrefixCommand () {
        this.name = "setprefix";
        this.help = "установить новый префикс";
        this.arguments = "<item>";
        this.botPermissions = new Permission[]{Permission.MANAGE_ROLES};
        this.guildOnly = true;
    }

    @Override
    protected void execute(CommandEvent event) {

        Main.setPrefix(event.getArgs());
        event.reply("Префикс изменен на " + event.getArgs());
    }
}
