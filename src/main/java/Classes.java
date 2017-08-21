import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class Classes {

    public static void onMessageReceivedEvent(MessageReceivedEvent event) { // This method is NOT called because it doesn't have the @EventSubscriber annotation
        String message = event.getMessage().toString();
        String lowerCaseMessage = message.toLowerCase();
        StringBuilder outMessage = null;
        if(lowerCaseMessage.equals("z!class"))
            outMessage.append("Please input a class name or number!");
    }

}