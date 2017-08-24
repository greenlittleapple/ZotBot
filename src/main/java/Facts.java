import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.Collections;

public class Facts {

    private static String[] facts = new String[]{
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
            "UCI is the largest UC of its size.",
            "UCI is situated in Irvine, CA, which is known as the technology center of Southern California.",
            "Over 104,000 students applied for admission for UCI in 2017!",
            "UCI's Aldrich Park contains over 11,000 trees.",
            "The word \"zot\" means \"foolish\" in Dutch and \"god\" in Albanian. Zot Zot Zot!",
            "UCI is ranked 8th greenest university in the nation!",
            "UCI is among Sierra magazine's top 10 \"Cool Schools\" for the eighth straight year."
    };

    private static ArrayList<Integer> factsCheck = new ArrayList<>(Collections.nCopies(facts.length, 0));

    public static void trigger(MessageReceivedEvent event) {
        int index;
        if(!factsCheck.contains(0))
            factsCheck = new ArrayList<>(Collections.nCopies(facts.length, 0));
        do {
            index = (int) (Math.random() * facts.length);
        } while(factsCheck.get(index) != 0);
        BotUtils.sendMessage(event.getChannel(), "Did you know? " + facts[index]);
        factsCheck.set(index, 1);
    }
}