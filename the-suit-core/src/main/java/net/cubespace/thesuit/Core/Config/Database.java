package net.cubespace.thesuit.Core.Config;

import lombok.Getter;
import lombok.Setter;
import net.cubespace.Yamler.Config.Comment;
import net.cubespace.Yamler.Config.Comments;
import net.cubespace.Yamler.Config.Config;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
@Getter
@Setter
public class Database extends Config {
    public Database() {
        CONFIG_HEADER = new String[]{"Configuration of the Database"};
    }

    @Comments({
        "This is the URL of the Database",
        "Must be jdbc:<database engine>:<connection parameter>",
        "For H2 (which is the default file based DB): jdbc:h2:{DIR}thesuit.db",
        "For MySQL: jdbc:mysql://<host>:<port>/<database>"
    })
    private String Url = "jdbc:h2:{DIR}thesuit.db";

    @Comment("The Username which should be used to auth against the Database")
    private String Username = "test";

    @Comment("The Password for the User")
    private String Password = "test";
}
