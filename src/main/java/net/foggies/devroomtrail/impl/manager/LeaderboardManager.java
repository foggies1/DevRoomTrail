package net.foggies.devroomtrail.impl.manager;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import dev.jcsoftware.jscoreboards.JPerPlayerMethodBasedScoreboard;
import me.lucko.helper.utils.Players;
import me.lucko.helper.utils.TimeUtil;
import net.foggies.devroomtrail.ParkourPlugin;
import net.foggies.devroomtrail.impl.database.PlayerDatabase;
import net.foggies.devroomtrail.impl.obj.LeaderboardPlayer;
import net.foggies.devroomtrail.utils.RegionUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class LeaderboardManager {

    private ParkourPlugin parkourPlugin;
    private PlayerDatabase playerDatabase;
    private Map<Player, JPerPlayerMethodBasedScoreboard> scoreboardMap;

    public LeaderboardManager(ParkourPlugin parkourPlugin) {
        this.parkourPlugin = parkourPlugin;
        this.playerDatabase = parkourPlugin.getPlayerDatabase();
        this.scoreboardMap = new HashMap<>();
        updateLeaderboards();
    }

    public void putPlayer(Player player, JPerPlayerMethodBasedScoreboard scoreboard){
        scoreboardMap.putIfAbsent(player, scoreboard);
    }

    public JPerPlayerMethodBasedScoreboard getScoreboard(Player player){
        return scoreboardMap.get(player);
    }

    public void updateLeaderboards(){
        Bukkit.getScheduler().runTaskTimer(parkourPlugin, () -> Players.forEach(player -> {

            Set<ProtectedRegion> regions = RegionUtil.getRegions(player, parkourPlugin);
            Optional<ProtectedRegion> protectedRegion = regions.stream()
                    .filter(region -> region.getId().equalsIgnoreCase("parkour_region")).findFirst();

            if(protectedRegion.isEmpty() && getScoreboard(player) != null) {
                JPerPlayerMethodBasedScoreboard scoreboard = getScoreboard(player);
                scoreboard.removePlayer(player);
                return;
            }

            generateScoreboard(player);
        }), 20, 20 * 10L);
    }

    public void generateScoreboard(Player player){
        JPerPlayerMethodBasedScoreboard scoreboard = new JPerPlayerMethodBasedScoreboard();
        List<String> lines = new ArrayList<>();
        int index = 1;

        lines.add("&7┍━");
        lines.add("&7⏐ &6&lBest Attempt: &f" + TimeUtil.toShortForm(parkourPlugin.getPlayerDatabase().getElapsedTime(player.getUniqueId())));
        lines.add("&7┝━");
        lines.add("&7⏐ &6&lLeaderboard: ");

        for(LeaderboardPlayer leaderboardPlayer : parkourPlugin.getPlayerDatabase().getLeaderboard()){
            String name = Bukkit.getOfflinePlayer(leaderboardPlayer.getUuid()).getName();
            lines.add("&7⏐   &6#" + index + " &7- " + name + " &7- &f" + leaderboardPlayer.getElapsedTime());
            index++;
        }

        lines.add("&7┕━");

        scoreboard.setLines(player, lines);
        scoreboard.setTitle(player, "&6&lParkour");
        scoreboard.addPlayer(player);

        putPlayer(player, scoreboard);
    }

}
