package net.cubespace.thesuit.Core.Listener;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.Where;
import net.cubespace.thesuit.Core.Database.Player;
import net.cubespace.thesuit.Core.TheSuitPlugin;
import net.cubespace.thesuit.Core.Util.FeatureDetector;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;
import org.jdeferred.Deferred;
import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;
import org.jdeferred.impl.DeferredObject;

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
        final Deferred<Integer, Exception, Void> def = new DeferredObject();
        plugin.getProxy().getScheduler().runAsync(plugin, new Runnable() {
            public void run() {
                try {
                    Dao<Player, Integer> playerDao = plugin.getDatabase().getDAO(Player.class);
                    Where<Player, Integer> playerWhere = playerDao.queryBuilder().where();
                    Player player;

                    if(FeatureDetector.canUseUUID()) {
                        player = playerWhere.eq("uuid", event.getPlayer().getUUID()).queryForFirst();
                    } else {
                        player = playerWhere.eq("name", event.getPlayer().getName().toLowerCase()).queryForFirst();
                    }

                    if (player == null) {
                        Player newPlayer = new Player();
                        newPlayer.setName(event.getPlayer().getName().toLowerCase());
                        newPlayer.setIp(event.getPlayer().getAddress().getAddress().toString());

                        if(FeatureDetector.canUseUUID()) {
                            newPlayer.setUuid(event.getPlayer().getUUID());
                        }

                        def.resolve(playerDao.create(newPlayer));
                    } else {
                        player.setIp(event.getPlayer().getAddress().getAddress().toString());
                        def.resolve(player.getId());
                    }
                } catch (SQLException ex) {
                    def.reject(ex);
                }

            }
        });

        def.done(new DoneCallback<Integer>() {
            public void onDone(Integer result) {
                plugin.getPluginLogger().info("Found User " + event.getPlayer().getName() + ". He/She has the ID: " + result);
            }
        }).fail(new FailCallback<Exception>() {
            public void onFail(Exception e) {
                plugin.getPluginLogger().error("Error in creating a new Player entry", e);
            }
        });
    }
}
