package net.foggies.devroomtrail.impl.events;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.foggies.devroomtrail.ParkourPlugin;
import net.foggies.devroomtrail.impl.obj.CheckPoint;
import net.foggies.devroomtrail.impl.obj.ParkourArea;
import net.foggies.devroomtrail.impl.obj.ParkourAttempt;
import net.foggies.devroomtrail.utils.RegionUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Optional;

public class PlayerMoveListener implements Listener {

    private ParkourPlugin parkourPlugin;
    private ParkourArea parkourArea;
    private final CheckPoint startLocation;
    private final ProtectedRegion parkourRegion;

    public PlayerMoveListener(ParkourPlugin parkourPlugin) {
        this.parkourPlugin = parkourPlugin;
        this.parkourArea = parkourPlugin.getParkourStorage().getParkourArea();
        this.startLocation = this.parkourArea.getStartLocation();
        this.parkourRegion = RegionUtil.getRegion(this.parkourArea.getStartLocation().getWorldName(), "parkour_area", parkourPlugin);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        ParkourArea parkourArea = parkourPlugin.getParkourStorage().getParkourArea();

        Player player = e.getPlayer();
        Location location = player.getLocation();
        Optional<ParkourAttempt> parkourAttempt = parkourPlugin.getParkourManager().getParkourAttempt(player);


        if (startLocation.isTheSame(location) && parkourAttempt.isEmpty()) {
            parkourPlugin.getParkourManager().beginAttempt(player, startLocation);
        } else if (parkourAttempt.isPresent()) {

            CheckPoint currentCheckpoint = parkourAttempt.get().getCurrentCheckPoint();

            if (!this.parkourRegion.contains(location.getBlockX(), location.getBlockY(), location.getBlockZ())) {
                parkourAttempt.get().setCurrentTries(parkourAttempt.get().getCurrentTries() + 1);
                player.teleport(new Location(player.getWorld(),
                        currentCheckpoint.getX(), currentCheckpoint.getY(), currentCheckpoint.getZ()));
            }

            parkourArea.calculateAttempt(currentCheckpoint, location, parkourAttempt.get(), parkourPlugin.getParkourManager());
        }


    }

}
