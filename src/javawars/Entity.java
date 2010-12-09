/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package javawars;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.*;
import java.util.Iterator;
import java.util.LinkedList;
import org.newdawn.fizzy.Body;
import org.newdawn.fizzy.Circle;
/**
 *
 * @author Piotrek
 */
public class Entity {
    private Shape model;
    private Body body;
    protected float _turnSpeed = 0.381f;
    protected float _direction = 0;
    protected float _rotation = 0;
    protected float _maxSpeed = 7f;
    protected Vector2f _velocity = new Vector2f(0,0);
    protected LinkedList _behaviors = new LinkedList();
    protected Color color = Color.blue;
    protected boolean killed = false;
    public Entity(){
        
    }

    public Shape getModel(){
        return model;
    }
    public void Kill(){
        killed = true;
        
    }
    public float getX(){
        return 0;
    }
    public float getY(){
        return 0;
    }
    public Body getBody(){
        return body;
    }
    public void DrawDebug(Shape model,Graphics g){
        
        g.setColor(Color.yellow);
        g.drawLine(model.getCenterX(), model.getCenterY(), model.getCenterX()+_velocity.x*20, model.getCenterY()+_velocity.y*20);
        //g.setColor(Color.blue);
       // g.drawOval(model.getCenterX(), model.getCenterY(), model.getBoundingCircleRadius(),model.getBoundingCircleRadius());

    }
}
