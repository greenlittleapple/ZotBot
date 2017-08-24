import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

public class Memes {

    private static ArrayList<File> images;
    private static Timer timer;

    Memes() {
        timer = new Timer();
        TimerTask getMemes = new TimerTask() {
            @Override
            public void run() {
                try {
                    RedditHandler.recoverImages("MemeEconomy");
                    RedditHandler.recoverImages("dankmemes");
                    RedditHandler.recoverImages("wholesomememes");
                    RedditHandler.recoverImages("meirl");
                    RedditHandler.recoverImages("bonehurtingjuice");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                images = new ArrayList<>();
                File folder = new File(System.getProperty("user.dir") + "/memes/");
                Collections.addAll(images, folder.listFiles());
            }
        };
        timer.schedule(getMemes, 0,1000*60*60);
    }

    public static void trigger(MessageReceivedEvent event) {
        if(images.size() != 0)
            BotUtils.sendFile(event.getChannel(), images.get((int) (Math.random() * images.size())));
        else
            BotUtils.sendMessage(event.getChannel(), "Please try again in a bit.");
    }

}