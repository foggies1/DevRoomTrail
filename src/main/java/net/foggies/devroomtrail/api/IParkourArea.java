package net.foggies.devroomtrail.api;

import net.foggies.devroomtrail.impl.manager.ParkourManager;
import net.foggies.devroomtrail.impl.obj.CheckPoint;
import net.foggies.devroomtrail.impl.obj.ParkourAttempt;
import org.bukkit.Location;

public interface IParkourArea {

    /**
     * Calculates all variables of a Parkour Attempt
     * to check if a valid attempt is under way and whether
     * the checkpoints have been reached.
     *
     * @param currentCheckpoint the current checkpoint the player is standing on.
     * @param location          the Bukkit location the player currently is.
     * @param parkourAttempt    the current Parkour Attempt.
     * @param parkourManager    the Parkour Manager object.
     * @return whether the attempt was successful.
     */
    boolean calculateAttempt(CheckPoint currentCheckpoint, Location location, ParkourAttempt parkourAttempt, ParkourManager parkourManager);


    /**
     * Checks if the current Bukkit location is the end checkpoint
     * within the parkour area.
     *
     * @param location       the Bukkit location.
     * @param parkourAttempt the current parkour attempt.
     * @return whether the location is the end or not.
     */
    boolean isEndCheckPoint(Location location, ParkourAttempt parkourAttempt);

}
