import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        IDiscordClient client = Connection.createClient("MzQzOTgxMjgxNTY4MDk2MjU3.DHZIcQ.vTfqmaUXWSLJL6LsHHKQhORbpjU", true); // Gets the client object (from the first example)
        assert client != null;
        EventDispatcher dispatcher = client.getDispatcher(); // Gets the EventDispatcher instance for this client instance
        dispatcher.registerListener(new CommandListener()); // Registers the @EventSubscriber example class from above
        dispatcher.registerListener(new MemeListener());
        dispatcher.registerListener(new FactListener());
    }
}
