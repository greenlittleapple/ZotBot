import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.MessageTokenizer;

class Warning {

    static void warn(MessageReceivedEvent event, String reasonInput, IUser moderator, boolean automod) {
        String message = event.getMessage().toString();
        String outMessage = "";
        String reason = "Breaking a server rule";
        MessageTokenizer tokenizer = new MessageTokenizer(Main.client, message);
        tokenizer.nextWord();
        if(tokenizer.hasNextWord() || automod) {
            String user = tokenizer.nextWord().toString();
            if(reasonInput.equals("")) {
                if(tokenizer.getRemainingContent().length() != 0)
                    reason = tokenizer.getRemainingContent();
            } else {
                reason = reasonInput;
            }
            IUser target;
            if(automod)
                target = event.getAuthor();
            else if(event.getMessage().getMentions().size() != 0)
                target = event.getMessage().getMentions().get(0);
            else
                target = BotUtils.findUser(user, event.getGuild());
            assert target != null;
            if (!target.getRolesForGuild(BotUtils.getUCIGuild()).contains(BotUtils.getRoleByID(350330574134706176L))) {
                target.addRole(BotUtils.getRoleByID(350330574134706176L));
                //Embed message for PMing warned user
                EmbedBuilder builder = new EmbedBuilder();
                builder.withAuthorName("Warned on UCI Server");
                builder.withColor(255,0,0);
                builder.appendField("Moderator", moderator.getDisplayName(BotUtils.getUCIGuild()) + "#" + moderator.getDiscriminator(), true);
                builder.appendField("Reason", reason, false);
                BotUtils.sendMessage(Main.client.getOrCreatePMChannel(target), builder.build());
                //Embed WARN message for #mod-log
                builder = new EmbedBuilder();
                builder.withAuthorName("Warned User");
                builder.withColor(255,0,0);
                builder.appendField("Moderator", moderator.getDisplayName(BotUtils.getUCIGuild()) + "#" + moderator.getDiscriminator(), true);
                builder.appendField("User",target.getDisplayName(BotUtils.getUCIGuild()) + "#" + target.getDiscriminator(), false);
                builder.appendField("Reason", reason, false);
                BotUtils.sendMessage(Main.client.getChannelByID(342545356531302413L), builder.build());
                //Chat message in channel
                outMessage = "<@" + target.getStringID() + "> You have been warned for reason: " + reason + ". You will be banned on your second warning. If this warning was given in error, please contact a moderator.";
            } else {
                BotUtils.getUCIGuild().banUser(target,reason, 7);
                //Embed BAN message for #mod-log
                EmbedBuilder builder = new EmbedBuilder();
                builder.withAuthorName("Banned User");
                builder.withColor(255,0,0);
                builder.appendField("Moderator", moderator.getDisplayName(BotUtils.getUCIGuild()) + "#" + moderator.getDiscriminator(), true);
                builder.appendField("User",target.getDisplayName(BotUtils.getUCIGuild()) + "#" + target.getDiscriminator(), false);
                builder.appendField("Reason", reason, false);
                BotUtils.sendMessage(Main.client.getChannelByID(342545356531302413L), builder.build());
            }
        } else {
            outMessage = "ERROR: Please input a user to warn.";
        }
        BotUtils.sendMessage(event.getChannel(), outMessage);
    }

    static void unwarn(MessageReceivedEvent event) {
        String outMessage;
        MessageTokenizer tokenizer = new MessageTokenizer(Main.client, event.getMessage().getContent());
        tokenizer.nextWord();
        IUser target = null;
        if(event.getMessage().getMentions().size() != 0)
             target = event.getMessage().getMentions().get(0);
        else if(tokenizer.hasNextWord())
            target = BotUtils.findUser(tokenizer.nextWord().toString(), event.getGuild());
        if(target != null) {
            if (target.getRolesForGuild(BotUtils.getUCIGuild()).contains(BotUtils.getRoleByID(350330574134706176L))) {
                target.removeRole(BotUtils.getRoleByID(350330574134706176L));
                outMessage = "Warning successfully removed.";
            } else {
                outMessage = "ERROR: User does not have a warning.";
            }
        } else
            outMessage = "ERROR: Please input a user to unwarn.";
        BotUtils.sendMessage(event.getChannel(), outMessage);
    }

    static void report(MessageReceivedEvent event) {
        IUser author = event.getAuthor();
        MessageTokenizer tokenizer = new MessageTokenizer(Main.client, event.getMessage().getContent());
        tokenizer.nextWord();
        if(tokenizer.hasNextWord()) {
            IUser target = BotUtils.findUser(tokenizer.nextWord().getContent(), event.getGuild());
            if(tokenizer.getRemainingContent().length() != 0) {
                String reason = tokenizer.getRemainingContent();
                EmbedBuilder builder = new EmbedBuilder();
                builder.withAuthorName("Report Submitted");
                builder.withColor(255, 0, 0);
                builder.appendField("Reporter", author.getDisplayName(BotUtils.getUCIGuild()) + "#" + author.getDiscriminator(), true);
                builder.appendField("Reportee", target.getDisplayName(BotUtils.getUCIGuild()) + "#" + target.getDiscriminator(), false);
                builder.appendField("Reason", reason, false);
                BotUtils.sendMessage(BotUtils.getUCIGuild().getChannelByID(342545356531302413L), "<@&342539333015699457>");
                BotUtils.sendMessage(BotUtils.getUCIGuild().getChannelByID(342545356531302413L), builder.build());
                BotUtils.sendMessage(Main.client.getOrCreatePMChannel(author), "Report successfully submitted for user " + target.getDisplayName(BotUtils.getUCIGuild()) + "#" + target.getDiscriminator() + ", thank you for helping!");
            } else {
                BotUtils.sendMessage(Main.client.getOrCreatePMChannel(author), "Please input a reason in your report! (Ex: z!report [user] [reason])");
            }
        } else {
            BotUtils.sendMessage(Main.client.getOrCreatePMChannel(author), "Please input a person to report! (Ex: z!report [user] [reason])");
        }
        event.getMessage().delete();
    }

}