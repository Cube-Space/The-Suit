package net.cubespace.thesuit.teleport;

import net.cubespace.lib.CubespacePlugin;
import net.cubespace.lib.Module.Module;
import net.cubespace.thesuit.teleport.PluginMessage.TeleportMessage;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class TheSuitTeleportModule extends Module {
    public TheSuitTeleportModule(CubespacePlugin plugin) {
        super(plugin);
    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {
        plugin.getPluginMessageManager("TheSuitTeleport").addPacketToRegister(this, TeleportMessage.class);
    }

    @Override
    public void onDisable() {
        plugin.getPluginMessageManager("TheSuitTeleport").removePacket(this);
    }
}
