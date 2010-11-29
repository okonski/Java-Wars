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
public class Follow extends Behavior{
    private int lastChange = 0;
    private Entity _target;
    public Follow(Entity target){
        //waga akcja, ustala priorytet w przypadku wielu zachowań
        _weight = 1.3f;
        _target = target;

    }
    public Vector2f CalculateVelocity(Entity entity, int delta){
        Vector2f velocity = entity._velocity;
        Vector2f direction = new Vector2f(_target._position.x,_target._position.y);
        direction.sub(entity._position);
        //velocity = new Vector2f(Math.toDegrees(Math.atan2(entity._position.x-_target._position.x, entity._position.y-_target._position.y)));
        velocity.x = direction.x*delta*entity._acceleration;
        velocity.y = direction.y*delta*entity._acceleration;
        //System.out.println(velocity);
        //velocity.y = _target._position.y;
        return velocity.scale(_weight);

    }
}
