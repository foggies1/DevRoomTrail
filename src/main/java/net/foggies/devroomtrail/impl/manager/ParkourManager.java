package net.foggies.devroomtrail.impl.manager;

import net.foggies.devroomtrail.ParkourPlugin;
import net.foggies.devroomtrail.api.IParkourManager;
import net.foggies.devroomtrail.impl.obj.CheckPoint;
import net.foggies.devroomtrail.impl.obj.ParkourAttempt;
import org.apache.commons.lang3.time.StopWatch;
import org.bukkit.entity.Player;

import java.util.*;

public class ParkourManager implements IParkourManager {

    private final ParkourPlugin parkourPlugin;
    private Map<UUID, ParkourAttempt> parkourAttemptMap;

    public ParkourManager(ParkourPlugin parkourPlugin) {
        this.parkourPlugin = parkourPlugin;
        this.parkourAttemptMap = new HashMap<>();
    }

    @Override
    public void beginAttempt(Player player, CheckPoint checkpoint){
        if(isCurrentlyAttempting(player)) return;

        ParkourAttempt parkourAttempt = new ParkourAttempt(player, checkpoint, new ArrayList<>(), 1, new StopWatch());
        parkourAttempt.beginMessage();
        parkourAttempt.beginTime();

        parkourAttemptMap.put(player.getUniqueId(), parkourAttempt);
    }

    @Override
    public void completeAttempt(Player player){
        if(!isCurrentlyAttempting(player)) return;

        Optional<ParkourAttempt> currentAttempt = getParkourAttempt(player);
        if(currentAttempt.isEmpty()) return;

        ParkourAttempt attempt = currentAttempt.get();
        attempt.showAttemptInformation();
        parkourPlugin.getPlayerDatabase().storePlayer(attempt);

        parkourAttemptMap.remove(player.getUniqueId());
    }

    @Override
    public Optional<ParkourAttempt> getParkourAttempt(Player player){
        return Optional.ofNullable(parkourAttemptMap.get(player.getUniqueId()));
    }

    @Override
    public boolean isCurrentlyAttempting(Player player){
        return parkourAttemptMap.containsKey(player.getUniqueId());
    }


}
