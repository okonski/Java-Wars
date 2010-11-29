/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package javawars;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.particles.*;
import org.newdawn.slick.particles.ConfigurableEmitter.SimpleValue;
import org.newdawn.slick.particles.ConfigurableEmitter.ColorRecord;
import java.io.File;
import java.io.IOException;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.FastTrig;
import org.newdawn.slick.util.Log;
import java.util.Iterator;
import java.util.LinkedList;
/**
 *
 * @author Piotrek
 */
public class Player extends Entity {
    private Circle model;
    private ParticleSystem particleSystem;
    private float tempAngle;
    private Image obrazek;
    ConfigurableEmitter thrust;
    public Player(float x, float y){
        _position = new Vector2f(x,y);
        _acceleration = 0.02f;
        try{
            obrazek = new Image("hawk.png");
        } catch(org.newdawn.slick.SlickException error){
            
        }
        //model = new Path(0,0);
        //model.lineTo(-20,60);
        //model.lineTo(20,60);
        //model.lineTo(0,0);
        model = new Circle(x,y,36.0f);
          try{
            particleSystem = ParticleIO.loadConfiguredSystem("thrust.xml");

            //particleSystem = new ParticleSystem("spark.png", 10000);

            //thrust = ParticleIO.loadEmitter("thrustem.xml");
            thrust = (ConfigurableEmitter)particleSystem.getEmitter(0);
            
            
            //particleSystem.addEmitter(thrust);
          } catch(java.io.IOException error){
             System.out.println("Nie znaleziono pliku!");
          }
        //model.close();
        System.out.println("Player created");
        //model = new Rectangle(_position.x,_position.y,50,20);
    }
    @Override
    public Circle getModel(){
        return model;
    }
    public void Collide(LinkedList entities){
        Iterator iterator = entities.iterator();
        while(iterator.hasNext()){
            Enemy en = (Enemy)iterator.next();
            if (model.intersects(en.getModel()))
                en.Kill();
        }
    }
    public void Update(GameContainer gc, int delta){

        Input input = gc.getInput();
        _rotation = 0;

        //_velocity = new Vector2f(0,0);
        if(input.isKeyDown(Input.KEY_A) || input.isKeyDown(Input.KEY_LEFT))
        {
          _rotation -= delta*_turnSpeed;
        }
        if(input.isKeyDown(Input.KEY_D)  || input.isKeyDown(Input.KEY_RIGHT))
        {
          _rotation += delta*_turnSpeed;
        }
        //dodaj obrot do kąta kierunku
        _direction += _rotation;
        _direction = _direction % 360;
        if(input.isKeyDown(Input.KEY_W) || input.isKeyDown(Input.KEY_UP)){
            _velocity.x += delta*(((float)Math.cos(Math.toRadians((float)_direction-90)) * _maxSpeed) - _velocity.x) * _acceleration;
            _velocity.y += delta*(((float)Math.sin(Math.toRadians((float)_direction-90)) * _maxSpeed) - _velocity.y) * _acceleration;
            _position.add(_velocity);
            particleSystem.getEmitter(0).setEnabled(true);
        } else {
            _velocity.x *= delta*_deceleration;
            _velocity.y *= delta*_deceleration;
            _position.add(_velocity);
            particleSystem.getEmitter(0).setEnabled(false);
        }




        //_direction = _direction % 360;
        //System.out.println("velocity: " + _velocity + "direction = " + _direction);
        particleSystem.update(delta);
    }
    public void Draw(Graphics g){
        //narysuj wektor predkosci i inne opcje domyslne dla klasy Entity
        //super.DrawDebug(model,g);
        float alfa;
        alfa = (float)Math.toRadians((double)_rotation);
        thrust.angularOffset.setValue(_direction-180);
        //narysuj płomień z silnika
        
        particleSystem.render(model.getCenterX(), model.getCenterY());
        //koniec rysowania plomienia
        //model = (Circle)model.transform(Transform.createRotateTransform(alfa,0,30));
        model.setLocation(_position);
        g.setColor(Color.red);
        //g.draw(model);
        obrazek.rotate(_rotation);
        g.drawImage(obrazek, model.getCenterX()-42,model.getCenterY()-42);

    }
}
