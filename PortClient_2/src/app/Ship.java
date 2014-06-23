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
    
    public static final String TYPE_DEFAULT = "segelboot_1";
    
    public static final String TYPE_MIDDLE = "yacht_1";
    public static final String TYPE_WORK_SHIP = "motorboot_1";
    public static final String TYPE_TANK = "oel_tanker";
    public static final String TYPE_PRIVATE = "segelboot_1";
    public static final String TYPE_AGENCY = "yacht_2";
    public static final String TYPE_PASSENGER = "autofaehre_klein_1";
    
    /** the character identifier  */
    public String type;
    /** the graphical app in which the character live */
    private GameAppState appState;
    private final SimpleApplication app;
    private Node shipNode;
    private Model shipModel;
    private Spatial ship;
    private Vector3f position;
    //    private static ArrayList<Model> allModels = new ArrayList<Model>();
    private Animation shipAnimation;
    private BitmapText hudText;
    
    private static final String[] GROUP_WORK_SHIP = {
        "TUG",
        "SPC",
        "GRF",
        "BLS"
    };
    
    private static final String[] GROUP_PASSENGER = {
        "PA",
        "FBT",
        "ROU"
    };
    
    private static final String[] GROUP_TANK = {
        "CA",
        "CHM",
        "CON",
        "TPG"
    };
    
    private static final String[] GROUP_AGENCY = {
        "PLT",
        "AMR",
        "PMP"
    };
    
    private static final String[] GROUP_MIDDLE = {
        "HYD"
    };
    
    private static final String[] GROUP_PRIVATE = {
        "APL",
        "VLR",
        "YAT"
    };
    
    
    public Ship(String type,GameAppState appState){
        
        this.type = type;
        this.appState = appState;
        this.app = appState.getApp();
        hudText = new BitmapText(app.getAssetManager().loadFont("Interface/Fonts/Default.fnt"), false);
        
        createShip();
    }
    
    private void createShip() {
        shipNode = new Node("ship");
        
        //TODO: Model aus vorhandenen wählen
        //                int n = (int)(Math.random()*allModels.size());
        //        shipModel = allModels.get(n);
        
        shipModel = createModel(getShipGroup(type));
        //        shipModel = new Model("assets/Models/yacht_1.zip","yacht_1/yacht_1.scene", this.appState);
        shipModel.setPosition((float) (Math.random()*100), 0, 0);
        ship = shipModel.getModel();
        
        appState.getBulletAppState().getPhysicsSpace().add(ship);
        app.getRootNode().attachChild(shipNode);
        
        shipAnimation = new Animation(this, this.appState);
    }
    
    public String getShipGroup(String type){
        
        int index;
        for(index=0; index<GROUP_AGENCY.length; index++)
            if(GROUP_AGENCY[index].contains(type))
                return TYPE_AGENCY;
        for(index=0; index<GROUP_MIDDLE.length; index++)
            if(GROUP_MIDDLE[index].contains(type))
                return TYPE_MIDDLE;
        for(index=0; index<GROUP_PRIVATE.length; index++)
            if(GROUP_PRIVATE[index].contains(type))
                return TYPE_PRIVATE;
        for(index=0; index<GROUP_PASSENGER.length; index++)
            if(GROUP_PASSENGER[index].contains(type))
                return TYPE_PASSENGER;
        for(index=0; index<GROUP_TANK.length; index++)
            if(GROUP_TANK[index].contains(type))
                return TYPE_TANK;
        for(index=0; index<GROUP_WORK_SHIP.length; index++)
            if(GROUP_WORK_SHIP[index].contains(type))
                return TYPE_WORK_SHIP;
        
        return TYPE_DEFAULT;
    }
    
    public Model createModel(String identifier) {
        return new Model("assets/Models/"+identifier+".zip",identifier+"/"+ identifier + ".scene", appState);
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
        hudText.lookAt(appState.getGameCam().getLocation(), new Vector3f(0,1,0));
        hudText.setColor(ColorRGBA.Gray);                         // font color
        hudText.setText(info);                                    // the text
        hudText.setLocalTranslation(this.position.x - hudText.getLineWidth()/2,
                this.position.y + hudText.getLineHeight()+10,
                this.position.z);                                // position
        app.getRootNode().attachChild(hudText);
        
    }
    
    //    public static void loadShipModels(GameAppState app){
    //        allModels.add(new Model("assets/Models/oel_tanker.zip","oel_tanker/oel_tanker.scene", app));
    //        allModels.add(new Model("assets/Models/autofaehre_klein_1.zip","autofaehre_klein_1/autofaehre_klein.scene", app));
    //        allModels.add(new Model("assets/Models/autofaehre_mittel_1.zip","autofaehre_mittel_1/autofaehre_mittel_1.scene", app));
    //        allModels.add(new Model("assets/Models/yacht_1.zip","yacht_1/yacht_1.scene", app));
    //    }
    
    
    public Vector3f getPosition(){
        return this.position;
    }
    
    
}
