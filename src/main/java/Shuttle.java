import com.jaunt.*;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.util.EmbedBuilder;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;

class Shuttle {

    static HashMap<String, String> colors;

    public Shuttle() {
        colors = new HashMap<>();
        colors.put("c-line", "#9C1AA1");
        colors.put("d-line", "#AC7A49");
        colors.put("h-line", "#4FAE9E");
        colors.put("m-line-weekday", "#008751");
        colors.put("m-line-weekend", "#7FC981");
        colors.put("n-line", "#ED6C21");
        colors.put("s-line", "#853500");
        colors.put("v-line", "#007DC3");
        colors.put("w-line", "#EA321B");
    }

    static void getShuttles(MessageReceivedEvent event) {
        UserAgent userAgent = new UserAgent();
        try {
            userAgent.visit("https://www.shuttle.uci.edu");
            userAgent.wait();
            Elements lines = null;
            try {
                lines = userAgent.doc.findFirst("<div class=ae-status-academic>").findFirst("<div class=ae-status-container>").findFirst("<ul>").findEvery("<li>");
                EmbedBuilder builder = new EmbedBuilder();
                builder.withTitle("Anteater Express Info");
                builder.appendDesc("----------");
                builder.withColor(0,100,164);
                Iterator<Element> lineIter = lines.iterator();
                while(lineIter.hasNext()) {
                    try {
                        Element next = lineIter.next();
                        StringBuilder string = new StringBuilder();
                        string.append(next.findFirst("<a>").getText());
                        if(next.findFirst("<div class=in-service|not-in-service style=\"display: none;\">").getText().equalsIgnoreCase("not in service")) {
                            string.append(" In Service");
                        } else {
                            string.append(" Not In Service");
                        }
                        builder.appendField(string.toString(), next.findAttributeValues("<a href>").get(0), false);
                    } catch (NotFound notFound) {
                        notFound.printStackTrace();
                    }
                }
                builder.appendField("*Use the command \"z!shuttle [line]\" for more info about that line!*","----------", false);
                BotUtils.sendMessage(event.getChannel(), builder.build());
            } catch (NotFound notFound) {
                BotUtils.sendMessage(event.getChannel(), "Error retrieving shuttles, please try again later.");
            }
        } catch (ResponseException e) {
            BotUtils.sendMessage(event.getChannel(), "Error retrieving shuttles, please try again later.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static void getShuttleForLine(MessageReceivedEvent event) {
        String line = event.getMessage().getContent().substring(10).toLowerCase();
        EmbedBuilder builder = new EmbedBuilder();
        builder.withImage("https://www.greenlittleapple.com/img/" + line + "-map-2017.png");
        builder.withAuthorName(line.toUpperCase() + " Line");
        UserAgent userAgent = new UserAgent();
        if(line.contains("weekend")) {
            builder.withImage("https://www.greenlittleapple.com/img/m-map-2017.png");
            line = "m-line-weekend";
        }
        else if(line.equals("m")) {
            line = "m-line-weekday";
        }
        else {
            line += "-line";
        }
        builder.withColor(Color.decode(colors.get(line)));
        try {
            userAgent.visit("https://www.shuttle.uci.edu/routes/" + line + "/");
            String desc = "Availability: ";
            try {
                if(userAgent.doc.findFirst("<div class=in-service|not-in-service style=\"display: none;\">").getText().equalsIgnoreCase("not in service")) {
                    desc += "In Service";
                } else {
                    desc += "Not In Service";
                }
            } catch (NotFound notFound) {
                desc += "UNKNOWN, check \"z!shuttle\"";
            }
            builder.appendDesc(desc);
            try {
                builder.appendField("Route Fare", "$" + userAgent.doc.findFirst("<div class=\"price \">").findFirst("<span class=integer-part>").getText(), true);
            } catch (NotFound notFound) {
                builder.appendField("Route Fare", "UNKNOWN", true);
            }
            BotUtils.sendMessage(event.getChannel(), builder.build());
        } catch (ResponseException e) {
            BotUtils.sendMessage(event.getChannel(), "Please input a valid shuttle line. (Use \"z!shuttle\" to see all lines)");
        }
    }
}