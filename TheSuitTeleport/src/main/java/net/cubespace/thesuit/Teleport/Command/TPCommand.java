package net.cubespace.thesuit.Teleport.Command;

import lombok.AllArgsConstructor;
import net.cubespace.lib.Chat.FontFormat;
import net.cubespace.lib.Chat.MessageBuilder.MessageBuilder;
import net.cubespace.lib.Command.CLICommand;
import net.cubespace.lib.Command.Command;
import net.cubespace.thesuit.Teleport.Config.TheSuitTeleportMessages;
import net.cubespace.thesuit.Teleport.Location.Location;
import net.cubespace.thesuit.Teleport.TheSuitTeleportModule;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.jdeferred.Deferred;
import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
@AllArgsConstructor
public class TPCommand implements CLICommand {
    private TheSuitTeleportModule module;

    @Command(arguments = 0, command = "tp")
    public void tpCommand(final CommandSender sender, String[] args) {
        //Get the message Config
        final TheSuitTeleportMessages messages = module.getConfigManager().getConfig("messages");

        //Check if the Sender is a Player
        if (!(sender instanceof ProxiedPlayer)) {
            MessageBuilder messageBuilder = new MessageBuilder();
            messageBuilder.setText(FontFormat.translateString(messages.getCommand_TP_NotPlayer())).send(sender);
            return;
        }

        final ProxiedPlayer send = (ProxiedPlayer) sender;

        //Check if none argument is given => Help
        if (args.length == 0) {
            MessageBuilder messageBuilder = new MessageBuilder();
            messageBuilder.setText(FontFormat.translateString(messages.getCommand_TP_Help_Player())).send(sender);
            messageBuilder.setText(FontFormat.translateString(messages.getCommand_TP_Help_Location())).send(sender);

            return;
        }

        //Check if first argument is a Player
        final ProxiedPlayer player;
        if ((player = BungeeCord.getInstance().getPlayer(args[0])) != null) {
            //Check if Player is allowed to TP to other Players
            if(!module.getPlugin().getPermissionManager().has(sender, "thesuit.command.tp.to.player")) {
                MessageBuilder messageBuilder = new MessageBuilder();
                messageBuilder.setText(FontFormat.translateString(messages.getCommand_TP_NoPermissionForPlayerTP())).send(sender);
                return;
            }

            //Get Players location
            Deferred<Location, Throwable, Void> locationDeferred = module.getPosition(player);
            locationDeferred.done(new DoneCallback<Location>() {
                @Override
                public void onDone(Location location) {
                    Deferred<Boolean, Throwable, Void> teleportDeferred = module.teleport(send, location);
                    teleportDeferred.done(new DoneCallback<Boolean>() {
                        @Override
                        public void onDone(Boolean aBoolean) {
                            if(aBoolean) {
                                MessageBuilder messageBuilder = new MessageBuilder();
                                messageBuilder.setText(FontFormat.translateString(messages.getCommand_TP_ToPlayerTeleportSuccess())).setVariable("nick", player.getName()).send(sender);
                            } else {
                                MessageBuilder messageBuilder = new MessageBuilder();
                                messageBuilder.setText(FontFormat.translateString(messages.getCommand_TP_NotTeleported())).send(sender);
                                module.getModuleLogger().warn("Player could not be teleported. Bukkit returned false");
                            }
                        }
                    });

                    teleportDeferred.fail(new FailCallback<Throwable>() {
                        @Override
                        public void onFail(Throwable throwable) {
                            MessageBuilder messageBuilder = new MessageBuilder();
                            messageBuilder.setText(FontFormat.translateString(messages.getCommand_TP_NotTeleported())).send(sender);
                            module.getModuleLogger().warn("Player could not be teleported.", throwable);
                        }
                    });
                }
            });

            locationDeferred.fail(new FailCallback<Throwable>() {
                @Override
                public void onFail(Throwable throwable) {
                    module.getModuleLogger().warn("Could not get Players Position", throwable);
                    MessageBuilder messageBuilder = new MessageBuilder();
                    messageBuilder.setText(messages.getCommand_TP_NotTeleported()).send(sender);
                }
            });

            return;
        }

        //Check if arguments are coordinates
        if (args.length == 3) {
            final int x = Integer.valueOf(args[0]);
            final int y = Integer.valueOf(args[1]);
            final int z = Integer.valueOf(args[2]);

            //Get Players location
            Deferred<Location, Throwable, Void> locationDeferred = module.getPosition(send);
            locationDeferred.done(new DoneCallback<Location>() {
                @Override
                public void onDone(Location location) {
                    location.setX(x);
                    location.setY(y);
                    location.setZ(z);

                    Deferred<Boolean, Throwable, Void> teleportDeferred = module.teleport((ProxiedPlayer) sender, location);
                    teleportDeferred.done(new DoneCallback<Boolean>() {
                        @Override
                        public void onDone(Boolean aBoolean) {
                            if(aBoolean) {
                                MessageBuilder messageBuilder = new MessageBuilder();
                                messageBuilder.setText(FontFormat.translateString(messages.getCommand_TP_ToLocationTeleportSuccess())).
                                        setVariable("x", String.valueOf(x)).setVariable("y", String.valueOf(y)).setVariable("z", String.valueOf(z)).send(sender);
                            } else {
                                MessageBuilder messageBuilder = new MessageBuilder();
                                messageBuilder.setText(FontFormat.translateString(messages.getCommand_TP_NotTeleported())).send(sender);
                                module.getModuleLogger().warn("Player could not be teleported. Bukkit returned false");
                            }
                        }
                    });

                    teleportDeferred.fail(new FailCallback<Throwable>() {
                        @Override
                        public void onFail(Throwable throwable) {
                            MessageBuilder messageBuilder = new MessageBuilder();
                            messageBuilder.setText(FontFormat.translateString(messages.getCommand_TP_NotTeleported())).send(sender);
                            module.getModuleLogger().warn("Player could not be teleported.", throwable);
                        }
                    });
                }
            });

            locationDeferred.fail(new FailCallback<Throwable>() {
                @Override
                public void onFail(Throwable throwable) {
                    module.getModuleLogger().warn("Could not get Players Position", throwable);
                    MessageBuilder messageBuilder = new MessageBuilder();
                    messageBuilder.setText(messages.getCommand_TP_NotTeleported()).send(sender);
                }
            });
        }
    }
}
