import com.jaunt.*;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.util.EmbedBuilder;

import java.util.Iterator;

class Shuttle {

    static void getShuttles(MessageReceivedEvent event) {
        UserAgent userAgent = new UserAgent();
        try {
            userAgent.visit("https://www.shuttle.uci.edu");
        } catch (ResponseException e) {
            e.printStackTrace();
        }
        Elements lines = null;
        try {
            lines = userAgent.doc.findFirst("<div class=ae-status-academic>").findFirst("<div class=ae-status-container>").findFirst("<ul>").findEvery("<li>");
        } catch (NotFound notFound) {
            notFound.printStackTrace();
        }
        EmbedBuilder builder = new EmbedBuilder();
        builder.withTitle("Anteater Express Info");
        builder.appendDesc("----------");
        builder.withColor(0,100,164);
        Iterator<Element> lineIter = lines.iterator();
        while(lineIter.hasNext()) {
            try {
                Element next = lineIter.next();
                StringBuilder string = new StringBuilder();
                string.append(next.findFirst("<a>").getText()
                        + " - ");
                if(next.findFirst("<div class=in-service|not-in-service style=\"display: none;\">").getText().equalsIgnoreCase("not in service")) {
                    string.append("In Service");
                } else {
                    string.append("Not In Service");
                }
                builder.appendField(string.toString(), next.findAttributeValues("<a href>").get(0), false);
            } catch (NotFound notFound) {
                notFound.printStackTrace();
            }
        }
        BotUtils.sendMessage(event.getChannel(), builder.build());
    }
}