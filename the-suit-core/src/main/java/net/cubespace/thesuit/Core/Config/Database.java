package net.cubespace.thesuit.Core.Config;

import net.cubespace.Yamler.Config.Config;
import net.cubespace.lib.CubespacePlugin;

import java.io.File;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class Database extends Config {
    public Database(CubespacePlugin plugin) {
        CONFIG_FILE = new File(plugin.getDataFolder(), "database.yml");
        CONFIG_HEADER = new String[]{"Configuration of the Database"};
    }

    public String Url = "jdbc:h2:{DIR}thesuit.db";
    public String Username = "test";
    public String Password = "test";
}
