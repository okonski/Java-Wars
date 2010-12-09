/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package javawars;
import org.newdawn.slick.geom.*;
/**
 *
 * @author Piotrek
 */
public class Behavior {
    protected float _weight = 10;
    public boolean reacted = false;
    public Vector2f CalculateVelocity(Entity entity, int delta){
        return entity._velocity;
    }
    public float WrapAngle(float radians)
        {
            while (radians < -Math.PI)
            {
                radians += Math.PI*2;
            }
            while (radians > Math.PI)
            {
                radians -= Math.PI;
            }
            return radians;
        }
}
