import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        IDiscordClient client = Connection.createClient("MzQzOTgxMjgxNTY4MDk2MjU3.DGmFjQ.2XakTTce4kQLeJpwUTgjDtFR8X4", true); // Gets the client object (from the first example)
        EventDispatcher dispatcher = client.getDispatcher(); // Gets the EventDispatcher instance for this client instance
        dispatcher.registerListener(new AnnotationListener()); // Registers the @EventSubscriber example class from above
        dispatcher.registerListener(new MemeListener());
    }
}
