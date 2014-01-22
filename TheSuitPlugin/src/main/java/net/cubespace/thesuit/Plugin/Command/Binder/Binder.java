package net.cubespace.thesuit.Plugin.Command.Binder;

import net.cubespace.lib.Chat.FontFormat;
import net.cubespace.lib.Chat.MessageBuilder.MessageBuilder;
import net.cubespace.lib.CubespacePlugin;
import net.cubespace.thesuit.Plugin.Config.Messages;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class Binder extends Command {
    protected String commandName;
    protected CubespacePlugin plugin;

    public Binder(CubespacePlugin plugin, String name, String... aliases) {
        super(name, null, aliases);

        this.plugin = plugin;
        this.commandName = name;
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if(plugin.getPermissionManager().has(commandSender, "thesuit.command.*") || plugin.getPermissionManager().has(commandSender, "thesuit.command." + commandName.replace(":", "."))) {
            plugin.getCommandExecutor().onCommand(commandSender, commandName, strings);
        } else {
            MessageBuilder messageBuilder = new MessageBuilder();
            messageBuilder.setText(FontFormat.translateString(((Messages) plugin.getConfigManager().getConfig("messages")).getNoPermission())).send(commandSender);
        }
    }
}
