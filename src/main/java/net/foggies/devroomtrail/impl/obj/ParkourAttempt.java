package net.foggies.devroomtrail.impl.obj;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.lucko.helper.utils.Players;
import net.foggies.devroomtrail.api.IParkourAttempt;
import org.apache.commons.lang3.time.StopWatch;
import org.bukkit.entity.Player;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ParkourAttempt implements IParkourAttempt {

    private final Player player;
    private CheckPoint currentCheckPoint;
    private List<CheckPoint> checkPointsAchieved;
    private int currentTries;
    private StopWatch stopWatch;

    @Override
    public void beginTime(){
        this.stopWatch.start();
    }

    @Override
    public void stopTime(){
        this.stopWatch.stop();
    }

    @Override
    public void addCheckPointAchieved(CheckPoint checkpoint){
        if(checkPointsAchieved.contains(checkpoint)) return;
        checkPointsAchieved.add(checkpoint);
    }

    @Override
    public void showAttemptInformation(){
        stopTime();
        Players.msg(player, "");
        Players.msg(player, "&6&l❚ PARKOUR COMPLETED");
        Players.msg(player, "&e   ×  Attempts: &f" + this.currentTries);
        Players.msg(player, "&e   ×  Elapsed Time: &f" + this.stopWatch.formatTime());
    }

    @Override
    public void checkPointReachedInformation(){
        Players.msg(player, "");
        Players.msg(player, "&6&l❚ CHECKPOINT REACHED");
        Players.msg(player, "&e   ×  Current Attempts: &f" + getCurrentTries());
        Players.msg(player, "&e   ×  Check Points Reached: &f" + getCheckPointsAchieved().size());
    }

    public void beginMessage(){
        Players.msg(player, "&6&l❚ PARKOUR BEGUN ;)");
    }


}
