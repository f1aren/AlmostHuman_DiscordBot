package cmds;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import java.util.Random;

public class CoinCommand extends Command {

    public CoinCommand () {
        this.name = "coin";
        this.help = "орел или решка?";
        //this.arguments = "<item>";
        //this.guildOnly = false;
    }

    @Override
    protected void execute(CommandEvent event) {
        Random random = new Random();

        if (random.nextBoolean()) {
            event.reply("Выпал орел");
        } else {
            event.reply("Выпала решка");
        }
    }
}
