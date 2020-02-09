package cmds.administration;

import core.Main;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;

public class UnmuteCommand extends Command {

    public UnmuteCommand () {
        this.name = "unmute";
        this.help = "разглушить пользователя";
        this.arguments = "<user>";
        this.botPermissions = new Permission[] {Permission.VOICE_MUTE_OTHERS};
        this.userPermissions = new Permission[] {Permission.VOICE_MUTE_OTHERS};
        this.guildOnly = true;
    }

    @Override
    protected void execute(CommandEvent event) {
        String[] argsData = event.getArgs().split("\\s+", 1);
        Member member;


        if (argsData.length == 1 && !event.getArgs().isEmpty()) {
            if (argsData[0].startsWith("<@") && argsData[0].endsWith(">")) {

                String userId = argsData[0].replaceAll("[^0-9]", "");
                member = event.getGuild().getMemberById(userId);
                unmuteMember(member, event);

            } else if (argsData[0].matches("(.{2,32})#(\\d{4})")) {
                try {
                    member = event.getGuild().getMemberByTag(argsData[0]);
                    unmuteMember(member, event);
                } catch (NullPointerException e) {
                    event.reply("Пользователь не найден");
                }
            } else {

                try {
                    member = event.getGuild().getMembersByName(argsData[0], true).get(0);
                    unmuteMember(member, event);
                } catch (IndexOutOfBoundsException e) {
                    event.reply("Пользователь не найден");
                }
            }

        } else {
            event.replyWarning(Main.getPrefix() + this.name + " " + this.arguments);

        }
    }

    private void unmuteMember (Member member, CommandEvent event) {
        member.mute(false).queue();
        event.reply(member.getUser().getAsTag() + " разглушен");
    }
}