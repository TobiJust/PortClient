package app;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import info.InfoManager;
import info.Message;
import info.PlayerInfo;
import network.NetworkClient;

/**
 * ScreenController
 *
 */
public class StartScreen extends AbstractAppState implements ScreenController {
    
    private Node rootNode;
    private Node guiNode;
    private Node localRootNode = new Node("Start Screen RootNode");
    private Node localGuiNode = new Node("Start Screen GuiNode");
    private SimpleApplication sApp;
    private Application app;
    private Nifty nifty;
    private Screen screen;
    private final AssetManager assetManager;
    private final ViewPort viewPort;
    private final AppStateManager stateManager;
    
    public StartScreen(SimpleApplication app) {
        this.rootNode = app.getRootNode();
        this.viewPort = app.getViewPort();
        this.guiNode = app.getGuiNode();
        this.assetManager = app.getAssetManager();
        this.stateManager = app.getStateManager();
        this.app = app;
        this.sApp = app;
    }
    
    public void startGame(String nextScreen) {
        nifty.gotoScreen(nextScreen);  // switch to another screen
    }
    
    /**
     * jME3 AppState methods
     */
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        this.app = app;
    }
    
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
    }
    
    @Override
    public void update(float tpf) {
        System.out.println("UPDATE");
        if (screen.getScreenId().equals("hud")) {
        }
    }
    
    @Override
    public void cleanup() {
        rootNode.detachChild(localRootNode);
        guiNode.detachChild(localGuiNode);
        super.cleanup();
    }
    
    public void onStartScreen() {
    }
    
    public void onEndScreen() {
    }
    
    @NiftyEventSubscriber(id = "nametextfield")
    public void onTextFieldInputEvent(final String id, final NiftyInputEvent event){
        if(NiftyInputEvent.SubmitText.equals(event)){
            AppState();
        }
    }
    /**
     * quit the application
     */
    public void QuitGame() {
        app.stop();
    }
    
    /**
     * change State to AppState
     */
    public void AppState() {
        if (screen.getScreenId().equals("StartScreen")) {
            TextField name = screen.findNiftyControl("nametextfield", TextField.class);
            InfoManager.setPlayer(new PlayerInfo(name.getDisplayedText(), 0, 0, 0, PlayerInfo.State.WALKING));
            NetworkClient.getSession().write(new Message(Message.Ident.LOGIN, name.getDisplayedText()));
            Chat chat = new Chat(sApp);
            nifty.fromXml("nifty/chat.xml", "ChatScreen", chat);
            nifty.gotoScreen("ChatScreen");
            
            GameAppState gameAppState = new GameAppState(chat);
            stateManager.attach(gameAppState);
        }
    }
}
