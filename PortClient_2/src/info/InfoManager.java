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
    private static String playerName;
    private static ArrayList<VesselInfo> vesselList;
    
    public static Console getConsole() {
        return console;
    }
    
    public static void setConsole(Console console) {
        InfoManager.console = console;
    }
    
    public static String getPlayerName() {
        return playerName;
    }
    
    public static void setPlayerName(String playerName) {
        InfoManager.playerName = playerName;
    }
    
    public static void setVesselList(ArrayList<VesselInfo> vesselList){
        InfoManager.vesselList = vesselList;
    }
    
    public static ArrayList<VesselInfo> getVesselList(){
        return InfoManager.vesselList;
    }
}
