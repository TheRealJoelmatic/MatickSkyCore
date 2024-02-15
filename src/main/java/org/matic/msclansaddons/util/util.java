package org.matic.msclansaddons.util;

import java.util.Objects;

import static org.matic.msclansaddons.data.saveManger.getStringData;

public class util {

    public static boolean hasPlayerPlayedBefore(String playername){
        String isPlayer = getStringData(playername, "uuid", "uuid.yml");

        return Objects.equals(isPlayer, "0.0");
    }
}
