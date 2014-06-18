/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info;

import de.lessvoid.nifty.controls.Console;
import java.util.ArrayList;

/**
 *
 * @author CaLLe
 */
public class InfoManager {
    
    private static Console console;
    private static ArrayList<VesselInfo> vesselList;
    private static ArrayList<PlayerInfo> playerList;
    private static PlayerInfo player;

    public static ArrayList<PlayerInfo> getPlayerList() {
        return playerList;
    }

    public static void setPlayerList(ArrayList<PlayerInfo> playerList) {
        InfoManager.playerList = playerList;
    }
    
    public static PlayerInfo getPlayer() {
        return player;
    }

    public static void setPlayer(PlayerInfo player) {
        InfoManager.player = player;
    }
    
    public static Console getConsole() {
        return console;
    }
    
    public static void setConsole(Console console) {
        InfoManager.console = console;
    }
    
    public static void setVesselList(ArrayList<VesselInfo> vesselList){
        
        InfoManager.vesselList = vesselList;
    }
    
    public static ArrayList<VesselInfo> getVesselList(){
        return vesselList;
    }
}
