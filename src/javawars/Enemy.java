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
import org.newdawn.fizzy.Body;
import org.newdawn.fizzy.Circle;
/**
 *
 * @author Piotrek
 */
public class Enemy extends Entity{
    private Circle model;
    private Body body;
    private Game _game;
    private Image _image;
    public Enemy(float x, float y,Game game){
        _acceleration = 0.0005f;
        _game = game;
        try{
            _image = new Image("resources/enemy.png");
        } catch (org.newdawn.slick.SlickException error){

        }
         body = new Body(new Circle(20),x,y,false);
         body.setUserData("enemy");
         body.setFriction(0);
         body.setRestitution(0);
         game.world.add(body);
        _behaviors.add(new Follow(game.player));
        _maxSpeed = 12f;
        color = Color.blue;

    }
    /*
    public void Collide(LinkedList entities){
        Iterator iterator = entities.iterator();
        while(iterator.hasNext()){
            Enemy en = (Enemy)iterator.next();
            if (en == this)
                continue;
            if (body.isTouching(en.getBody())){
            //if (_position.distance(en._position) <= 2.4f*en.model.radius){
            //if (this.model.intersects(en.getModel())){
            
                //en.color = Color.red;
                //this.color = Color.orange;
                

              
                Vector2f dist = new Vector2f(en.getX()-this.getX(),en.getY()-this.getY());
                float waga = (1-(dist.length()/4*en.getModel().radius));
                //waga = (float)Math.pow((double)waga, 2);
                //dist.normalise();
                dist.scale(waga);
                dist.normalise();
                dist.scale(1.1f);
                this._position.add(dist);
                this._velocity.add(dist);
                //dist.scale(-1);
                //en._position.add(dist);
            }
            if (_position.distance(en._position) <= 2*en.model.radius){
                if (en.getX() < this.getX()){
                    this._position.x += (2*this.getModel().radius)-_position.distance(en._position)+0.5f;
                }

            }
            //if (model.intersects(en.getModel())){
            //    en.color = Color.red;
            //}
        }

    }
     */
    @Override
    public void Kill(){
        killed = true;
        //_game.world.remove(body);
        _game.removedEnemies.add(this);
    }
    @Override
    public float getX(){
        return body.getX();
    }
    @Override
    public float getY(){
        return body.getY();
    }
    @Override
    public Body getBody(){
        return body;
    }
    public Vector2f getPosition(){
        return new Vector2f(body.getX(),body.getY());
    }
    public void Update(GameContainer gc, int delta){
        
        Iterator iterator = _behaviors.iterator();
        Vector2f temp = new Vector2f(0,0);
        //iteruj przez wszystkie zachowania i modyfikuj Predkosc
        while(iterator.hasNext()){
            Vector2f v = ((Behavior)iterator.next()).CalculateVelocity((Entity)this,delta);
            temp.x = v.x;
            temp.y = v.y;
        }
        temp.x *= delta*_maxSpeed;
        temp.y *= delta*_maxSpeed;
        //velocity.x += delta*(((float)Math.cos(Math.toRadians(Math.random()*360)) * entity._maxSpeed) - velocity.x) * entity._acceleration;
        
        if (temp.x > _maxSpeed)
            temp.x = _maxSpeed;
        if (temp.y > _maxSpeed)
            temp.y = _maxSpeed;
        //_position.x += temp.x;
        //_position.y += temp.y;
        body.setVelocity(3, 4);
        
    }
    public void Draw(Graphics g){
        //narysuj wektor predkosci i inne opcje domyslne dla klasy Entity
        //super.DrawDebug(model,g);

        //model = (Path)model.transform(transform);
        //model = (Rectangle)model.transform(Transform.createRotateTransform((float)Math.toRadians((double)_rotation),0,30));


        if (killed == false){

            g.setColor(color);
            float rad = ((Circle)body.getShape()).getRadius();
            g.pushTransform();
            g.translate(-rad, -rad);
            //g.draw(model.transform(Transform.createRotateTransform((float)Math.toRadians((double)_rotation),_position.x,_position.y)));
            //g.draw(model);
            int width = (int)((Circle)body.getShape()).getRadius()*2;
            _image = _image.getScaledCopy(width,width);
            

            g.drawImage(_image, body.getX(), body.getY());

            //g.drawOval(body.getX(), body.getY(), ((Circle)body.getShape()).getRadius()*2,2*((Circle)body.getShape()).getRadius());
            g.popTransform();
        }
        
    }
}
