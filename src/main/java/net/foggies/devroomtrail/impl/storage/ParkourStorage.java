package net.foggies.devroomtrail.impl.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import net.foggies.devroomtrail.ParkourPlugin;
import net.foggies.devroomtrail.api.IParkourStorage;
import net.foggies.devroomtrail.impl.obj.CheckPoint;
import net.foggies.devroomtrail.impl.obj.ParkourArea;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;


public class ParkourStorage implements IParkourStorage {

    private ParkourPlugin parkourPlugin;
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private File folder;
    private File dataFile;
    @Getter private ParkourArea parkourArea;

    public ParkourStorage(ParkourPlugin parkourPlugin) {
        this.parkourPlugin = parkourPlugin;
        this.folder = parkourPlugin.getDataFolder();
        if(!this.folder.exists()) this.folder.mkdirs();

        this.dataFile = new File(folder, "data.json");
        try {
            if (!this.dataFile.exists()) this.dataFile.createNewFile();
            this.parkourArea = loadParkourArea();
        } catch (IOException e){
            System.out.println("Problem when creating the Data Folder for the Parkour Area.");
            e.printStackTrace();
        }

    }

    @Override
    public ParkourArea createParkourAreaTemplate() throws IOException {
        ArrayList<CheckPoint> checkpoints = new ArrayList<>(Arrays.asList(
                new CheckPoint("world", 0, 0, 0),
                new CheckPoint("world", 0, 0, 0),
                new CheckPoint("world", 0, 0, 0)
        ));

        CheckPoint startLocation = new CheckPoint("world", 0, 0, 0);
        CheckPoint endLocation = new CheckPoint("world", 0, 0, 0);

        ParkourArea parkourArea = new ParkourArea(startLocation, checkpoints, endLocation);
        Files.write(dataFile.toPath(), gson.toJson(parkourArea, ParkourArea.class).getBytes(StandardCharsets.UTF_8));
        return parkourArea;
    }

    @Override
    public ParkourArea loadParkourArea() throws IOException {

        ParkourArea parkourArea = gson.fromJson(
                new InputStreamReader(
                new FileInputStream(this.dataFile),
                        StandardCharsets.UTF_8), ParkourArea.class);

        if(parkourArea == null) parkourArea = createParkourAreaTemplate();

        return parkourArea;
    }



}
