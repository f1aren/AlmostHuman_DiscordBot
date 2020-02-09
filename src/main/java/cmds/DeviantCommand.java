package cmds;

import core.Keys;
import gson.deviant.DeviantBasic;
import gson.deviant.AccessTokenData;
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


public class DeviantCommand extends Command {

    private String accessUrl = "https://www.deviantart.com/oauth2/token?grant_type=client_credentials&client_id="
            + Keys.DEVIANT_CLIENTID + "&client_secret=" + Keys.DEVIANT_CLIENTSECRET;
    private String dataUrl = "https://www.deviantart.com/api/v1/oauth2/browse/tags?tag=";


    public DeviantCommand () {
        this.name = "deviant";
        this.help = "получить пикчу";
        this.arguments = "<Image Tag>";
        this.guildOnly = false;
    }

    @Override
    protected void execute(CommandEvent event)  {
        Gson gson = new Gson();

        URL url = null;
        InputStreamReader reader = null;
        AccessTokenData jsonData = null;
        DeviantBasic deviantData = null;

        int offset = 0;//(int) (Math.random() * 40);
        int maxResult = 0;
        int rand = 0;
        int maxLists = 0;


        try {
            event.reply("Ищу пикчу...");
            url = new URL(accessUrl);
            reader = new InputStreamReader(url.openStream());
            jsonData = gson.fromJson(reader, AccessTokenData.class);
            //event.reply(jsonData.access_token);
            reader.close();

            url = new URL(dataUrl + event.getArgs() + "&limit=50&access_token=" + jsonData.access_token);
            reader = new InputStreamReader(url.openStream());
            deviantData = gson.fromJson(reader, DeviantBasic.class);
            //event.reply(String.valueOf(deviantData.has_more));
            //event.reply("results.size = " + String.valueOf(deviantData.results.size()));
            //event.reply(deviantData.results.get((int) (Math.random() * deviantData.results.size())).content.src);
            reader.close();


        } catch (Exception e) {
            event.replyWarning("Exception error: " + e);
        }

        do {
            rand = (int) (Math.random() * deviantData.results.size());
        } while (deviantData.results.get(rand).category.equals("Pages") ||
                deviantData.results.get(rand).category.equals("Personal")) ;

        //int indexOfToken = deviantData.results.get(rand).content.src.indexOf("?token=");
        //event.reply(String.valueOf(indexOfToken));
        //imgFormat = deviantData.results.get(rand).content.src.substring(indexOfToken - 3, indexOfToken);
        //event.reply(imgFormat);

        String imgFormat;
        BufferedImage img = null;
        File imgFile = null;
        try {
            url = new URL(deviantData.results.get(rand).content.src);

            //System.out.println(deviantData.results.get(rand).content.src);
            img = ImageIO.read(url.openStream());

            ImageInputStream iis = ImageIO.createImageInputStream(url.openStream());
            Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(iis);
            imgFormat = imageReaders.next().getFormatName();


            imgFile = new File("deviant." + imgFormat);
            ImageIO.write(img, imgFormat, imgFile);
            event.reply(imgFile, "deviant." + imgFormat);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
