/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.io.Serializable;
import util.Model;

/**
 *
 * @author Tobi
 */
public class Ship implements Serializable{
    
    /** the character identifier  */
    public String id;
     /** the graphical app in which the character live */
    private GameAppState appState;
    private final SimpleApplication app;
    private Node shipNode;
    private Model shipModel;
    private Spatial ship;
    private Vector3f position;
    
    public Ship(String id, GameAppState appState){
        
        this.id = id;
        this.appState = appState;
        this.app = appState.getApp();
        
        createShip();
    }

    private void createShip() {
         shipNode = new Node("ship");
        //player = new BetterCharacterControl(0.3f, 2.8f, 70f);
        
        CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(1.5f, 1.8f, 2);
        shipModel = new Model("assets/Models/oel_tanker.zip","oel_tanker/oel_tanker.scene", this.appState);
        shipModel.setPosition((float) (Math.random()*100), 0, 0);
        ship = shipModel.getModel();
//        ship.setLocalTranslation(new Vector3f(0, 10, 0));
        
        appState.getBulletAppState().getPhysicsSpace().add(ship);
        
        app.getRootNode().attachChild(shipNode);
    }
    public void setPosition(Vector3f position){
        shipModel.setPosition(position.x, position.y, position.z);
        this.position = position;
    }
    
}
