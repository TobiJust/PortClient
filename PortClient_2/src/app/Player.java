package app;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import info.InfoManager;
import info.Message;
import info.PlayerInfo;
import info.Point3D;
import java.io.Serializable;
import network.NetworkClient;

/**
 * Class
 * <code>Player</code> <br>
 *
 *
 * @author Just
 */
public class Player implements Serializable{
    
    /** the character identifier  */
    public String id;
    /** the main node of the character */
    Node playerNode;
    /** the graphical app in which the character live */
    SimpleApplication app;
    /** Player Collision node */
    Node playerCollisionNode;
    /** First Person Camera*/
    private Camera cam;
    /** Position of the player */
    private Vector3f position;
    
    /**
     * boolean variable used by the input handler to set the movement to perform
     */
    boolean walkingBackwards, walkingForward, running, turningLeft, turningRight, nowTurning;
    private CharacterControl player;
    private Vector3f walkDirection = new Vector3f();
    private Vector3f camDir = new Vector3f();
    private Vector3f camLeft = new Vector3f();
    private final GameAppState appState;
    
    /**
     * Player constructor <br>
     * Create a new graphical player
     *
     * @param id - (String) the character's identifier
     * @param appState - (GraphicalWorld) the graphical app
     * @param model - (Node) the 3d model to apply
     */
    public Player(String id, GameAppState appState) {
        
        this.id = id;
        this.appState = appState;
        this.app = appState.getApp();
        
        this.cam = app.getCamera();
        createPlayer();
        
    }
    
    private void createPlayer() {
        playerNode = new Node("player");
        //player = new BetterCharacterControl(0.3f, 2.8f, 70f);
        
        CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(1.5f, 1.8f, 2);
        player = new CharacterControl(capsuleShape, 0.05f);
        player.setJumpSpeed(15);
        player.setFallSpeed(30);
        player.setGravity(30);
        player.setPhysicsLocation(new Vector3f(0, 10, 0));
        
        playerNode.addControl(player);
        appState.getBulletAppState().getPhysicsSpace().add(player);
        player.warp(new Vector3f(100.0f, 60f, -100.0f));
        
        app.getRootNode().attachChild(playerNode);
        
    }
    
    public Vector3f getPosition() {
        return playerNode.getWorldTranslation();
    }
    /**
     * Function
     * <code>update</code> <br>
     * Update the physics character
     *
     * @param time
     */
    
    public void update(float time) {
        
        cam.setLocation(playerNode.getLocalTranslation().add(new Vector3f(0.0f, 1.8f, 0.0f)));
        //playerCollisionNode.getLocalTranslation().set(playerNode.getWorldTranslation());
        
        updateMovements();
        
        // update world
        //        app.getWorld().setPosition(id, playerNode.getWorldTranslation());
        
    }
    
    /**
     */
    private void updateMovements() {
        
        camDir.set(cam.getDirection()).multLocal(0.8f);
        walkDirection.set(0, 0, 0);
        walkDirection.addLocal(camDir);        
        
        camDir.set(cam.getDirection()).multLocal(0.8f);
        camLeft.set(cam.getLeft()).multLocal(0.4f);
        walkDirection.set(0, 0, 0);
        
        if (turningLeft) {
            walkDirection.addLocal(camLeft);
        }
        if (turningRight) {
            walkDirection.addLocal(camLeft.negate());
        }
        if (walkingForward) {
            walkDirection.addLocal(camDir);
        }
        if (walkingBackwards) {
            walkDirection.addLocal(camDir.negate());
        }
        
        walkDirection.setY(0);
        player.setWalkDirection(walkDirection);
        app.getCamera().setLocation(player.getPhysicsLocation());
        Point3D playerPos = new Point3D(player.getPhysicsLocation().getX(), 
                                        player.getPhysicsLocation().getY(), 
                                        player.getPhysicsLocation().getZ());
        PlayerInfo playerInfo = InfoManager.getPlayer();
        playerInfo.setCoordinates(playerPos);
        InfoManager.setPlayer(playerInfo);
        NetworkClient.getSession().write(new Message(Message.Ident.PLAYER_POSITION, playerInfo));
    }
    
    
    /**
     * Function
     * <code>getPlayerNode</code> <br>
     * Return the main Node of the physics character
     *
     * @return the main Node of the physics character
     */
    public Node getPlayerNode() {
        return playerNode;
    }
    
    public Node getCollision() {
        return playerNode;
    }
    
    /**
     * Function
     * <code>getModel</code> <br>
     *
     * @return (Node) the character 3d model
     */
    public CharacterControl getCharacterControl() {
        return player;
    }
    
    public void setWalkingBackwards(boolean walkingBackwards) {
        this.walkingBackwards = walkingBackwards;
    }
    
    public void setWalkingForward(boolean walkingForward) {
        this.walkingForward = walkingForward;
    }
    
    public void setTurningRight(boolean turningRight) {
        this.turningRight = turningRight;
    }
    
    public void setTurningLeft(boolean turningLeft) {
        this.turningLeft = turningLeft;
    }
    
    public void setPosition(Vector3f position){
        this.position = position;
    }
    
    public CharacterControl getPlayer() {
        return this.player;
    }
    
}
