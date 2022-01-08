package net.foggies.devroomtrail.api;

import org.bukkit.Location;

public interface ICheckPoint {

    /**
     * Checks if the given Bukkit location
     * is the same location as the CheckPoint
     * object.
     *
     * @param location the Bukkit location.
     * @return whether the location is the same as the checkpoint's.
     */
    boolean isTheSame(Location location);

}
