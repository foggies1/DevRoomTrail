package net.foggies.devroomtrail;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import lombok.Getter;
import net.foggies.devroomtrail.impl.database.PlayerDatabase;
import net.foggies.devroomtrail.impl.events.PlayerMoveListener;
import net.foggies.devroomtrail.impl.manager.LeaderboardManager;
import net.foggies.devroomtrail.impl.manager.ParkourManager;
import net.foggies.devroomtrail.impl.storage.ParkourStorage;
import net.foggies.devroomtrail.utils.ConfigManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class ParkourPlugin extends JavaPlugin {

    private ParkourStorage parkourStorage;
    private ParkourManager parkourManager;
    private WorldGuardPlugin worldGuardPlugin;
    private WorldGuard worldGuard;
    private PlayerDatabase playerDatabase;
    private LeaderboardManager leaderboardManager;
    private ConfigManager configManager;
    private String mongoURI;
    private String database;
    private String collection;

    @Override
    public void onEnable() {

        this.configManager = ConfigManager.getInstance();
        this.configManager.setPlugin(this);

        loadMongoCredentials();

        this.playerDatabase = new PlayerDatabase(this);
        this.worldGuardPlugin = WorldGuardPlugin.inst();
        this.worldGuard = WorldGuard.getInstance();

        this.parkourStorage = new ParkourStorage(this);
        this.parkourManager = new ParkourManager(this);
        this.leaderboardManager = new LeaderboardManager(this);

        getServer().getPluginManager().registerEvents(new PlayerMoveListener(this), this);

    }

    public void loadMongoCredentials(){
        FileConfiguration fileConfiguration = this.configManager.getConfig("mongoDB.yml");

        this.mongoURI = fileConfiguration.getString("mongoURI");
        this.database = fileConfiguration.getString("database_name");
        this.collection = fileConfiguration.getString("collection_name");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
