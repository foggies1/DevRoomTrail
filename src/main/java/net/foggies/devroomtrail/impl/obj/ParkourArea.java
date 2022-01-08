package net.foggies.devroomtrail.impl.obj;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.foggies.devroomtrail.api.IParkourArea;
import net.foggies.devroomtrail.impl.manager.ParkourManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Optional;


@AllArgsConstructor
@Getter
public class ParkourArea implements IParkourArea {

    public CheckPoint startLocation;
    public ArrayList<CheckPoint> checkpoints;
    public CheckPoint endLocation;

    @Override
    public boolean calculateAttempt(CheckPoint currentCheckpoint, Location location, ParkourAttempt parkourAttempt, ParkourManager parkourManager){

        Player player = parkourAttempt.getPlayer();

        if(isEndCheckPoint(location, parkourAttempt)) {
            parkourManager.completeAttempt(player);
            return true;
        }

        Optional<CheckPoint> checkPointOptional = getCheckpoints().stream()
                .filter(checkPoint -> checkPoint.isTheSame(location))
                .filter(checkPoint -> !checkPoint.equals(currentCheckpoint))
                .filter(checkPoint -> !parkourAttempt.getCheckPointsAchieved().contains(checkPoint))
                .findFirst();

        if(checkPointOptional.isEmpty()) return false;


        parkourAttempt.setCurrentCheckPoint(checkPointOptional.get());
        parkourAttempt.addCheckPointAchieved(checkPointOptional.get());
        parkourAttempt.checkPointReachedInformation();
        return true;
    }

    public boolean isEndCheckPoint(Location location, ParkourAttempt parkourAttempt) {
        return endLocation.isTheSame(location) && parkourAttempt.getCheckPointsAchieved().size() == getCheckpoints().size();
    }

}
