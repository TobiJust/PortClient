/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info;

import de.lessvoid.nifty.controls.Console;

/**
 *
 * @author CaLLe
 */
public class InfoManager {
    
    private static Console console;
    private static String playerName;

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
}
