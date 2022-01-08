package net.foggies.devroomtrail.api;

import net.foggies.devroomtrail.impl.obj.CheckPoint;

public interface IParkourAttempt {

    /**
     * Called when a player begins their attempt,
     * used to calculate the elapsed time of their
     * parkour attempt.
     */
    void beginTime();

    /**
     * Called when a player completes / leaves
     * their current attempt. Used to calculate the
     * elapsed time of their parkour attempt.
     */
    void stopTime();

    /**
     * Sends an array of messages to the player
     * once they have completed / left their current
     * parkour attempt regarding their attempt information.
     */
    void showAttemptInformation();

    /**
     * When a player reaches a checkpoint within the Parkour
     * area, we will add it to the checkpoints they have gotten.
     *
     * @param checkpoint the checkpoint reached.
     */
    void addCheckPointAchieved(CheckPoint checkpoint);

    /**
     * Sends and array of messages to the player
     * once they have reached a checkpoint in their
     * parkour attempt regarding their attempt information.
     */
    void checkPointReachedInformation();

}
