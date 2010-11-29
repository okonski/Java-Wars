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
        randChange = Math.random()*10;
        
    }
    public Vector2f CalculateVelocity(Entity entity, int delta){
        Vector2f velocity = new Vector2f(entity._velocity.x,entity._velocity.y);
        if (lastChange/1000 >= randChange){
            randX = Math.random()*360;
            randY = Math.random()*360;
            lastChange = 0;
        }
        velocity.x += (float)Math.cos(Math.toRadians(randX));
        velocity.y += (float)Math.sin(Math.toRadians(randY));
        lastChange += delta;
        return velocity.normalise().scale(_weight);
        
    }
}
