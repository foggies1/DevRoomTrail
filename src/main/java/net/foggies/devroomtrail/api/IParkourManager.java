package net.foggies.devroomtrail.api;

import net.foggies.devroomtrail.impl.obj.CheckPoint;
import net.foggies.devroomtrail.impl.obj.ParkourAttempt;
import org.bukkit.entity.Player;

import java.util.Optional;

public interface IParkourManager {

    /**
     * Begins a Parkour Attempt for the target player.
     *
     * @param player     the target Bukkit player.
     * @param checkpoint the beginning checkpoint.
     */
    void beginAttempt(Player player, CheckPoint checkpoint);

    /**
     * Called when a player has reached the end
     * point of the parkour area.
     *
     * @param player the Bukkit player.
     */
    void completeAttempt(Player player);

    /**
     * Gets the target player's current Parkour Attempt.
     *
     * @param player the target Bukkit player.
     * @return the parkour attempt.
     */
    Optional<ParkourAttempt> getParkourAttempt(Player player);

    /**
     * Checks to see if the player is currently attempting
     * parkour.
     *
     * @param player the target Bukkit player.
     * @return whether they're currently attempting parkour.
     */
    boolean isCurrentlyAttempting(Player player);

}
