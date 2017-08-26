import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IUser;

import java.io.File;
import java.util.Arrays;

class CommandListener {

    private static final String[] badWords = new String[]{"chink","faggot","gook","n i g g e r", "f a g g o t","cunt","kike","n i g g a", "k i k e","nigg"};
    private static final String noPerm = "ERROR: You do not have permission to use this command.";

    @SuppressWarnings("EmptyMethod")
    @EventSubscriber
    public void onReadyEvent(ReadyEvent event) {

    }

    @EventSubscriber
    public void onMessageReceivedEvent(MessageReceivedEvent event) {
        String message = event.getMessage().toString();
        String lowerCaseMessage = message.toLowerCase();
        String outMessage;
        if(!event.getAuthor().isBot()) {
            outer:
            if(lowerCaseMessage.startsWith("z!")) {
                String command = lowerCaseMessage.substring(2,lowerCaseMessage.length());
                switch (command) {
                    default:
                        outMessage = "```ERROR: Please enter a valid command. Type z!help to see a list of commands!```";
                        break;
                    case "bustazot":
                        BotUtils.sendFile(event.getChannel(), "MAP OF RESTROOMS AT UCI", new File(System.getProperty("user.dir") + "/map.png"));
                        break outer;
                    case "calendar":
                        outMessage = "https://www.reg.uci.edu/navigation/calendars.html";
                        break;
                    case "clubs":
                        outMessage = "http://campusorgs.uci.edu/search/2016-2017";
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
                                "`z!calendar`, `z!clubs`, `z!find`, `z!food`, `z!fact`, `z!help`, `z!housing`, `z!meme`, `z!portal`, `z!planner`, `z!playing`, `z!services`, `z!zot`" +
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
                    case "help find":
                        outMessage = "`z!find [user]: Finds a user based on a search query.`";
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
                    Playing.trigger(event);
                    break outer;
                } else if(command.startsWith("warn")) {
                    if (BotUtils.isMod(event.getAuthor())) {
                        Warning.warn(event, "", event.getAuthor(), false);
                        break outer;
                    } else {
                        outMessage = noPerm;
                    }
                } else if(command.startsWith("unwarn")) {
                    if (BotUtils.isMod(event.getAuthor())) {
                        Warning.unwarn(event);
                        break outer;
                    } else {
                        outMessage = noPerm;
                    }
                } else if(command.startsWith("find")) {
                    IUser foundUser = BotUtils.findUser(command.substring(5), event.getGuild());
                    if(foundUser != null)
                        outMessage = foundUser.getDisplayName(event.getGuild());
                    else
                        outMessage = "ERROR: Could not find user.";
                }
                BotUtils.sendMessage(event.getChannel(), outMessage);
            }
            else if (lowerCaseMessage.contains("zot zot zot")) {
                BotUtils.sendMessage(event.getChannel(), "ZOT ZOT ZOT!");
            }
            else if (Arrays.stream(badWords).parallel().anyMatch(lowerCaseMessage::contains) && !lowerCaseMessage.contains("pochinki") && event.getGuild().equals(BotUtils.getUCIGuild())) {
                Warning.warn(event, "Offensive or foul language", Main.client.getOurUser(), true);
                event.getMessage().delete();
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