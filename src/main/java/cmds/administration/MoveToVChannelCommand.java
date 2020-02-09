package cmds.administration;

import core.Main;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;

import java.util.List;


public class MoveToVChannelCommand extends Command {

    public MoveToVChannelCommand () {
        this.name = "movetovchannel";
        this.help = "переместить пользователя в другой голосовой канал";
        this.arguments = "<User> <VoiceChannel>";
        this.botPermissions = new Permission[] {Permission.VOICE_MOVE_OTHERS};
        this.userPermissions = new Permission[] {Permission.VOICE_MOVE_OTHERS};
        this.guildOnly = true;
    }

    @Override
    protected void execute(CommandEvent event) {
        String[] argsData = event.getArgs().split("\\s+", 2);
        Member member;
        VoiceChannel voiceChannel = null;


        if (argsData.length == 2 && !event.getArgs().isEmpty()) { // если 2 элемента массива и аргумент не пуст
            if (argsData[0].startsWith("<@") && argsData[0].endsWith(">")) {
                String userId = argsData[0].replaceAll("[^0-9]", "");
                member = event.getGuild().getMemberById(userId);

                List<VoiceChannel> listVChannels = event.getGuild().getVoiceChannelsByName(argsData[1], true);

                if (listVChannels.size() != 0) {
                    voiceChannel = listVChannels.get(0);
                    moveMemberToVChannel(member, voiceChannel, event);
                } else {
                    event.reply("Голосовой канал не найден");
                }


            } else if (argsData[0].matches("(.{2,32})#(\\d{4})")) {
                member = event.getGuild().getMemberByTag(argsData[0]);
                if (member != null) {
                    List<VoiceChannel> listVChannels = event.getGuild().getVoiceChannelsByName(argsData[1], true);

                    if (listVChannels.size() != 0) {
                        voiceChannel = listVChannels.get(0);
                        moveMemberToVChannel(member, voiceChannel, event);
                    } else {
                        event.reply("Голосовой канал не найден");
                    }
                } else {
                    event.reply("Пользователь не найден");
                }


            } else {

                List<Member> membersList = event.getGuild().getMembersByName(argsData[0], true);

                if (membersList.size() != 0) {
                    member = membersList.get(0);
                    List<VoiceChannel> listVChannels = event.getGuild().getVoiceChannelsByName(argsData[1], true);

                    if (listVChannels.size() != 0) {
                        voiceChannel = listVChannels.get(0);
                        moveMemberToVChannel(member, voiceChannel, event);
                    } else {
                        event.reply("Голосовой канал не найден");
                    }
                } else {
                    event.reply("Пользователь не найден");
                }

            }

        } else {
            event.replyWarning(Main.getPrefix() + this.name + " " + this.arguments);
        }
    }


    private  void moveMemberToVChannel (Member member, VoiceChannel voiceChannel, CommandEvent event) {
        if (member.getVoiceState().inVoiceChannel()) {
            member.getGuild().moveVoiceMember(member, voiceChannel).queue();
            event.reply(member.getUser().getAsTag() + " был перемещен");
        } else {
            event.reply("Пользователь сейчас не находится в голосовом канале");
        }
    }
}
