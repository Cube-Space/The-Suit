package net.cubespace.thesuit.Core.Util;

import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class FeatureDetector {
    private static boolean useUUID;

    static {
        try {
            ProxiedPlayer.class.getMethod("getUUID");
            useUUID = true;
        } catch (NoSuchMethodException e) {
            useUUID = false;
        }
    }

    /**
     * Either or not the Player Object has an UUID (UUIDs only work in 1.7+)
     * @return false when not, true when it has
     */
    public static boolean isUseUUID() {
        return useUUID;
    }
}
