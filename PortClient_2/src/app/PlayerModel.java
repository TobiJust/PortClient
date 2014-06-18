/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import util.Model;

/**
 *
 * @author CaLLe
 */
public class PlayerModel {
    
    /** the character identifier  */
    public String id;
     /** the graphical app in which the character live */
    private GameAppState appState;
    private final SimpleApplication app;
    private Node playerNode;
    private Model playerModel;
    private Spatial player;
    private Vector3f position;
    
    public PlayerModel(String id, GameAppState appState){
        
        this.id = id;
        this.appState = appState;
        this.app = appState.getApp();
        
        createPlayerModel();
    }

    private void createPlayerModel() {
         playerNode = new Node("player");
        //player = new BetterCharacterControl(0.3f, 2.8f, 70f);
         
        CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(1.5f, 1.8f, 2);
        playerModel = new Model("assets/Models/oel_tanker.zip","oel_tanker/oel_tanker.scene", this.appState);
        playerModel.setPosition((float) (Math.random()*100), 0, 0);
        player = playerModel.getModel();
        
        appState.getBulletAppState().getPhysicsSpace().add(player);
        app.getRootNode().attachChild(playerNode);
    }
    public void setPosition(Vector3f position){
        playerModel.setPosition(position.x, position.y, position.z);
        this.position = position;
    }
}
