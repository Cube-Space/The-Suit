package net.cubespace.thesuit.Teleport.Config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.cubespace.Yamler.Config.Config;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TheSuitTeleportMessages extends Config {
    private String Command_TP_NotPlayer = "&4You only can TP if you are a Player";
    private String Command_TP_NoPermissionForPlayerTP = "&4You can not directly teleport to Players";
    private String Command_TP_CouldNotGetPlayersLocation = "&4Could not fetch Players Location. Please report this to an Admin";
    private String Command_TP_ToPlayerTeleportSuccess = "&6You where teleported to &2%nick%";
    private String Command_TP_NotTeleported = "&4You could not be teleported";
}
