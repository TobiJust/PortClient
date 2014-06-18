/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import app.GameAppState;
import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;

/**
 *
 * @author Tobi
 */
public class GameInputHandler implements ActionListener{
    
    private InputManager inputManager;
    private GameAppState app;
    
    public GameInputHandler(GameAppState appState){
        
        this.app = appState;
        this.inputManager = appState.getApp().getInputManager();
        
        setUpKeys();
    }
    
    private void setUpKeys(){
        inputManager.addMapping("Up", KeyBindings.PLAYER_FORWARD);
        inputManager.addMapping("Down", KeyBindings.PLAYER_BACKWARD);
        inputManager.addMapping("Left", KeyBindings.PLAYER_LEFT);
        inputManager.addMapping("Right", KeyBindings.PLAYER_RIGHT);
        inputManager.addMapping("Jump", KeyBindings.PLAYER_JUMP);
        
        inputManager.addMapping("Chat", KeyBindings.CHAT_WINDOW);
        
        inputManager.addMapping("Camera", KeyBindings.CAMERA_CHANGE);
        
        inputManager.addListener(this, new String[]{"Up", "Down", "Left", "Right", "Jump", "Camera", "Chat"});
    }
    /**
     * These are our custom actions triggered by key presses.
     */
    public void onAction(String binding, boolean isPressed, float tpf) {
        if (binding.equals("Left")) {
            app.getPlayer().setTurningLeft(isPressed);
        } else if (binding.equals("Right")) {
            app.getPlayer().setTurningRight(isPressed);
        } else if (binding.equals("Up")) {
            app.getPlayer().setWalkingForward(isPressed);
        } else if (binding.equals("Down")) {
            app.getPlayer().setWalkingBackwards(isPressed);
        } else if (binding.equals("Jump")) {
            if (isPressed) {
                app.getPlayer().getCharacterControl().jump();
            }
        } else if (binding.equals("Camera")) {
            if(isPressed)
                app.switchCamera();            
        } else if (binding.equals("Chat")){
            if(isPressed)
                app.toggleChat();
        }
        
    }
}
