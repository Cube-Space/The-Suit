package net.cubespace.thesuit.Plugin.Config;

import lombok.Getter;
import net.cubespace.Yamler.Config.Comment;
import net.cubespace.Yamler.Config.Config;
import net.cubespace.lib.CubespacePlugin;

import java.io.File;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class Messages extends Config {
    public Messages(CubespacePlugin plugin) {
        CONFIG_FILE = new File(plugin.getDataFolder(), "messages.yml");
    }

    @Comment("Message which gets displayed when a User has no Permission to a Command")
    @Getter
    private String NoPermission = "&cYou have no Permission to execute this";

    @Comment("Message which gets displayed when a Module is not loaded when trying to reload")
    @Getter
    private String Command_TS_ReloadModule_ModuleNotLoaded = "&cThis Module can not be reloaded because it is not loaded";

    @Comment("Message which gets displayed when a Module has thrown an Error when disabling")
    @Getter
    private String Command_TS_ReloadModule_ModuleErrorOnDisable = "&cThe module has been unloaded. It has thrown an Error in the Process of it. Be sure to check the logs";

    @Comment("Message which gets displayed when a Module has thrown an Error when enabling")
    @Getter
    private String Command_TS_ReloadModule_ModuleErrorOnEnable = "&cThe module has been loaded. It has thrown an Error in the Process of it. Be sure to check the logs";

    @Comment("Message which gets displayed when a Module has been successfully reloaded")
    @Getter
    private String Command_TS_ReloadModule_ModuleReloaded = "&6The module has been reloaded";
}
