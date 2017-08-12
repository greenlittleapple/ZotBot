import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class AnnotationListener {

    String[] facts = new String[]{
            "Anteaters can be small as a squirrel (silky anteaters) or 7 feet long, counting from the tip of the nose to the end of its tail (giant anteaters).",
            "Anteaters are toothless creatures.",
            "Anteaters use their long and sticky tongue to catch prey.",
            "The tongue of an anteater can be 2 feet long. It is narrow and covered with tiny spines.",
            "Anteaters catch ants and termites through the hole on the top of the anthill.",
            "Anteaters never destroy anthills because they plan to come and eat another portion of ants in the future.",
            "Since ants can bite, anteaters must eat them quickly. They flick their tongue 150-160 times in minute during feeding to grab enough ants and avoid bites.",
            "Anteaters' digestion is facilitated by specifically designed stomach that grinds large quantity of ants and termites. Their stomach produces formic acid instead of hydrochloric acid (which is normally found in other mammals). They can eat up to 30,000 insects per day.",
            "Anteaters have poor eyesight, but excellent sense of smell. They can detect smell 40 times better than humans and use their nose to find food.",
            "Anteaters have low body temperature compared to other placental mammals, just 32.7 degrees of Celsius.",
            "Anteaters sleep 15 hours per day.",
            "Anteaters have 4 inches long claws and they use them to defend against jaguars and cougars.",
            "Anteaters are solitary animals and they gather only during mating season. Group of anteaters is called \"parade\".",
            "Anteaters live up to 15 years in the wild and 25 years in captivity."
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
                                "`z!calendar`, `z!clubs`, `z!food`, `z!fact`, `z!help`, `z!housing`, `z!portal`, `z!planner`, `z!services`, `z!zot`" +
                                "\n\n" +
                                "```Type \"z!help [command]\" for more info regarding a command. (e.g. \"z!help fact\")" +
                                "\n" +
                                "I was made by Apple \uD83C\uDF4F#4472, please contact him if you have any suggestions or issues! v0.081217```";
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
                        outMessage = "`z!fact: Gives a random Anteater fact!`";
                        break;
                    case "help help":
                        outMessage = "`Seriously?`";
                        break;
                    case "help housing":
                        outMessage = "`z!housing: Provides a link to UCI Housing.`";
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