package cmds;

import Core.Keys;
import Gson.Deviant.AccessTokenData;
import Gson.Deviant.DeviantBasic;
import com.google.gson.Gson;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Iterator;


public class AnimeCommand extends Command {

    private String accessUrl = "https://www.deviantart.com/oauth2/token?grant_type=client_credentials&client_id="
            + Keys.DEVIANT_CLIENTID + "&client_secret=" + Keys.DEVIANT_CLIENTSECRET;
    private String dataUrl = "https://www.deviantart.com/api/v1/oauth2/browse/newest?&q=waifu&limit=24&offset=";

    public AnimeCommand () {
        this.name = "anime";
        this.help = "получить свою вайфу ( ͡° ͜ʖ ͡°)";
        //this.arguments = "<arg>";
        //this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
        this.guildOnly = false;
    }


    @Override
    protected void execute(CommandEvent event) {

        Gson gson = new Gson();
        URL url = null;
        InputStreamReader reader = null;
        AccessTokenData jsonData = null;
        DeviantBasic deviantData = null;

        //Core.Deviant.results resultsData = null;
        int offset = 0;//(int) (Math.random() * 40);
        int maxResult = 0;
        int rand = 0;
        int maxLists = 0;

        //gson.fromJson( );


        try {
            event.reply("Ищу вайфу... ( ͡° ͜ʖ ͡°)");
            url = new URL(accessUrl);
            reader = new InputStreamReader(url.openStream());
            jsonData = gson.fromJson(reader, AccessTokenData.class);
            //event.reply(jsonData.access_token);
            reader.close();


            url = new URL(dataUrl + offset + "&access_token=" + jsonData.access_token);
            reader = new InputStreamReader(url.openStream());
            deviantData = gson.fromJson(reader, DeviantBasic.class);
            reader.close();
            //event.reply("results.size = " + String.valueOf(deviantData.results.size()));



        } catch (Exception e) {
            event.replyWarning("Error: " + e);
        }

        do {
            rand = (int) (Math.random() * deviantData.results.size());
            //event.reply(deviantData.results.size() + " " + rand);
            //event.reply(deviantData.results.get(rand).category);
            //System.out.println(deviantData.results.get(rand).category);
        } while (deviantData.results.get(rand).category.equals("Pages") ||
                deviantData.results.get(rand).category.equals("Personal")) ;

        //event.reply(deviantData.results.get(rand).content.src);

        String imgFormat;
        BufferedImage img = null;
        File imgFile = null;
        try {
            url = new URL(deviantData.results.get(rand).content.src);
            img = ImageIO.read(url.openStream());

            ImageInputStream iis = ImageIO.createImageInputStream(url.openStream()); // узнаем формат пикчи
            Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(iis);
            imgFormat = imageReaders.next().getFormatName();

            imgFile = new File("image." + imgFormat);
            ImageIO.write(img, imgFormat, imgFile);
            event.reply(imgFile, "image." + imgFormat);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
