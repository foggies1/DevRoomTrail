package net.foggies.devroomtrail.api;

import net.foggies.devroomtrail.impl.obj.LeaderboardPlayer;
import net.foggies.devroomtrail.impl.obj.ParkourAttempt;
import org.bson.Document;

import java.util.Set;
import java.util.UUID;

public interface IPlayerDatabase {

    /**
     * Store a player into a mongo database.
     *
     * @param parkourAttempt the player's parkour attempt object.
     */
    void storePlayer(ParkourAttempt parkourAttempt);

    /**
     * Get the top player's.
     *
     * @return the set of leaderboard players.
     */
    Set<LeaderboardPlayer> getLeaderboard();

    /**
     * Checks if the mongo collection contains
     * the target player.
     *
     * @param uuid the target player's uuid.
     * @return whether it contains the player or not.
     */
    boolean containPlayer(UUID uuid);

    /**
     * Get the player's best parkour
     * attempt time.
     *
     * @param uuid the target player's uuid.
     * @return the elapsed time.
     */
    long getElapsedTime(UUID uuid);

    /**
     * Gets the player's data document.
     *
     * @param uuid the target player's uuid.
     * @return the document object.
     */
    Document getPlayerDocument(UUID uuid);

}
