/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package javawars;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import java.util.Iterator;
import java.util.LinkedList;
/**
 *
 * @author Piotrek
 */
public class Enemy extends Entity{
    private Circle model;
    public Enemy(Player player, float x, float y){
        model = new Circle(x,y,20);
        _acceleration = 0.0005f;
        _position = new Vector2f(x,y);
        //_behaviors.add(new Wander());
        _behaviors.add(new Follow(player));
        _maxSpeed = 1.2f;
    }
    @Override
    public Circle getModel(){
        return model;
    }
    public void Collide(LinkedList entities){
        Iterator iterator = entities.iterator();
        while(iterator.hasNext()){
            Enemy en = (Enemy)iterator.next();

            if (_position.distance(en._position) <= en.model.radius){
                en.color = Color.red;
                
            }
            //if (model.intersects(en.getModel())){
            //    en.color = Color.red;
            //}
        }

    }
    public void Update(GameContainer gc, int delta){
        color = Color.blue;
        Iterator iterator = _behaviors.iterator();
        Vector2f temp = new Vector2f(0,0);
        //iteruj przez wszystkie zachowania i modyfikuj Predkosc
        while(iterator.hasNext()){
            Vector2f v = ((Behavior)iterator.next()).CalculateVelocity((Entity)this,delta);
            temp.x += v.x;
            temp.y += v.y;
        }
        //temp.x = ((delta*_maxSpeed)-temp.x)*_acceleration;
        //temp.y = ((delta*_maxSpeed)-temp.x)*_acceleration;
        //velocity.x += delta*(((float)Math.cos(Math.toRadians(Math.random()*360)) * entity._maxSpeed) - velocity.x) * entity._acceleration;
        if (temp.x > _maxSpeed)
            temp.x = _maxSpeed;
        if (temp.y > _maxSpeed)
            temp.y = _maxSpeed;
        _position.x += temp.x;
        _position.y += temp.y;
    }
    public void Draw(Graphics g){
        //narysuj wektor predkosci i inne opcje domyslne dla klasy Entity
        super.DrawDebug(model,g);

        //model = (Path)model.transform(transform);
        //model = (Rectangle)model.transform(Transform.createRotateTransform((float)Math.toRadians((double)_rotation),0,30));

        model.setLocation(_position);
        if (killed == false){
            g.setColor(color);
            g.draw(model.transform(Transform.createRotateTransform((float)Math.toRadians((double)_rotation),model.getCenterX(),model.getCenterY())));
        }
    }
}
