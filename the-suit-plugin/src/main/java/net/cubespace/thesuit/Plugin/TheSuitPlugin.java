package net.cubespace.thesuit.Plugin;

import net.cubespace.lib.CubespacePlugin;

import java.io.File;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class TheSuitPlugin extends CubespacePlugin {
    public void onEnable() {
        //Set basic things for the Module Manager
        getModuleManager().setModuleSpace("the-suit");

        //Init all Submodules
        File moduleFolder = new File(getDataFolder(), "modules");
        if(!moduleFolder.exists()) {
            moduleFolder.mkdirs();
        }

        getModuleManager().detectModules(moduleFolder);
        getModuleManager().loadAndEnableModules();

        //Tell the Library we are ready to go
        super.onEnable();
    }

    public void onDisable() {
        getModuleManager().disableModules();
    }
}
