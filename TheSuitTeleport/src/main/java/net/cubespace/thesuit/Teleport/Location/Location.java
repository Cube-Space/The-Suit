package net.cubespace.thesuit.Teleport.Location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.md_5.bungee.api.config.ServerInfo;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    private ServerInfo server;
    private String world;
    private Double x;
    private Double y;
    private Double z;

    public void setX(int x) {
        this.x = (double) x;
    }

    public void setY(int y) {
        this.y = (double) y;
    }

    public void setZ(int z) {
        this.z = (double) z;
    }
}
