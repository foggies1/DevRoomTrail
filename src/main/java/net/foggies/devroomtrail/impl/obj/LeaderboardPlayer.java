package net.foggies.devroomtrail.impl.obj;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class LeaderboardPlayer {

    private UUID uuid;
    private int tries;
    private String elapsedTime;

}
