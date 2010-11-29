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
public class GoUp extends Behavior{

    public GoUp(){
        //waga akcja, ustala priorytet w przypadku wielu zachowań
        _weight = 1f;
    }
    public Vector2f CalculateVelocity(Entity entity, int delta){
        Vector2f velocity = entity._velocity;
        velocity.x += (float)Math.cos(-Math.PI/2);
        velocity.y += (float)Math.sin(-Math.PI/2);
        return velocity.normalise().scale(_weight);
    }
}
