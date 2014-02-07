package net.cubespace.thesuit.Teleport.Listener;

import com.iKeirNez.PluginMessageApiPlus.PacketHandler;
import com.iKeirNez.PluginMessageApiPlus.PacketListener;
import lombok.AllArgsConstructor;
import net.cubespace.lib.CubespacePlugin;
import net.cubespace.thesuit.Teleport.Location.Location;
import net.cubespace.thesuit.Teleport.PluginMessage.LocationResponse;
import net.cubespace.thesuit.Teleport.PluginMessage.TeleportResponse;
import net.cubespace.thesuit.Teleport.TheSuitTeleportModule;
import org.jdeferred.Deferred;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
@AllArgsConstructor
public class PluginMessage implements PacketListener {
    private CubespacePlugin plugin;
    private TheSuitTeleportModule module;

    @PacketHandler
    public void onTeleportResponse(TeleportResponse teleportResponse) {
        //Get the stored Deferred (if there is any)
        Deferred<Boolean, Throwable, Void> deferred = plugin.getDeferredManager().getDeferred(teleportResponse.getDeferredUUID());

        if(deferred == null) {
            module.getModuleLogger().warn("A Teleport Response arrived but the deferred was resolved already. Maybe increase the Timeout");
            return;
        }

        deferred.resolve(teleportResponse.getSuccess());
        plugin.getDeferredManager().removeDeferred(teleportResponse.getDeferredUUID());
    }

    @PacketHandler
    public void onLocationResponse(LocationResponse locationResponse) {
        //Get the stored Deferred (if there is any)
        Deferred<Location, Throwable, Void> deferred = plugin.getDeferredManager().getDeferred(locationResponse.getDeferredUUID());

        if(deferred == null) {
            module.getModuleLogger().warn("A Position Response arrived but the deferred was resolved already. Maybe increase the Timeout");
            return;
        }

        deferred.resolve(new Location(
            locationResponse.getSender().getBungeePlayer().getServer().getInfo(), locationResponse.getWorld(), locationResponse.getX(), locationResponse.getY(), locationResponse.getZ()
        ));
        plugin.getDeferredManager().removeDeferred(locationResponse.getDeferredUUID());
    }
}
