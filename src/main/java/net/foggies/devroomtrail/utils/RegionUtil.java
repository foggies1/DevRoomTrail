package net.foggies.devroomtrail.utils;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import net.foggies.devroomtrail.ParkourPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Set;

public class RegionUtil {

    public static ProtectedRegion getRegion(String world, String id,  ParkourPlugin parkourPlugin){
        RegionContainer regionContainer = parkourPlugin.getWorldGuard().getPlatform().getRegionContainer();
        Map<String, ProtectedRegion> regionMap = regionContainer.get(BukkitAdapter.adapt(Bukkit.getWorld(world))).getRegions();
        return regionMap.get(id);
    }

    public static Set<ProtectedRegion> getRegions(Player player, ParkourPlugin parkourPlugin){
        RegionContainer regionContainer = parkourPlugin.getWorldGuard().getPlatform().getRegionContainer();

        RegionQuery regionQuery = regionContainer.createQuery();
        Location location = BukkitAdapter.adapt(player.getLocation());
        ApplicableRegionSet set = regionQuery.getApplicableRegions(location);

        return set.getRegions();
    }

}
