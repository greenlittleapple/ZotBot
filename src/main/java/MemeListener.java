import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class MemeListener {

    private static ArrayList<File> images;

    MemeListener() {
        images = new ArrayList<>();
        File folder = new File(System.getProperty("user.dir") + "/memes/");
        Collections.addAll(images, folder.listFiles());
    }

    @EventSubscriber
    public void onMessageReceivedEvent(MessageReceivedEvent event) { // This method is NOT called because it doesn't have the @EventSubscriber annotation
        String message = event.getMessage().toString();
        String lowerCaseMessage = message.toLowerCase();
        if(lowerCaseMessage.equals("z!meme")){
            BotUtils.sendFile(event.getChannel(), images.get((int) (Math.random() * images.size())));
        }
    }

}