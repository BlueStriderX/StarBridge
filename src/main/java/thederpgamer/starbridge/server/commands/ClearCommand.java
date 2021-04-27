package thederpgamer.starbridge.server.commands;

import api.mod.StarMod;
import api.utils.game.PlayerUtils;
import api.utils.game.chat.CommandInterface;
import net.dv8tion.jda.api.entities.Command;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.requests.restaction.CommandUpdateAction;
import org.jetbrains.annotations.Nullable;
import org.schema.game.common.data.player.PlayerState;
import thederpgamer.starbridge.StarBridge;
import thederpgamer.starbridge.utils.LogUtils;

/**
 * ClearCommand
 * <Description>
 *
 * @author TheDerpGamer
 * @since 04/13/2021
 */
public class ClearCommand implements CommandInterface, DiscordCommand {

    @Override
    public String getCommand() {
        return "clear";
    }

    @Override
    public String[] getAliases() {
        return new String[] {
                "cl"
        };
    }

    @Override
    public String getDescription() {
        return "Clears old data of the specified type.\n" +
                "- /%COMMAND% [logs/l] : Clears old (non-active) logs.";
    }

    @Override
    public boolean isAdminOnly() {
        return true;
    }

    @Override
    public boolean onCommand(PlayerState playerState, String[] args) {
        if(args == null || args.length == 0 || (args.length == 1 && (args[0].equalsIgnoreCase("logs") || args[0].equalsIgnoreCase("l")))) {
            LogUtils.clearLogs();
            PlayerUtils.sendMessage(playerState, "Successfully cleared " + (StarBridge.instance.maxWorldLogs - 1) + " logs");
            return true;
        } else return false;
    }

    @Override
    public void serverAction(@Nullable PlayerState playerState, String[] args) {

    }

    @Override
    public StarMod getMod() {
        return StarBridge.instance;
    }

    @Override
    public void execute(SlashCommandEvent event) {
        String message = event.getCommandPath().trim().replace("/", " ").toLowerCase();
        String dataType = event.getOption("data_type").getAsString();
        if(dataType.equalsIgnoreCase("logs") || dataType.equalsIgnoreCase("l")) {
            LogUtils.clearLogs();
            event.reply("Successfully cleared " + (StarBridge.instance.maxWorldLogs - 1) + " logs").queue();
        } else event.reply("Incorrect usage \"/" + message + "\". Use /help clear for proper usages.").queue();
    }

    @Override
    public CommandUpdateAction.CommandData getCommandData() {
        CommandUpdateAction.CommandData commandData = new CommandUpdateAction.CommandData(getCommand(), "Clears old data of the specified type");
        commandData.addOption(new CommandUpdateAction.OptionData(Command.OptionType.STRING, "data_type", "The type of the data to clear").setRequired(true));
        return commandData;
    }
}
