import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RequestBuffer;

import java.io.File;
import java.io.FileNotFoundException;

class BotUtils {

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

    static void sendFile(IChannel channel, String message, File file) {
        RequestBuffer.request(() -> {
            try {
                channel.sendFile(message, file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }
}