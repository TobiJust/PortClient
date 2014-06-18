/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import com.jme3.input.FlyByCamera;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import info.InfoManager;
import info.PlayerInfo;
import info.Point3D;
import javax.vecmath.Point3d;

/**
 *
 * @author CaLLe
 */
public class CustomFlyByCamera extends FlyByCamera {
    
    public CustomFlyByCamera(Camera cam){
        super(cam);
    }
    
    @Override
    protected void moveCamera(float value, boolean sideways) {
        super.moveCamera(value, sideways);
        PlayerInfo player = InfoManager.getPlayer();
        Vector3f location = cam.getLocation().clone();
        Point3D coords = new Point3D(location.getX(), location.getY(), location.getZ());
        player.setCoordinates(coords);
        InfoManager.setPlayer(player);
    }
}
