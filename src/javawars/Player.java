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
import org.newdawn.fizzy.Body;
import org.newdawn.fizzy.Circle;
/**
 *
 * @author Piotrek
 */
public class Player extends Entity {
    private Circle model;
    private Body body;
    private ParticleSystem thrustSystem,shootingSystem;
    public Game _game;
    private float tempAngle;
    private Weapon weapon;
    public LinkedList bullets;
    //kierunek w ktorym bedzie oddany strzal
    //wyliczane na podstawie roznicy wektorow pozycji gracza i myszki
    private Vector2f aim;
    private Image obrazek;
    ConfigurableEmitter thrust,cannon;
    public Player(float x, float y, Game game){
        _game = game;
        body = new Body(new Circle(38),x,y,false);
        weapon = new BasicWeapon(game,"resources/thrust.jpg");
        bullets = new LinkedList();
        body.setFriction(0.01f);
        body.setRestitution(0);
        game.world.add(body);
        aim = new Vector2f(0,0);
        //body.setPosition(x, y);
        _acceleration = 0.6f;
        _deceleration = 0.1f;
        _maxSpeed = 40f;
        try{
            obrazek = new Image("hawk.png");
        } catch(org.newdawn.slick.SlickException error){
            //nie rob nic, jak zawsze
        }
        //model = new Path(0,0);
        //model.lineTo(-20,60);
        //model.lineTo(20,60);
        //model.lineTo(0,0);
        model = new Circle(x,y,35.0f);
          try{
            thrustSystem = ParticleIO.loadConfiguredSystem("resources/thrust.xml");
            shootingSystem = ParticleIO.loadConfiguredSystem("resources/weapon.xml");
            //particleSystem = new ParticleSystem("spark.png", 10000);

            //thrust = ParticleIO.loadEmitter("thrustem.xml");
            thrust = (ConfigurableEmitter)thrustSystem.getEmitter(0);
            cannon = (ConfigurableEmitter)shootingSystem.getEmitter(0);
            
            //particleSystem.addEmitter(thrust);
          } catch(java.io.IOException error){
             System.out.println("Nie znaleziono pliku!");
          }
        //model.close();
        System.out.println("Player created");
        //model = new Rectangle(_position.x,_position.y,50,20);
    }

    public void Collide(LinkedList entities){
        Iterator iterator = entities.iterator();
        Iterator iteratorB = bullets.iterator();
        int i = 0;
        int j = 0;
        while(iteratorB.hasNext()){
            Bullet b = (Bullet)iteratorB.next();
            
            i++;
            //if (body.isTouching(en.getBody())){

            //System.out.println("Iteracja przez : " + j + "pociskow");
            }
        //System.out.println("Iteracja przez : " + i + "przeciwnikow");
        }
    @Override
    public float getX(){
        return body.getX();
    }
    @Override
    public float getY(){
        return body.getY();
    }
    public Body getBody(){
        return body;
    }
    public void Update(GameContainer gc, int delta){
        
        Input input = gc.getInput();
        _rotation = 0;

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
        if(input.isKeyDown(Input.KEY_W)){
            _velocity.x = ((float)Math.cos(Math.toRadians((float)_direction-90)));
            _velocity.y = ((float)Math.sin(Math.toRadians((float)_direction-90)));
            _velocity.normalise();
            _velocity.scale(_maxSpeed);
            _velocity.scale(delta);
            body.setVelocity(_velocity.x, _velocity.y);
            thrust.setEnabled(true);
        } else {
            //BAAAAAAAAAAARDZO zwolnij, nie mozna zatrzymac do zera, bo juz nie ruszy
            body.setVelocity(0.01f, 0.01f);
            thrust.setEnabled(false);
        }
        if(input.isKeyDown(Input.KEY_Z))
        {
            //if (explosionMan.getEmitterCount() < 2)
            _game.explosionMan.addExplosion(this.getX(),this.getY());

        }
        
        aim = new Vector2f(-(gc.getWidth()/2)+input.getMouseX(),-gc.getHeight()/2+input.getMouseY());
        aim.normalise();
        double alfa = aim.getTheta();
        cannon.angularOffset.setValue((float)alfa+90f);

        if (input.isMouseButtonDown(0) || input.isMouseButtonDown(1)){
           weapon.Fire(this,aim);


            shootingSystem.getEmitter(0).setEnabled(true);
            //shootingSystem.get
        } else{
           shootingSystem.getEmitter(0).setEnabled(false);
        }
        weapon.Update(gc, delta);
        //atualizuj pozycje pocisków
        Iterator iterator = bullets.iterator();
        while(iterator.hasNext()){
            Bullet b = (Bullet)iterator.next();
            b.Update(gc, delta);
        }
        //aktualizuj efekty cząsteczkowe
        thrustSystem.update(delta);
        shootingSystem.update(delta);
    }
    public void Draw(Graphics g){
        //narysuj wektor predkosci i inne opcje domyslne dla klasy Entity
        //super.DrawDebug(model,g);
        float alfa;
        alfa = (float)Math.toRadians((double)_rotation);
        thrust.angularOffset.setValue(_direction-180);
        //narysuj płomień z silnika
        float rad = ((Circle)body.getShape()).getRadius();

        //koniec rysowania plomienia
        //model = (Circle)model.transform(Transform.createRotateTransform(alfa,0,30));
        //model.setLocation(_position);
        g.setColor(Color.red);
        //g.draw(model);
        obrazek.rotate(_rotation);
        g.drawLine(body.getX(), body.getY(), body.getX(), body.getY());
        g.pushTransform();
        g.translate(-rad, -rad);
        thrustSystem.render(body.getX()+rad, body.getY()+rad);
        shootingSystem.render(body.getX()+rad, body.getY()+rad);
        g.drawImage(obrazek, body.getX(),body.getY());
        g.popTransform();
       //narysuj pociski
        Iterator iterator = bullets.iterator();
        while(iterator.hasNext()){
            Bullet b = (Bullet)iterator.next();
            b.Draw(g);
        }

    }
}
