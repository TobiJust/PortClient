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
import de.lessvoid.nifty.controls.ChatTextSendEvent;
import de.lessvoid.nifty.controls.Console;
import de.lessvoid.nifty.controls.ConsoleExecuteCommandEvent;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.controls.chatcontrol.ChatControl;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import info.InfoManager;
import info.Message;
import mygame.Main;
import network.NetworkClient;

/**
 * ScreenController
 *
 */
public class Chat extends AbstractAppState implements ScreenController {
    
    private Node rootNode;
    private Node guiNode;
    private Node localRootNode = new Node("Start Screen RootNode");
    private Node localGuiNode = new Node("Start Screen GuiNode");
    private Application app;
    private Nifty nifty;
    private Screen screen;
    private final AssetManager assetManager;
    private final ViewPort viewPort;
    private TextField input;
    
    public Chat(SimpleApplication app) {
        this.rootNode = app.getRootNode();
        this.viewPort = app.getViewPort();
        this.guiNode = app.getGuiNode();
        this.assetManager = app.getAssetManager();
        this.app = app;
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
        InfoManager.setConsole(screen.findNiftyControl("chatInput", Console.class));
        InfoManager.getConsole().getElement().hide();
        input = screen.findNiftyControl("chatInput", TextField.class);
    }
    
    @Override
    public void update(float tpf) {
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
    
    /**
     * change State to AppState
     */
    public void sendMessage() {
        System.out.println("sendMessage");
        if (screen.getScreenId().equals("ChatScreen")) {
            //            NetworkClient.getSession().write(new info.Message(Message.Ident.LOGIN, name.getDisplayedText()));
            System.out.println(input.getDisplayedText());
        }
    }
    
    @NiftyEventSubscriber(id = "chatInput")
    public final void onSendText(final String id, final ChatTextSendEvent event) {
        System.out.println("CHAT CALLBACK " + event.getText());
        ChatControl ct = event.getChatControl();
        ct.receivedChatLine(event.getText(), null);
    }
    
    @NiftyEventSubscriber(id = "chatInput")
    public void onConsoleExecuteCommandEvent(final String id, final ConsoleExecuteCommandEvent cEvent) {
        System.out.println("EXECUTE COMMAND");
        String consoleInput = cEvent.getCommandLine();
        NetworkClient.getSession().write(new Message(Message.Ident.TEXT_MESSAGE, consoleInput));
        hideChatWindow();
    }
    
    public void hideChatWindow(){
        InfoManager.getConsole().getElement().hide();
    }
    public void showChatWindow(){
        InfoManager.getConsole().getElement().show();
        InfoManager.getConsole().getTextField().setFocus();
    }
}
