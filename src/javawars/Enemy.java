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
import java.util.Random;
/**
 *
 * @author Piotrek
 */
public class Enemy extends Entity{
    private Circle model;
    private Body body;
    private Game _game;
    private Image _image;
    public Enemy(float x, float y,Game game,String texture){
        _game = game;
        
        try{
            _image = new Image(texture);
        } catch (org.newdawn.slick.SlickException error){

        }
         body = new Body(new Circle(20),x,y,false);
         body.setUserData("enemy");
         body.setFriction(0);
         body.setRestitution(0);
         body.setDensity(10);
         game.world.add(body);
         _behaviors.add(new Follow(game.player));
         _behaviors.add(new Wander());
        Random rand = new Random();
        _maxSpeed = 74f;
        //wylosuj dodatkowo prędkość, zeby zroznicowac przeciwnikow
        _maxSpeed += rand.nextInt(10);


        color = Color.blue;

    }
    @Override
    public void Kill(){
        killed = true;
        _game.addToRemoval(this);
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
            Behavior be = (Behavior)iterator.next();
            Vector2f v = be.CalculateVelocity((Entity)this,delta);

            temp.x += v.x;
            temp.y += v.y;
            //nie licz dalszych zachowan jesli jedno sie spelnilo
            if (be.reacted)
                break;
        }
        
        if (temp.x > _maxSpeed)
            temp.x = _maxSpeed;
        if (temp.y > _maxSpeed)
            temp.y = _maxSpeed;
        body.setVelocity(temp.x, temp.y);
        
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
            //_image = _image.getScaledCopy(width,width);
            

            g.drawImage(_image, body.getX(), body.getY());

            //g.drawOval(body.getX(), body.getY(), ((Circle)body.getShape()).getRadius()*2,2*((Circle)body.getShape()).getRadius());
            g.popTransform();
        }
        
    }
}
