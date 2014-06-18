/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import info.VesselInfo;
import java.util.ArrayList;

/**
 *
 * @author Tobi
 */
public class Animation{

    
    private Vector3f startPos;
    private Vector3f endPos;
    private float interp = 0.0f;
    private float speed;
    private float rotation;
    private Spatial object;
    private GameAppState appState;
    
    public Animation(Spatial object, GameAppState appState)  {
        this.object = object;
        this.appState = appState;
        startPos = new Vector3f(0, 0, 0);
        endPos = new Vector3f(0, 0, 10);
        speed = 0.1f;
    }
//    public void update(){
//        interp += speed * appState.getTimePerFrame();
//        Vector3f currentPos = object.getLocalTranslation();
//        Quaternion currentRot = object.getLocalRotation();
//        Vector3f forward = new Vector3f(0,0,1);
//        
//        if (currentPos.distance(endPos) > 0.1f) {
//            object.setLocalTranslation(new Vector3f().interpolate(startPos, endPos, interp));
//            object.rotate(0, rotation, 0);
//        }
//        object.addControl((RigidBodyControl)object.getControl(0));
//        appState.getBulletAppState().getPhysicsSpace().add(object);
//    }
    
    public void setStartPosition(Vector3f start) {
        this.startPos = start;
    }
    public void setEndPosition(Vector3f end) {
        this.endPos = end;
    }
    public void setSpeed(float speed) {
        this.speed = speed/10;
    }
    public void setRotation(float rotation){
        this.rotation = rotation/10000;
    }
}
