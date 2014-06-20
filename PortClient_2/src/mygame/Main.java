package mygame;

import app.CustomFlyByCamera;
import app.StartScreen;
import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.system.AppSettings;
import de.lessvoid.nifty.Nifty;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.NetworkClient;

/**
 *
 */
public class Main extends SimpleApplication {

    /**
     * Screen Controller
     */
    private StartScreen startScreen;
    /**
     * logger
     */
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    /**
     * default application settings
     */
    private static final String FILE_NAME = "ClientSettings.properties";
    private static AppSettings settings;
    private static NetworkClient nClient;
    private CustomFlyByCamera customFlyCam;
    private static SimpleApplication app;

    public static void main(String[] args) {
        app = new Main();

        // disable startscreen load config from file
        app.setShowSettings(false);
        settings = new AppSettings(true);
        File propertiesFile = new File(FILE_NAME);
        InputStream is = null;
        try {
            is = new FileInputStream(propertiesFile);
            settings.load(is);
            // logger.log(Level.SEVERE, settings.toString());
        } catch (IOException ex) {
            logger.log(Level.WARNING, null, ex);
            // default Settings setzen
            settings.putInteger(NetworkClient.CONECTION_TIMEOUT, 5000);
            settings.putString(NetworkClient.HOST_NAME, "localhost");
            settings.putInteger(NetworkClient.PORT, 1234);
            settings.setFullscreen(true);

        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ex) {
                    logger.log(Level.WARNING, null, ex);
                }
            }
        }

        app.setSettings(settings);
        nClient = new NetworkClient(settings);
        (new Thread(nClient)).start();
        app.start();
    }

    @Override
    public void simpleInitApp() {

        // init screen
        startScreen = new StartScreen(this);
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);
        Nifty nifty = niftyDisplay.getNifty();
        nifty.fromXml("nifty/StartScreen.xml", "StartScreen", startScreen);
        nifty.gotoScreen("StartScreen");
        
        // remove the original fly cam
	inputManager.removeListener(flyCam);
	flyCam.setDragToRotate(true);
        
        // create own mapping for escape key
        inputManager.deleteMapping(SimpleApplication.INPUT_MAPPING_EXIT);
        inputManager.addMapping("Exit", new KeyTrigger(KeyInput.KEY_ESCAPE));
        inputManager.addListener(new ActionListener() {
            public void onAction(String name, boolean isPressed, float tpf) {
                NetworkClient.getSession().close(true);
                app.stop();
            }
        }, "Exit");
        
        guiViewPort.addProcessor(niftyDisplay);
    }
    
    public CustomFlyByCamera getCustomFlyByCamera() {
        return customFlyCam;
    }
    
}