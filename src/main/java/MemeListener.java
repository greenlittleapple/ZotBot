import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.io.File;
import java.util.ArrayList;

public class MemeListener {

    static ArrayList<File> images;

    public MemeListener() {
        images = new ArrayList<File>();
        File folder = new File("D:\\Documents\\ZotBot\\src\\main\\resources\\UCI Bot Memes");
        for(File x : folder.listFiles()) {
            images.add(x);
        }
    }

    @EventSubscriber
    public void onMessageReceivedEvent(MessageReceivedEvent event) { // This method is NOT called because it doesn't have the @EventSubscriber annotation
        String message = event.getMessage().toString();
        String lowerCaseMessage = message.toLowerCase();
        String outMessage = null;
        if(lowerCaseMessage.equals("z!meme")){
            BotUtils.sendFile(event.getChannel(), images.get((int) (Math.random() * images.size())));
        }
    }

}