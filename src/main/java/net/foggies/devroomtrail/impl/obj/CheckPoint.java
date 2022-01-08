package net.foggies.devroomtrail.impl.obj;

import lombok.Data;
import lombok.Setter;
import net.foggies.devroomtrail.api.ICheckPoint;
import org.bukkit.Location;

@Data
@Setter
public class CheckPoint implements ICheckPoint {

    private final String worldName;
    private final int x;
    private final int y;
    private final int z;

    @Override
    public boolean isTheSame(Location location){
        return location.getWorld().getName().equalsIgnoreCase(worldName) &&
                location.getBlockX() == x &&
                location.getBlockY() == y &&
                location.getBlockZ() == z;
    }

}
