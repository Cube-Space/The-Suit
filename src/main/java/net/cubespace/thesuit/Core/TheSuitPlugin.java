package net.cubespace.thesuit.Core;

import net.cubespace.lib.CubespacePlugin;
import net.cubespace.thesuit.ban.TheSuitBanModule;
import net.cubespace.thesuit.home.TheSuitHomeModule;
import net.cubespace.thesuit.teleport.TheSuitTeleportModule;
import net.cubespace.thesuit.warp.TheSuitWarpModule;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class TheSuitPlugin extends CubespacePlugin {
    public void onEnable() {
        //Init all Submodules
        getModuleManager().registerModule(new TheSuitBanModule(this));
        getModuleManager().registerModule(new TheSuitHomeModule(this));
        getModuleManager().registerModule(new TheSuitTeleportModule(this));
        getModuleManager().registerModule(new TheSuitWarpModule(this));
    }
}
