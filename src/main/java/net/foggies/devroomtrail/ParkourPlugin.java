package net.foggies.devroomtrail;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import lombok.Getter;
import net.foggies.devroomtrail.impl.database.PlayerDatabase;
import net.foggies.devroomtrail.impl.events.PlayerMoveListener;
import net.foggies.devroomtrail.impl.manager.LeaderboardManager;
import net.foggies.devroomtrail.impl.manager.ParkourManager;
import net.foggies.devroomtrail.impl.storage.ParkourStorage;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class ParkourPlugin extends JavaPlugin {

    private ParkourStorage parkourStorage;
    private ParkourManager parkourManager;
    private WorldGuardPlugin worldGuardPlugin;
    private WorldGuard worldGuard;
    private PlayerDatabase playerDatabase;
    private LeaderboardManager leaderboardManager;

    @Override
    public void onEnable() {

        this.playerDatabase = new PlayerDatabase();
        this.worldGuardPlugin = WorldGuardPlugin.inst();
        this.worldGuard = WorldGuard.getInstance();

        this.parkourStorage = new ParkourStorage(this);
        this.parkourManager = new ParkourManager(this);
        this.leaderboardManager = new LeaderboardManager(this);

        getServer().getPluginManager().registerEvents(new PlayerMoveListener(this), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
