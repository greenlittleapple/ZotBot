import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.io.File;

public class CommandListener {

    @EventSubscriber
    public void onReadyEvent(ReadyEvent event) { // This method is called when the ReadyEvent is dispatched

    }

    @EventSubscriber
    public void onMessageReceivedEvent(MessageReceivedEvent event) { // This method is NOT called because it doesn't have the @EventSubscriber annotation
        String message = event.getMessage().toString();
        String lowerCaseMessage = message.toLowerCase();
        String outMessage;
        if(!event.getAuthor().isBot()) {
            outer:
            if(lowerCaseMessage.startsWith("z!")) {
                String command = lowerCaseMessage.substring(2,lowerCaseMessage.length());
                switch (command) {
                    default:
                        outMessage = "```Please enter a valid command. Type z!help to see a list of commands!```";
                        break;
                    case "bustazot":
                        BotUtils.sendFile(event.getChannel(), "MAP OF RESTROOMS AT UCI", new File(System.getProperty("user.dir") + "/map.png"));
                        break outer;
                    case "calendar":
                        outMessage = "https://www.reg.uci.edu/navigation/calendars.html";
                        break;
                    case "clubs":
                        outMessage = "http://campusorgs.uci.edu/";
                        break;
                    case "fact":
                        Facts.trigger(event);
                        break outer;
                    case "food":
                        outMessage = "http://www.food.uci.edu/dining.php";
                        break;
                    case "help":
                        outMessage =
                                "```Available commands are: ```" +
                                "\n" +
                                "`z!calendar`, `z!clubs`, `z!food`, `z!fact`, `z!help`, `z!housing`, `z!meme`, `z!portal`, `z!planner`, `z!playing`, `z!services`, `z!zot`" +
                                "\n\n" +
                                "```Type \"z!help [command]\" for more info regarding a command. (e.g. \"z!help fact\")" +
                                "\n" +
                                "I was made by Apple \uD83C\uDF4F#4472, please contact him if you have any suggestions or issues! v" + Main.version + "```";
                        break;
                    case "help calendar":
                        outMessage = "`z!calendar: Provides a link to the UCI Academic Calendar.`";
                        break;
                    case "help clubs":
                        outMessage = "`z!clubs: Provides a link to UCI Campus Organizations.`";
                        break;
                    case "help food":
                        outMessage = "`z!food: Provides a link to UCI Dining Locations.`";
                        break;
                    case "help fact":
                        outMessage = "`z!fact: Gives a random UCI/Anteater fact!`";
                        break;
                    case "help help":
                        outMessage = "`Seriously?`";
                        break;
                    case "help housing":
                        outMessage = "`z!housing: Provides a link to UCI Housing.`";
                        break;
                    case "help meme":
                        outMessage = "z!meme: Gives a random UCI meme!";
                        break;
                    case "help planner":
                        outMessage = "`z!planner: Provides a link to CourseEater, UCI's (unofficial) course planning site.`";
                        break;
                    case "help playing":
                        outMessage = "z!playing [game]: Lists all users on the server playing that game.";
                        break;
                    case "help portal":
                        outMessage = "`z!portal: Provides a link to the ZotPortal.`";
                        break;
                    case "help services":
                        outMessage = "`z!services: Provides a link to UCI Student Services.`";
                        break;
                    case "help zot":
                        outMessage = "Zot Zot Zot!";
                        break;
                    case "housing":
                        outMessage = "http://www.housing.uci.edu/";
                        break;
                    case "meme":
                        Memes.trigger(event);
                        break outer;
                    case "planner":
                        outMessage = "https://courseeater.com/";
                        break;
                    case "portal":
                        outMessage = "https://portal.uci.edu/";
                        break;
                    case "services":
                        outMessage = "https://www.admissions.uci.edu/discover/student-life/services.php";
                        break;
                    case "zot":
                        outMessage = "ZOT ZOT ZOT!";
                        break;
                }
                if(command.startsWith("playing")) {
                    outMessage = Playing.trigger(event);
                }
                BotUtils.sendMessage(event.getChannel(), outMessage);
            }
            else if (lowerCaseMessage.contains("zot zot zot")) {
                BotUtils.sendMessage(event.getChannel(), "ZOT ZOT ZOT!");
            }
            /*else {
                String bestType = com.greenlittleapple.zotbot.AI.receiveInput(lowerCaseMessage);
                //if(!bestType.startsWith("null")) {
                    BotUtils.sendMessage(event.getChannel(), "Message type is: " + bestType.substring(0, bestType.indexOf(' ')) + ", score: " + bestType.substring(bestType.indexOf(' ') + 1, bestType.length()));
                //}
            }*/
        }
    }

}