/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import app.GameAppState;
import com.jme3.asset.AssetManager;
import com.jme3.asset.plugins.ZipLocator;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.io.Serializable;

/**
 *
 * @author Just
 */
public class Model implements Serializable{
    
    private AssetManager assetManager;
    private BulletAppState bulletAppState;
    private Node rootNode;
    private Spatial model;
    private float scale = 1f;
    private Vector3f position = new Vector3f(0f, 0f, 0f);
    
    public Model(String filePath, String sceneName, GameAppState appState){
        this.assetManager = appState.getApp().getAssetManager();
        this.bulletAppState = appState.getBulletAppState();
        this.rootNode = appState.getApp().getRootNode();
        
        // We load the scene from the zip file and adjust its size.
        if(filePath != null)
            assetManager.registerLocator(filePath, ZipLocator.class);
        model = assetManager.loadModel(sceneName);
        model.setLocalScale(scale);
        model.setLocalTranslation(position);
        
        // We set up collision detection for the scene by creating a
        // compound collision shape and a static RigidBodyControl with mass zero.
        CollisionShape shape = CollisionShapeFactory.createMeshShape((Node) model);
        RigidBodyControl body = new RigidBodyControl(shape, 0);
        model.addControl(body);
        
        bulletAppState.getPhysicsSpace().add(model);
        rootNode.attachChild(model);
    }
    
    public void setScale(float scale){
        this.scale = scale;
        this.model.setLocalScale(scale);
        
    }
    
    public void setPosition(float x, float y, float z){
        this.position = new Vector3f(x, y, z);
        
        model.getControl(RigidBodyControl.class).setPhysicsLocation(position);
    }
    public void setRotation(float x, float y, float z, float w){
        Quaternion rotation = new Quaternion(x, y, z, w);
           model.getControl(RigidBodyControl.class).setPhysicsRotation(rotation);
    
    }
    public Spatial getModel(){
        return model;
    }
}
