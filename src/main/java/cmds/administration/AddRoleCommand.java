package cmds.administration;

import core.Main;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

public class AddRoleCommand extends Command {
    public AddRoleCommand () {
        this.name = "addrole";
        this.help = "добавить роль пользователю";
        this.arguments = "<User> <Role>";
        this.botPermissions = new Permission[] {Permission.MANAGE_ROLES};
        this.userPermissions = new Permission[] {Permission.MANAGE_ROLES};
        this.guildOnly = true;
    }

    @Override
    protected void execute(CommandEvent event) {
        String[] argsData = event.getArgs().split("\\s+", 2);
        Member member;

        if (argsData.length == 2 && !event.getArgs().isEmpty()) {
            if (argsData[0].startsWith("<@") && argsData[0].endsWith(">")) { // через пинг
                String userId = argsData[0].replaceAll("[^0-9]", "");
                member = event.getGuild().getMemberById(userId);

                findRoleOfGuild(member, argsData[1], event);


            } else if (argsData[0].matches("(.{2,32})#(\\d{4})")) { // через тэг
                member = event.getGuild().getMemberByTag(argsData[0]);

                findRoleOfGuild(member, argsData[1], event);

            } else {
                event.reply("Пользователь не найден");

            }

        } else {
            event.replyWarning(Main.getPrefix() + this.name + " " + this.arguments);
        }
    }

    private void addRole (Member member, Role role, CommandEvent event) {
        if (role == null) {
            event.reply("Роль не найдена");
            return;
        } else if (member == null) {
            event.reply("Пользователь не найден");
            return;
        }

        if (!member.getRoles().contains(role)) { // проверяем, имеет ли юзер эту роль
            event.getGuild().addRoleToMember(member, role).queue();
            event.reply("Роль \"" + role.getName() + "\" была добавлена пользователю " + member.getUser().getAsTag());
        } else {
            event.reply(member.getUser().getAsTag() + " уже имеет роль \"" + role.getName() + "\"");
        }

    }

    private void findRoleOfGuild (Member member, String roleData, CommandEvent event) {
        Role role;

        if (roleData.matches("\\d{18}")) { // ищем роль по id
            role = event.getGuild().getRoleById(roleData);
            addRole(member, role, event);

        } else { // по имени
            role = event.getGuild().getRolesByName(roleData, true).get(0);
            addRole(member, role, event);
        }
    }
}
