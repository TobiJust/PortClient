package app;

import com.jme3.animation.Animation;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.water.WaterFilter;
import util.GameInputHandler;
import util.KeyBindings;

/**
 *
 * @author Just
 */
public class GameAppState extends AbstractAppState{
    /* AppState specific */
    
    private SimpleApplication app;
    private Camera cam;
    private FlyByCamera flyCam;
    private Node rootNode;
    private AssetManager assetManager;
    private InputManager inputManager;
    private AppStateManager stateManager;
    private ViewPort viewPort;
    private GameInputHandler gameInputHandler;
    private Player player;
    private Node playerNode;
    private Spatial sceneModel;
    private Spatial ship;
    private WaterFilter water;
    private BulletAppState bulletAppState;
    private RigidBodyControl sceneBody;
    private CharacterControl character;
    boolean up, down, left, right;
    private KeyBindings keyBindings;
    private Animation shipAnimation;
    private boolean isFlyByCamera = false;
    private boolean isChat = false;
    private GameWorld world;
    private final Chat chat;
    
    
    public GameAppState(Chat chat){
        this.chat = chat;
    }
    /* GUI Elements */
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        
        this.stateManager = stateManager;
        this.app = (SimpleApplication) app;
        this.cam = this.app.getCamera();
        this.flyCam = this.app.getFlyByCamera();
        this.rootNode = this.app.getRootNode();
        this.assetManager = this.app.getAssetManager();
        this.inputManager = this.app.getInputManager();
        this.viewPort = this.app.getViewPort();
        keyBindings = new KeyBindings();
        gameInputHandler = new GameInputHandler(this);
        
        flyCam.setDragToRotate(false);
        initWorld();
        initPlayer();
        
    }
    
    @Override
    public void update(float tpf) {
        
        if(!isFlyByCamera)
            player.update(tpf);
        
        //        shipAnimation.update();
        
        
    }
    private void initWorld(){
        // init the Game World
        world = new GameWorld(this);
        world.init();
    }
    
    private void initPlayer() {
        player = new Player("player", this);
        playerNode = player.getPlayerNode();
        character = player.getCharacterControl();
        
    }
    public void switchCamera(){
        if(!isFlyByCamera)
            isFlyByCamera = true;
        else
            isFlyByCamera = false;
        
        this.flyCam.setMoveSpeed(150f);
        
    }
    public void toggleChat() {
        if(!isChat){
            chat.showChatWindow();
            isChat = true;
        }
        else{
//            chat.hideChatWindow();
            isChat = false;
        }
    }
    
    public BulletAppState getBulletAppState() {
        return this.bulletAppState;
    }
    public Player getPlayer(){
        return player;
    }
    public SimpleApplication getApp() {
        return this.app;
    }
}
