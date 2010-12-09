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
public class Wander extends Behavior{
    private int lastChange = 0;
    private double randX,randY,randChange;
    public Wander(){
        //waga akcja, ustala priorytet w przypadku wielu zachowań
        _weight = 2f;
        randX = Math.random()*360;
        randY = Math.random()*360;
        randChange = Math.random()*7;
        
    }
    public Vector2f CalculateVelocity(Entity entity, int delta){
        reacted = false;
        Vector2f velocity = new Vector2f(entity.getBody().getXVelocity(),entity.getBody().getXVelocity());
        if (lastChange/1000 >= randChange){
            randX = Math.random()*360;
            randY = Math.random()*360;
            lastChange = 0;
        }
        velocity.x = (float)Math.cos(Math.toRadians(randX))*entity._maxSpeed;
        velocity.y = (float)Math.sin(Math.toRadians(randY))*entity._maxSpeed;
        lastChange += delta;
        reacted = true;
        return velocity;
        
    }
}
