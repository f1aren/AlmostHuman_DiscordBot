package cmds;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import java.util.Random;

public class RandomNumsCommand extends Command {
    public RandomNumsCommand () {
        this.name = "randnums";
        this.help = "выдает случайное число";
        this.arguments = "<min> <max>";
        //this.guildOnly = false;
    }

    @Override
    protected void execute(CommandEvent event) {
        try {
            String[] strResult = event.getArgs().split("\\s+");
            int min = Integer.parseInt(strResult[0]);
            int max = Integer.parseInt(strResult[1]);

            Random random = new Random();

            int result = random.nextInt((max - min) + 1) + min;

            event.replySuccess("Твое число - " + result);
        } catch (Exception e) {
            event.replyWarning("Лол, ошибка\nhttps://pp.userapi.com/c850620/v850620563/13e8bf/eJMLEODPArU.jpg");
        }


    }
}
