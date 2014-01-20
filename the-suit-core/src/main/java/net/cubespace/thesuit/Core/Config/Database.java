package net.cubespace.thesuit.Core.Config;

import net.cubespace.Yamler.Config.Comment;
import net.cubespace.Yamler.Config.Comments;
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

    @Comments({
        "This is the URL of the Database",
        "Must be jdbc:<database engine>:<connection parameter>",
        "For H2 (which is the default file based DB): jdbc:h2:{DIR}thesuit.db",
        "For MySQL: jdbc:mysql://<host>:<port>/<database>"
    })
    public String Url = "jdbc:h2:{DIR}thesuit.db";

    @Comment("The Username which should be used to auth against the Database")
    public String Username = "test";

    @Comment("The Password for the User")
    public String Password = "test";
}
