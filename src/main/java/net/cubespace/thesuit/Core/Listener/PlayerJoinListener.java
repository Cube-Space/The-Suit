package net.cubespace.thesuit.Core.Listener;

import com.j256.ormlite.dao.Dao;
import net.cubespace.thesuit.Core.Database.Player;
import net.cubespace.thesuit.Core.TheSuitPlugin;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import java.sql.SQLException;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class PlayerJoinListener implements Listener {
    private TheSuitPlugin plugin;

    public PlayerJoinListener(TheSuitPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(final PostLoginEvent event) {
        plugin.getProxy().getScheduler().runAsync(plugin, new Runnable() {
            @Override
            public void run() {
                try {
                    Dao<Player, Integer> playerDao = plugin.getDatabase().getDAO(Player.class);
                    Player player = playerDao.queryBuilder().where().eq("uuid", event.getPlayer().getUUID()).queryForFirst();

                    if(player == null) {
                        Player newPlayer = new Player();
                        newPlayer.setName(event.getPlayer().getName());
                        newPlayer.setUuid(event.getPlayer().getUUID());

                        playerDao.create(newPlayer);
                    }
                } catch (SQLException e) {
                    plugin.getPluginLogger().error("Error in creating a new Player entry", e);
                }
            }
        });
    }
}
