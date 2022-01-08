package net.foggies.devroomtrail.api;

import net.foggies.devroomtrail.impl.obj.ParkourArea;

import java.io.IOException;

public interface IParkourStorage {

    /**
     * Called in the event that a parkour data file
     * has not been created, it will generate a new json file
     * and preload a template that can be configured.
     *
     * @return the Parkour Area object generated.
     * @throws IOException the exception.
     */
    ParkourArea createParkourAreaTemplate() throws IOException;

    /**
     * Loads a parkour area from the parkour data file.
     *
     * @return the parkour area.
     * @throws IOException the exception.
     */
    ParkourArea loadParkourArea() throws IOException;

}
