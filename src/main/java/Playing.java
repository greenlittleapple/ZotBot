import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.RequestBuffer;

import java.util.ArrayList;
import java.util.List;

public class Playing {

    public static void trigger(MessageReceivedEvent event) { // This method is NOT called because it doesn't have the @EventSubscriber annotation
        String message = event.getMessage().toString();
        String lowerCaseMessage = message.toLowerCase();
        if(lowerCaseMessage.equals("z!playing")) {
            BotUtils.sendMessage(event.getChannel(), "Please input a game name! (e.g. z!playing PUBG)");
        } else {
            EmbedBuilder builder = new EmbedBuilder();
            builder.withColor(0,100,164);
            String game = message.substring(10);
            if(game.toLowerCase().matches("cs:go|csgo|counter strike global offensive"))
                game = "Counter-Strike Global Offensive";
            if (game.toLowerCase().matches("league|lol"))
                game = "League of Legends";
            if (game.toLowerCase().matches("ow"))
                game = "Overwatch";
            if (game.toLowerCase().matches("osu"))
                game = "osu!";
            List<IUser> users = event.getGuild().getUsers();
            ArrayList<IUser> players = new ArrayList<>();
            for (IUser x : users) {
                String playedGame = x.getPresence().getPlayingText().toString();
                if (playedGame.substring(9, playedGame.length() - 1).equalsIgnoreCase(game)) {
                    players.add(x);
                }
            }
            StringBuilder listPlayers = new StringBuilder("```fix\n");
            for (IUser user : players) {
                listPlayers.append(user.getDisplayName(event.getGuild()));
                if(!user.getDisplayName(event.getGuild()).equals(user.getName())) {
                        listPlayers.append(" (")
                            .append(user.getName())
                            .append("#")
                            .append(user.getDiscriminator())
                            .append(")");
                }
                listPlayers.append("\n");
            }
            listPlayers.append("```");
            builder.withAuthorName("Current users playing " + game + " - " + players.size());
            builder.appendDesc(listPlayers.toString());
            if (players.size() == 0) {
                BotUtils.sendMessage(event.getChannel(), "Looks like no one is playing " + game + "!");
            } else {
                RequestBuffer.request(() -> event.getChannel().sendMessage(builder.build()));
            }
        }
    }

}