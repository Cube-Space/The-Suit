package net.cubespace.thesuit.Teleport;

import net.cubespace.lib.Module.Module;
import net.cubespace.thesuit.Teleport.PluginMessage.TeleportMessage;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class TheSuitTeleportModule extends Module {
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
