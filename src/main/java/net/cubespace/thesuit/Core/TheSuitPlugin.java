package net.cubespace.thesuit.Core;

import com.j256.ormlite.dao.DaoManager;
import net.cubespace.lib.CubespacePlugin;
import net.cubespace.lib.Database.Database;
import net.cubespace.thesuit.Core.Database.Player;
import net.cubespace.thesuit.ban.TheSuitBanModule;
import net.cubespace.thesuit.home.TheSuitHomeModule;
import net.cubespace.thesuit.teleport.TheSuitTeleportModule;
import net.cubespace.thesuit.warp.TheSuitWarpModule;

import java.sql.SQLException;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class TheSuitPlugin extends CubespacePlugin {
    private Database database;

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
        getModuleManager().registerModule(new TheSuitBanModule(this));
        getModuleManager().registerModule(new TheSuitHomeModule(this));
        getModuleManager().registerModule(new TheSuitTeleportModule(this));
        getModuleManager().registerModule(new TheSuitWarpModule(this));

        //Enable the Module and tell the Library we are ready to go
        getModuleManager().enable();
        super.onEnable();
    }

    public Database getDatabase() {
        return database;
    }
}
