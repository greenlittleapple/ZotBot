import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IUser;

import java.util.ArrayList;
import java.util.List;

public class PlayingListener {

    @EventSubscriber
    public void onMessageReceivedEvent(MessageReceivedEvent event) { // This method is NOT called because it doesn't have the @EventSubscriber annotation
        String message = event.getMessage().toString();
        String lowerCaseMessage = message.toLowerCase();
        StringBuilder outMessage;
        if(lowerCaseMessage.startsWith("z!playing")){
            if(lowerCaseMessage.equals("z!playing")) {
                outMessage = new StringBuilder("```Please input a game name! (e.g. z!playing PUBG)```");
            } else {
                String game = message.substring(10);
                outMessage = new StringBuilder("Current users playing " + game + ":\n```\n");
                List<IUser> users = BotUtils.getGuild().getUsers();
                ArrayList<IUser> players = new ArrayList<>();
                for (IUser x : users) {
                    String playedGame = x.getPresence().getPlayingText().toString().toLowerCase();
                    if (playedGame.substring(9, playedGame.length() - 1).equals(game.toLowerCase())) {
                        players.add(x);
                    }
                }
                for (IUser user : players) {
                    outMessage.append(user.getDisplayName(BotUtils.getGuild())).append("\n");
                }
                outMessage.append("```");
                if (players.size() == 0) {
                    outMessage = new StringBuilder("Looks like no one is playing " + game + "!");
                }
            }
            BotUtils.sendMessage(event.getChannel(), outMessage.toString());
        }
    }

}