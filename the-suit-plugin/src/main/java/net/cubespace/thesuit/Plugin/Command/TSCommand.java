package net.cubespace.thesuit.Plugin.Command;

import net.cubespace.lib.Chat.FontFormat;
import net.cubespace.lib.Chat.MessageBuilder.MessageBuilder;
import net.cubespace.lib.Command.CLICommand;
import net.cubespace.lib.Command.Command;
import net.cubespace.lib.CubespacePlugin;
import net.cubespace.lib.Module.Module;
import net.cubespace.thesuit.Plugin.Config.Messages;
import net.md_5.bungee.api.CommandSender;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class TSCommand implements CLICommand {
    private CubespacePlugin plugin;

    public TSCommand(CubespacePlugin plugin) {
        this.plugin = plugin;
    }

    @Command(arguments = 1, command = "ts:reloadModule")
    public void onTSReloadModuleCommand(CommandSender sender, String[] args) {
        Module module = plugin.getModuleManager().getModule(args[0]);

        if(module == null) {
            MessageBuilder messageBuilder = new MessageBuilder();
            messageBuilder.setText(FontFormat.translateString(((Messages) plugin.getConfigManager().getConfig("messages")).getCommand_TS_ReloadModule_ModuleNotLoaded())).send(sender);
            return;
        }

        if(!plugin.getModuleManager().disableModule(module)) {
            MessageBuilder messageBuilder = new MessageBuilder();
            messageBuilder.setText(FontFormat.translateString(((Messages) plugin.getConfigManager().getConfig("messages")).getCommand_TS_ReloadModule_ModuleErrorOnDisable())).send(sender);
            return;
        }

        if(!plugin.getModuleManager().enableModule(module.getModuleDescription())) {
            MessageBuilder messageBuilder = new MessageBuilder();
            messageBuilder.setText(FontFormat.translateString(((Messages) plugin.getConfigManager().getConfig("messages")).getCommand_TS_ReloadModule_ModuleErrorOnEnable())).send(sender);
            return;
        }

        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.setText(FontFormat.translateString(((Messages) plugin.getConfigManager().getConfig("messages")).getCommand_TS_ReloadModule_ModuleReloaded())).send(sender);
    }
}
