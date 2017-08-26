import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RequestBuffer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

@SuppressWarnings("Duplicates")
class BotUtils {

    static boolean isMod(IUser user) {
        return user.getRolesForGuild(BotUtils.getUCIGuild()).contains(BotUtils.getRoleByID(342539333015699457L));
    }

    static IGuild getUCIGuild() {
        return Main.client.getGuildByID(341464294132678668L);
    }

    static IRole getRoleByID(Long id) {
        return Main.client.getRoleByID(id);
    }

    // Helper functions to make certain aspects of the bot easier to use.
    static void sendMessage(IChannel channel, String message){

        // This might look weird but it'll be explained in another page.
        RequestBuffer.request(() -> {
            try{
                channel.sendMessage(message);
            } catch (DiscordException e){
                System.err.println("Message could not be sent with error: ");
                e.printStackTrace();
            }
        });
    }

    static void sendMessage(IChannel channel, EmbedObject object){

        // This might look weird but it'll be explained in another page.
        RequestBuffer.request(() -> {
            try{
                channel.sendMessage(object);
            } catch (DiscordException e){
                System.err.println("Message could not be sent with error: ");
                e.printStackTrace();
            }
        });
    }

    static void sendFile(IChannel channel, File file) {
        RequestBuffer.request(() -> {
            try {
                channel.sendFile(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    static void sendFile(IChannel channel, @SuppressWarnings("SameParameterValue") String message, File file) {
        RequestBuffer.request(() -> {
            try {
                channel.sendFile(message, file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    static IUser findUser(String term, IGuild server) {
        ArrayList<IUser> users = new ArrayList<>(getUCIGuild().getUsers());
        for(int i = 0; i < 7; i++) {
            for(IUser x : users) {
                boolean matched = false;
                switch(i) {
                    case 0:
                        matched = checkMatch(server, term, x, true,true, false,false,false,false);
                        break;
                    case 1:
                        matched = checkMatch(server, term, x, true,false, false,false,false,false);
                        break;
                    case 2:
                        matched = checkMatch(server, term, x, false,true, false,false,false,false);
                        break;
                    case 3:
                        matched = checkMatch(server, term, x, false,false, true,false,false,false);
                        break;
                    case 4:
                        matched = checkMatch(server, term, x, false,false, false,true,false,false);
                        break;
                    case 5:
                        matched = checkMatch(server, term, x, false,false, false,false,true,false);
                        break;
                    case 6:
                        matched = checkMatch(server, term, x, false,false, false,false,false,true);
                        break;
                }
                if(matched)
                    return x;
            }
        }
        return null;
    }

    private static boolean checkMatch(IGuild guild, String input, IUser user, boolean username_match, boolean nickname_match, boolean username_start, boolean nickname_start, boolean username_contains, boolean nickname_contains) {
        String userName = user.getName().toLowerCase();
        String dispName = user.getDisplayName(guild).toLowerCase();
        input = input.toLowerCase();
        if(username_match) {
            if(nickname_match) {
                return checkMatch(guild, input,user,true,false,false,false,false,false) && checkMatch(guild, input,user,false,true,true,true,true,true);
            } else {
                return input.equalsIgnoreCase(userName);
            }
        } else if(nickname_match) {
            return input.equals(dispName);
        } else if(username_start) {
            return userName.startsWith(input);
        } else if(nickname_start) {
            return dispName.startsWith(input);
        } else if(username_contains) {
            return userName.contains(input);
        } else if(nickname_contains) {
            return dispName.contains(input);
        }
        return false;
    }

}