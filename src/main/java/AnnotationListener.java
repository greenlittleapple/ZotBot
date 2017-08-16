import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class AnnotationListener {

    String[] facts = new String[]{
            "Anteaters can be as small as a squirrel (silky anteater) or up to 7 feet long (giant anteater).",
            "Anteaters are toothless creatures.",
            "The tongue of an anteater can be up to 2 feet long. It is narrow and covered with tiny spines.",
            "Anteaters never destroy anthills to preserve it for future eating.",
            "Anteaters have to eat ants quickly or else they will get bitten. As a result, they can flick their tongues 150-160 times a minute.",
            "Anteaters' stomachs are specifically designed to grind a large amount of insects at once. It produces formic acid instead of hydrochloric acid (which is normally found in other mammals), and as a result anteaters can eat up to 30,000 insects per day.",
            "Anteaters are nearly blind, but make up for it with their excellent sense of smell, which is 40 times better than that of humans.",
            "Anteaters have a low body temperature compared to other mammals, at just 32.7 degrees Celsius.",
            "Anteaters sleep 15 hours per day.",
            "Anteaters have 4-inch-long claws and use them to defend against jaguars and cougars.",
            "Anteaters are solitary animals and gather only during mating season. A group of anteaters is called \"parade\".",
            "Anteaters can live up to 15 years in the wild and 25 years in captivity.",
            "UC Irvine is the only UC in Irvine. (Credit: Jacxk101)",
            "UCI is the largest UC of its kind, boasting 33,467 total students in 2016.",
            "UCI is situated in Irvine, CA, which is known as the technology center of Southern California.",
            "Over 104,000 students applied for admission for UCI in 2017!",
            "UCI's Aldrich Park contains over 11,000 trees.",
            "The word \"zot\" means \"foolish\" in Dutch and \"god\" in Albanian. Zot Zot Zot!"
    };

    @EventSubscriber
    public void onReadyEvent(ReadyEvent event) { // This method is called when the ReadyEvent is dispatched

    }

    @EventSubscriber
    public void onMessageReceivedEvent(MessageReceivedEvent event) { // This method is NOT called because it doesn't have the @EventSubscriber annotation
        String message = event.getMessage().toString();
        String lowerCaseMessage = message.toLowerCase();
        String outMessage = null;
        if(!event.getAuthor().isBot()) {
            outer:
            if(lowerCaseMessage.startsWith("z!")) {
                String command = lowerCaseMessage.substring(2,lowerCaseMessage.length());
                switch (command) {
                    default:
                        outMessage = "```Please enter a valid command. Type z!help to see a list of commands!```";
                        break;
                    case "calendar":
                        outMessage = "https://www.reg.uci.edu/navigation/calendars.html";
                        break;
                    case "clubs":
                        outMessage = "http://campusorgs.uci.edu/";
                        break;
                    case "fact":
                        outMessage = "Did you know? " + facts[(int) (Math.random() * facts.length)];
                        break;
                    case "food":
                        outMessage = "http://www.food.uci.edu/dining.php";
                        break;
                    case "help":
                        outMessage =
                                "```Available commands are: ```" +
                                "\n" +
                                "`z!calendar`, `z!clubs`, `z!food`, `z!fact`, `z!help`, `z!housing`, `z!meme`, `z!portal`, `z!planner`, `z!services`, `z!zot`" +
                                "\n\n" +
                                "```Type \"z!help [command]\" for more info regarding a command. (e.g. \"z!help fact\")" +
                                "\n" +
                                "I was made by Apple \uD83C\uDF4F#4472, please contact him if you have any suggestions or issues! v0.081617```";
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
                BotUtils.sendMessage(event.getChannel(), outMessage);
            }
            else if (lowerCaseMessage.startsWith("zot zot zot")) {
                BotUtils.sendMessage(event.getChannel(), "ZOT ZOT ZOT!");
            }
            /*else {
                String bestType = AI.receiveInput(lowerCaseMessage);
                //if(!bestType.startsWith("null")) {
                    BotUtils.sendMessage(event.getChannel(), "Message type is: " + bestType.substring(0, bestType.indexOf(' ')) + ", score: " + bestType.substring(bestType.indexOf(' ') + 1, bestType.length()));
                //}
            }*/
        }
    }

}