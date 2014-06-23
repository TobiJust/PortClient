/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.io.Serializable;
import java.util.ArrayList;
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
    private static ArrayList<Model> allModels = new ArrayList<Model>();
    private Animation shipAnimation;
    private BitmapText hudText;
    
    public Ship(String id, GameAppState appState){
        
        this.id = id;
        this.appState = appState;
        this.app = appState.getApp();
        hudText = new BitmapText(app.getAssetManager().loadFont("Interface/Fonts/Default.fnt"), false);
        
        createShip();
    }
    
    private void createShip() {
        shipNode = new Node("ship");
        
        //TODO: Model aus vorhandenen wählen
        //        int n = (int)(Math.random()*allModels.size());
        //shipModel = allModels.get(n);
        shipModel = new Model("assets/Models/yacht_1.zip","yacht_1/yacht_1.scene", this.appState);
        shipModel.setPosition((float) (Math.random()*100), 0, 0);
        ship = shipModel.getModel();
        
        appState.getBulletAppState().getPhysicsSpace().add(ship);
        app.getRootNode().attachChild(shipNode);
        
        shipAnimation = new Animation(this, this.appState);
    }
    
    public void setPosition(Vector3f position){
        shipModel.setPosition(position.x, position.y, position.z);
        this.position = position;
    }
    
    public void setPosition(float x, float y, float z){
        shipModel.setPosition(x, y, z);
        this.position = new Vector3f(x,y,z);
    }
    
    public void setHeadDirection(double course) {
        shipModel.setRotation((float) course);
    }
    
    public void setInformation(String info){
        hudText.setSize(8);                                       // font size
        hudText.setColor(ColorRGBA.Gray);                         // font color
        hudText.setText(info);                                    // the text
        hudText.setLocalTranslation(this.position.x,
                this.position.y + hudText.getLineHeight(),
                this.position.z);                                // position
        app.getRootNode().attachChild(hudText);
    }
    
    public static void loadShipModels(GameAppState app){
        allModels.add(new Model("assets/Models/oel_tanker.zip","oel_tanker/oel_tanker.scene", app));
        allModels.add(new Model("assets/Models/autofaehre_klein_1.zip","autofaehre_klein_1/autofaehre_klein.scene", app));
        allModels.add(new Model("assets/Models/autofaehre_mittel_1.zip","autofaehre_mittel_1/autofaehre_mittel_1.scene", app));
        allModels.add(new Model("assets/Models/yacht_1.zip","yacht_1/yacht_1.scene", app));
    }
    
    
}
