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
    
    public Vector2f CalculateVelocity(Entity entity, int delta){
        return entity._velocity;
    }
}
