package cmds;

import Core.Main;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class AvatarCommand extends Command {
    public AvatarCommand () {
        this.name = "avatar";
        this.help = "получить аватар пользователя";
        this.arguments = "<user>";
        //this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
        this.guildOnly = true;
    }

    @Override
    protected void execute(CommandEvent event) {
        String[] argsData = event.getArgs().split("\\s+");
        User user = null;
        Member member = null;

        if (argsData.length == 1 && !event.getArgs().isEmpty()) {
            if (argsData[0].startsWith("<@") && argsData[0].endsWith(">")) {
                argsData[0] = argsData[0].substring(3, argsData[0].length() - 1);

                member = event.getGuild().getMemberById(argsData[0]);
                sendImage(member.getUser().getAvatarUrl(), event);

            } else if (argsData[0].matches("(.{2,32})#(\\d{4})")) {
                try {
                    member = event.getGuild().getMemberByTag(argsData[0]);
                    sendImage(member.getUser().getAvatarUrl(), event);
                } catch (NullPointerException e) {
                    event.reply("Пользователь не найден");
                }
            } else {

                try {
                    member = event.getGuild().getMembersByName(argsData[0], false).get(0);
                    sendImage(member.getUser().getAvatarUrl(), event);
                } catch (IndexOutOfBoundsException e) {
                    event.reply("Пользователь не найден");
                }
            }

        } else {
            event.replyWarning(Main.getPrefix() + this.name + " " + this.arguments);
        }


    }

    private void sendImage (String linkUrl, CommandEvent event) {
        BufferedImage img = null;
        File imgFile = null;
        URL url = null;
        URLConnection uc = null;

        try {
            url = new URL(linkUrl);
            uc = url.openConnection();
            uc.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)"); // обход ошибки 403
            uc.connect();

            img = ImageIO.read(uc.getInputStream());
            imgFile = new File("avatar.png");
            ImageIO.write(img, "png", imgFile);
            event.reply(imgFile, "avatar.png");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
