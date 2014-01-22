package net.cubespace.thesuit.Core;

import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.LruObjectCache;
import lombok.Getter;
import net.cubespace.Yamler.Config.InvalidConfigurationException;
import net.cubespace.lib.Database.Database;
import net.cubespace.lib.Module.Module;
import net.cubespace.thesuit.Core.Database.Player;
import net.cubespace.thesuit.Core.Listener.PlayerJoinListener;

import java.sql.SQLException;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class TheSuitCoreModule extends Module {
    @Getter
    private Database database;

    @Override
    public void onLoad() {
        //Expose the Database config to the Module
        getConfigManager().registerConfig("database", net.cubespace.thesuit.Core.Config.Database.class);

        //Init the config
        net.cubespace.thesuit.Core.Config.Database dbConfig = getConfigManager().getConfig("database");

        //Init the Database Connection
        database = new Database(plugin, dbConfig.getUrl(), dbConfig.getUsername(), dbConfig.getPassword());
    }

    @Override
    public void onEnable() {
        //Register the Core Entities
        try {
            database.registerDAO(DaoManager.createDao(database.getConnectionSource(), Player.class), Player.class);

            //Enable LRU Cache for the Player DAO
            database.getDAO(Player.class).setObjectCache(new LruObjectCache(250));
        } catch (SQLException e) {
            //Tell the module Loader we failed :(
            throw new RuntimeException("Could not register Core Entities", e);
        }

        //Register the PlayerJoinListener so we are able to save Players on join
        plugin.getProxy().getPluginManager().registerListener(plugin, new PlayerJoinListener(plugin));
    }

    @Override
    public void onDisable() {
        net.cubespace.thesuit.Core.Config.Database dbConfig = getConfigManager().getConfig("database");

        try {
            dbConfig.save();
        } catch (InvalidConfigurationException e) {
            getModuleLogger().warn("Could not save DB Config", e);
        }

        try {
            database.getConnectionSource().close();
        } catch (SQLException e) {
            getModuleLogger().warn("Could not close JDBC Pool", e);
        }
    }
}
