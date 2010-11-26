/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package javawars;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;
/**
 *
 * @author Piotrek
 */
public class Entity {
    private Shape model;
    protected Vector2f _position;
    protected float _turnSpeed = 0.42f;
    protected float _direction = 0;
    protected float _rotation = 0;
    protected float _maxSpeed = 7f;
    protected Vector2f _velocity = new Vector2f(0,0);

    protected float _acceleration = 5f;
    protected float _deceleration = 0.001f;
    public Entity(){
        System.out.println("Entity created");
    }
    public Shape getModel(){
        return model;
    }
    public void DrawDebug(Shape model,Graphics g){
        
        g.setColor(Color.yellow);
        g.drawLine(model.getCenterX(), model.getCenterY(), model.getCenterX()+_velocity.x*20, model.getCenterY()+_velocity.y*20);
        //g.setColor(Color.blue);
       // g.drawOval(model.getCenterX(), model.getCenterY(), model.getBoundingCircleRadius(),model.getBoundingCircleRadius());

    }
}
