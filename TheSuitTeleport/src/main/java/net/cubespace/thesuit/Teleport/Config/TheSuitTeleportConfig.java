package net.cubespace.thesuit.Teleport.Config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.cubespace.Yamler.Config.Config;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TheSuitTeleportConfig extends Config {
    private Integer TeleportRequestTimeout = 1000;
    private Integer LocationRequestTimeout = 1000;
}
