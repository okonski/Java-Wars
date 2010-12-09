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
    private Vector2f move;
    public Follow(Entity target){
        //waga akcja, ustala priorytet w przypadku wielu zachowań
        _weight = 1.3f;
        _target = target;
        move = new Vector2f(0,0);

    }
    public Vector2f CalculateVelocity(Entity entity, int delta){
        reacted = false;
        Vector2f velocity = new Vector2f(0,0);
        Vector2f distance = new Vector2f(_target.getX()-entity.getX(),_target.getY()-entity.getY());
        float distanceTotal = (float)Math.sqrt(distance.x*distance.x+distance.y+distance.y);
        if(distanceTotal <= 820){
            distance.normalise();
            velocity.x = entity._maxSpeed*distance.x;
            velocity.y = entity._maxSpeed*distance.y;
            reacted = true;
            return velocity;
        } else
            return new Vector2f(0,0);
        
   }
}
