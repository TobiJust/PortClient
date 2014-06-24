package app;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.InputManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.water.WaterFilter;
import info.InfoManager;
import info.PlayerInfo;
import info.VesselInfo;
import java.util.HashMap;
import util.GameInputHandler;
import util.KeyBindings;

/**
 *
 * @author Just
 */
public class GameAppState extends AbstractAppState {
    /* AppState specific */
    
    private SimpleApplication app;
    private Camera cam;
    private CustomFlyByCamera customFlyCam;
    private Node rootNode;
    private AssetManager assetManager;
    private InputManager inputManager;
    private AppStateManager stateManager;
    private ViewPort viewPort;
    private GameInputHandler gameInputHandler;
    private Player player;
    private Node playerNode;
    private Ship ship;
    private WaterFilter water;
    private BulletAppState bulletAppState;
    private RigidBodyControl sceneBody;
    private CharacterControl character;
    boolean up, down, left, right;
    private boolean isFlyByCamera = false;
    private boolean isChat = true;
    private GameWorld world;
    private final Chat chat;
    private HashMap<Integer, Ship> allShips = new HashMap<Integer, Ship>();
    private static HashMap<String, PlayerModel> allPlayers = new HashMap<String, PlayerModel>();
    
    public GameAppState(Chat chat) {
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
        this.rootNode = this.app.getRootNode();
        this.assetManager = this.app.getAssetManager();
        this.inputManager = this.app.getInputManager();
        this.viewPort = this.app.getViewPort();
        
        this.inputManager.addMapping("Camera", KeyBindings.CAMERA_CHANGE);
        customFlyCam = new CustomFlyByCamera(cam);
        customFlyCam.setMoveSpeed(1f);
        customFlyCam.registerWithInput(inputManager);
        customFlyCam.setDragToRotate(false);
        
        gameInputHandler = new GameInputHandler(this);
        
        
        initWorld();
        initPlayer();
        initShips();
    }
    
    @Override
    public void update(float tpf) {
        if (!isFlyByCamera) {
            player.update(tpf);
        }
        
        for (PlayerInfo pi : InfoManager.getPlayerList()) {
            if (!allPlayers.containsKey(pi.getName())) {
                PlayerModel pm = new PlayerModel("player", this);
                Vector3f playerCoords = new Vector3f(pi.getCoordinates().getX(),
                        pi.getCoordinates().getY(),
                        pi.getCoordinates().getZ());
                pm.setPosition(playerCoords);
                allPlayers.put(pi.getName(), pm);
            } else {
                PlayerModel pm = allPlayers.get(pi.getName());
                if (pi.getState().equals(PlayerInfo.State.FLYING)) {
                    pm.flyPlayer();
                } else {
                    pm.groundPlayer();
                }
                pm.setPosition(new Vector3f(pi.getCoordinates().getX(),
                        pi.getCoordinates().getY(),
                        pi.getCoordinates().getZ()));
            }
            
        }
        
        for (VesselInfo vi : InfoManager.getVesselList()) {
            
            
            if (!allShips.containsKey(vi.getMmsi())) {
                
                ship = new Ship(vi.getType(), this);
                //                ship = new Ship(vi.getType(), this);
                Vector3f shipCoordinates = new Vector3f(
                        vi.getCoordinates().getX(),
                        vi.getCoordinates().getZ(),
                        (vi.getCoordinates().getY()*-1));
                ship.setPosition(shipCoordinates);
                allShips.put(vi.getMmsi(), ship);
            } else {
                
//                    System.out.println("Koord. " + vi.getCoordinates().getX() + " " + vi.getCoordinates().getZ() + " " + 
//                             vi.getCoordinates().getY());
                
                allShips.get(vi.getMmsi()).setPosition(new Vector3f(
                        vi.getCoordinates().getX(),
                        vi.getCoordinates().getZ(),
                        (vi.getCoordinates().getY()*-1)));
                
//                if(vi.getName().contains("BARMBEK"))
                allShips.get(vi.getMmsi()).setHeadDirection(
                        vi.getCourse(), vi.getSpeed());
                allShips.get(vi.getMmsi()).setInformation(vi.getName());
            }
        }
    }
    
    private void initWorld() {
        // init the Game World
        world = new GameWorld(this);
        world.init();
    }
    
    private void initPlayer() {
        player = new Player("player", this);
        playerNode = player.getPlayerNode();
        character = player.getCharacterControl();
    }
    
    public void initPlayers() {
        for (PlayerInfo pi : InfoManager.getPlayerList()) {
            PlayerModel pm = new PlayerModel("player", this);
            Vector3f playerCoords = new Vector3f(pi.getCoordinates().getX(),
                    pi.getCoordinates().getY(),
                    pi.getCoordinates().getZ());
            pm.setPosition(playerCoords);
            allPlayers.put(pi.getName(), pm);
        }
    }
    
    public void initShips() {
        //        Ship.loadShipModels(this);
        if (InfoManager.getVesselList() != null) {
            for (VesselInfo vi : InfoManager.getVesselList()) {
                ship = new Ship("ship", this);
                ship.setPosition(
                        vi.getCoordinates().getX(),
                        vi.getCoordinates().getZ(),
                        vi.getCoordinates().getY());
                allShips.put(vi.getMmsi(), ship);
            }
        }
    }
    
    public void switchCamera() {
        System.out.println("SWITCH CAMERA: " + isFlyByCamera);
        if (!isFlyByCamera) {
            isFlyByCamera = true;
            InfoManager.getPlayer().setState(PlayerInfo.State.FLYING);
        } else {
            isFlyByCamera = false;
            InfoManager.getPlayer().setState(PlayerInfo.State.WALKING);
            Vector3f location = new Vector3f(InfoManager.getPlayer().getCoordinates().getX(),
                    InfoManager.getPlayer().getCoordinates().getY(),
                    InfoManager.getPlayer().getCoordinates().getZ());
            playerNode.setLocalTranslation(location);
            this.cam.setLocation(location);
            player.getCharacterControl().setPhysicsLocation(location);
        }
        this.customFlyCam.setMoveSpeed(150f);
    }
    
    public void toggleChat() {
        if (!isChat) {
            chat.showChatWindow();
            isChat = true;
        } else {
            //            chat.hideChatWindow();
            isChat = false;
        }
    }
    
    public BulletAppState getBulletAppState() {
        return this.bulletAppState;
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public SimpleApplication getApp() {
        return this.app;
    }
    
    public float getTimePerFrame() {
        return this.app.getTimer().getTimePerFrame();
    }
    
    public HashMap<Integer, Ship> getAllShips() {
        return allShips;
    }
    
    public Camera getGameCam() {
        return this.cam;
    }
    
}
