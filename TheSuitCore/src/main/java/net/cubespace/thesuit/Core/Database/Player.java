package net.cubespace.thesuit.Core.Database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.Setter;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
@Getter
@Setter
@DatabaseTable(tableName = "ts_player")
public class Player {
    @DatabaseField(generatedId = true)
    private Integer id;
    @DatabaseField(canBeNull = false)
    private String name;
    @DatabaseField(canBeNull = true)
    private String uuid;
    @DatabaseField(canBeNull = false)
    private String ip;
}
