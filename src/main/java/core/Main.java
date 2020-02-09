package core;

import cmds.*;
import cmds.administration.*;
import cmds.music.*;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jagrosh.jdautilities.examples.command.PingCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;


import javax.security.auth.login.LoginException;



public class Main {

    private static String prefix = "!!";

    private static EventWaiter waiter = new EventWaiter();
    private static CommandClientBuilder client = new CommandClientBuilder(); // jda utilities

    private static JDA jdaBuilder;

    public static void setPrefix (String newPrefix) {
        prefix = newPrefix;
        client.setPrefix(newPrefix);
        jdaBuilder.removeEventListener(client.build());
        jdaBuilder.addEventListener(client.build());

    }

    public static String getPrefix () {
        return prefix;
    }

    public static void main (String[] args) throws LoginException, IllegalArgumentException, InterruptedException {
        client.setOwnerId(Keys.OWNERID);

        client.setPrefix(prefix);
        client.setActivity(Activity.listening(prefix + "help"));
        client.addCommands(
                //new PrefixCommand(),
                new AnnounceCommand(),
                new AnimeCommand(),
                new AvatarCommand(),
                new DeviantCommand(),
                new RandomNumsCommand(),
                new CoinCommand(),
                new SaysCommand(),
                new PingCommand(),
                // administration
                new AddRoleCommand(),
                new RemoveRoleCommand(),
                new MuteCommand(),
                new UnmuteCommand(),
                new MoveToVChannelCommand(),

                // music
                new MplayCommand(),
                new MskipCommand(),
                new MstopCommand(),
                new MvolumeCommand(),
                new MtrackinfoCommand(),
                new MqueueCommand());



        jdaBuilder = new JDABuilder(Keys.TOCKENBOT).build();
        jdaBuilder.addEventListener(waiter);
        jdaBuilder.addEventListener(client.build());
        jdaBuilder.setAutoReconnect(true);

        jdaBuilder.awaitReady();



    }

    public static JDA getJdaBuilder() {
        return jdaBuilder;
    }


}
