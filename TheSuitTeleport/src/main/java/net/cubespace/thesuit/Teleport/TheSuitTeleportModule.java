package net.cubespace.thesuit.Teleport;

import lombok.NonNull;
import net.cubespace.lib.Module.Module;
import net.cubespace.thesuit.Plugin.Command.Binder.Binder;
import net.cubespace.thesuit.Teleport.Command.TPCommand;
import net.cubespace.thesuit.Teleport.Config.TheSuitTeleportConfig;
import net.cubespace.thesuit.Teleport.Config.TheSuitTeleportMessages;
import net.cubespace.thesuit.Teleport.Listener.PluginMessage;
import net.cubespace.thesuit.Teleport.Location.Location;
import net.cubespace.thesuit.Teleport.PluginMessage.LocationRequest;
import net.cubespace.thesuit.Teleport.PluginMessage.TeleportRequest;
import net.md_5.bungee.api.Callback;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.jdeferred.Deferred;
import org.jdeferred.impl.DeferredObject;

import java.util.UUID;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class TheSuitTeleportModule extends Module {
    @Override
    public void onLoad() {
        getConfigManager().registerConfig("main", TheSuitTeleportConfig.class);
        getConfigManager().registerConfig("messages", TheSuitTeleportMessages.class);
    }

    @Override
    public void onEnable() {
        plugin.getPluginMessageManager("TheSuitTeleport").addPacketToRegister(this, TeleportRequest.class);
        plugin.getPluginMessageManager("TheSuitTeleport").addListenerToRegister(this, new PluginMessage(plugin, this));

        plugin.getBindManager().bind("tp", Binder.class);
        plugin.getCommandExecutor().add(this, new TPCommand(this));
    }

    @Override
    public void onDisable() {
        plugin.getBindManager().unbind("tp");
        plugin.getCommandExecutor().remove(this);

        plugin.getPluginMessageManager("TheSuitTeleport").removeListener(this);
        plugin.getPluginMessageManager("TheSuitTeleport").removePacket(this);

    }

    public Deferred<Boolean, Throwable, Void> teleport(final ProxiedPlayer player, final Location location) {
        //Create a new Deferred Object and register it
        final Deferred<Boolean, Throwable, Void> deferred = new DeferredObject<>();
        final UUID storeID = plugin.getDeferredManager().registerDeferred(deferred,
                ((TheSuitTeleportConfig) getConfigManager().getConfig("main")).getTeleportRequestTimeout());

        //Check if the Player needs to be connected to another Server
        if (!player.getServer().getInfo().equals(location.getServer())) {
            player.connect(location.getServer(), new Callback<Boolean>() {
                @Override
                public void done(Boolean connected, Throwable throwable) {
                    if (connected) {
                        plugin.getPluginMessageManager("TheSuitTeleport").sendPluginMessage(player, new TeleportRequest(
                            storeID, location.getWorld(), location.getX(), location.getY(), location.getZ()
                        ));
                    } else {
                        deferred.reject(throwable);
                    }
                }
            });
        } else {
            plugin.getPluginMessageManager("TheSuitTeleport").sendPluginMessage(player, new TeleportRequest(
                storeID, location.getWorld(), location.getX(), location.getY(), location.getZ()
            ));
        }

        return deferred;
    }

    public Deferred<Location, Throwable, Void> getPosition(final ProxiedPlayer player) {
        //Create a new Deferred Object and register it
        final Deferred<Location, Throwable, Void> deferred = new DeferredObject<>();
        final UUID storeID = plugin.getDeferredManager().registerDeferred(deferred,
                ((TheSuitTeleportConfig) getConfigManager().getConfig("main")).getLocationRequestTimeout());

        //Check if the Player needs to be connected to another Server
        plugin.getPluginMessageManager("TheSuitTeleport").sendPluginMessage(player, new LocationRequest(
            storeID
        ));

        return deferred;
    }
}
