package net.cubespace.thesuit.Core;

import com.j256.ormlite.dao.DaoManager;
import net.cubespace.lib.CubespacePlugin;
import net.cubespace.lib.Database.Database;
import net.cubespace.thesuit.Core.Database.Player;

import java.io.File;
import java.sql.SQLException;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class TheSuitPlugin extends CubespacePlugin {
    protected Database database;

    public void onEnable() {
        //Init the config
        getConfigManager().initConfig("database", new net.cubespace.thesuit.Core.Config.Database(this));
        net.cubespace.thesuit.Core.Config.Database dbConfig = getConfigManager().getConfig("database");

        //Init the Database Connection
        database = new Database(this, dbConfig.Url, dbConfig.Username, dbConfig.Password);

        //Register the Core Entities
        try {
            database.registerDAO(DaoManager.createDao(database.getConnectionSource(), Player.class), Player.class);
        } catch (SQLException e) {
            getPluginLogger().error("Could not register Core Entities", e);

            //TODO Find a better way to unload the Plugin
            return;
        }

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
}