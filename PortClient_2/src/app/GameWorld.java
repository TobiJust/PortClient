/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.scene.Node;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;
import com.jme3.water.WaterFilter;
import util.Model;

public class GameWorld extends AbstractAppState{
    
    private SimpleApplication app;
    private FilterPostProcessor fpp;
    private WaterFilter water;
    private Vector3f sunDirection = new Vector3f(-0.1f, -0.7f, -1.0f); // same as light source
    private float initialWaterHeight = 0f; // choose a value for your scene
    private TerrainQuad terrain;
    private Material mat_terrain;
    private Node rootNode;
    private AssetManager assetManager;
    private BulletAppState bulletAppState;
    private final GameAppState appState;
    
    /**
     * constructor
     *
     * @param app
     */
    public GameWorld(GameAppState appState) {
        this.app = appState.getApp();
        this.appState = appState;
        this.rootNode = app.getRootNode();
        this.assetManager = app.getAssetManager();
        
        bulletAppState = appState.getBulletAppState();
    }
    
    public void init() {
        this.loadWorld();
        this.addEnvironment();
    }
    
    private void loadWorld() {
        
        Model scene = new Model("assets/Models/Insel_full.zip","insel_full.scene", this.appState);
        scene.setPosition(0, -20, 0);
    }    
    private void addEnvironment() {
        // Water
        fpp = new FilterPostProcessor(assetManager);
        water = new WaterFilter(rootNode, sunDirection);
        water.setWaterHeight(initialWaterHeight);
        fpp.addFilter(water);
        app.getViewPort().addProcessor(fpp);
        // Light
        addLight();
        // Sky
        addSky();
        
    }
    private void addLight(){
        // Light
                AmbientLight ambient = new AmbientLight();
                ambient.setColor(ColorRGBA.White.mult(1.3f));
                rootNode.addLight(ambient);
        
        // Sun Light
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(sunDirection);
        sun.setColor(ColorRGBA.White.mult(1f));
        rootNode.addLight(sun);
    }
    private void addSky() {
        // Heaven Textures
        Texture west = assetManager.loadTexture("Textures/Sky/sky_Left.jpg");
        Texture east = assetManager.loadTexture("Textures/Sky/sky_Right.jpg");
        Texture north = assetManager.loadTexture("Textures/Sky/sky_Front.jpg");
        Texture south = assetManager.loadTexture("Textures/Sky/sky_Back.jpg");
        Texture top = assetManager.loadTexture("Textures/Sky/sky_Top.jpg");
        Texture bottom = assetManager.loadTexture("Textures/Sky/sky_Bottom.jpg");
        
        rootNode.attachChild(SkyFactory.createSky(assetManager, west, east, north, south, top, bottom));
    }
    public SimpleApplication getApp(){
        return this.app;
    }
    public BulletAppState getBulletAppState() {
        return this.bulletAppState;
    }
    
}
