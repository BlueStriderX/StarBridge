package thederpgamer.starbridge.server.bot;

/**
 * BotThread
 * <Description>
 *
 * @author Garret Reichenbach
 * @since 04/09/2021
 */
public class BotThread extends Thread {

    private DiscordBot discordBot;

    public BotThread(String token, String chatWebhook, long chatChannelId, String logWebhook, long logChannelId) {
        discordBot = new DiscordBot(token, chatWebhook, chatChannelId, logWebhook, logChannelId);
    }

    @Override
    public void run() {
        discordBot.initialize();
    }

    public DiscordBot getBot() {
        return discordBot;
    }
}
