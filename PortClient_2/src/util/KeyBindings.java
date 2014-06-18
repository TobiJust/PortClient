package util;

import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;

/**
 *
 * @author Just
 */
public class KeyBindings {
    // Default keybindings
    public static KeyTrigger PLAYER_FORWARD = new KeyTrigger(KeyInput.KEY_W);
    public static KeyTrigger PLAYER_BACKWARD = new KeyTrigger(KeyInput.KEY_S);
    public static KeyTrigger PLAYER_LEFT = new KeyTrigger(KeyInput.KEY_A);
    public static KeyTrigger PLAYER_RIGHT = new KeyTrigger(KeyInput.KEY_D);
    public static KeyTrigger PLAYER_JUMP = new KeyTrigger(KeyInput.KEY_SPACE);
    
    public static KeyTrigger CHAT_WINDOW = new KeyTrigger(KeyInput.KEY_RETURN);
    
    public static KeyTrigger CAMERA_CHANGE = new KeyTrigger(KeyInput.KEY_C);
    
    public static KeyTrigger EXIT_APPLICATION = new KeyTrigger(KeyInput.KEY_ESCAPE);
    
}
