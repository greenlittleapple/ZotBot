import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.MessageTokenizer;

public class Warning {

    public static void warn(MessageReceivedEvent event, String reasonInput, IUser moderator, boolean automod) { // This method is NOT called because it doesn't have the @EventSubscriber annotation
        String message = event.getMessage().toString();
        String outMessage = "";
        String reason = "Breaking a server rule";
        if (event.getMessage().getMentions().size() != 0 || automod) {
            if(reasonInput.equals("")) {
                MessageTokenizer tokenizer = new MessageTokenizer(Main.client, message);
                tokenizer.nextWord();
                tokenizer.nextWord();
                if(tokenizer.getRemainingContent().length() != 0)
                    reason = tokenizer.getRemainingContent();
            } else {
                reason = reasonInput;
            }
            IUser target;
            if(automod)
                target = event.getAuthor();
            else
                target = event.getMessage().getMentions().get(0);
            if (!target.getRolesForGuild(BotUtils.getUCIGuild()).contains(BotUtils.getRoleByID(350330574134706176L))) {
                target.addRole(BotUtils.getRoleByID(350330574134706176L));
                EmbedBuilder builder = new EmbedBuilder();
                builder.withAuthorName("Warned on UCI Server");
                builder.withColor(255,0,0);
                builder.appendField("Moderator", moderator.getDisplayName(BotUtils.getUCIGuild()) + "#" + moderator.getDiscriminator(), true);
                builder.appendField("Reason", reason, false);
                BotUtils.sendMessage(Main.client.getOrCreatePMChannel(target), builder.build());
                outMessage = "<@" + target.getStringID() + "> You have been warned for reason: " + reason + ". You will be banned on your second warning. If this warning was given in error, please contact a moderator.";
            } else {
                BotUtils.getUCIGuild().banUser(target,reason, 7);
            }
        } else {
            outMessage = "ERROR: Please input a user to warn.";
        }
        BotUtils.sendMessage(event.getChannel(), outMessage);
    }

    public static void unwarn(MessageReceivedEvent event) {
        String outMessage = "";
        if(event.getMessage().getMentions().size() != 0) {
            IUser target = event.getMessage().getMentions().get(0);
            if(target.getRolesForGuild(BotUtils.getUCIGuild()).contains(BotUtils.getRoleByID(350330574134706176L))) {
                target.removeRole(BotUtils.getRoleByID(350330574134706176L));
                outMessage = "Warning successfully removed.";
            } else {
                outMessage = "ERROR: User does not have a warning.";
            }
        } else {
            outMessage = "ERROR: Please input a user to unwarn.";
        }
        BotUtils.sendMessage(event.getChannel(), outMessage);
    }

}