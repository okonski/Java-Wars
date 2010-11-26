/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package javawars;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.*;
/**
 *
 * @author Piotrek
 */
public class Player extends Entity {
    private Path model;
    public Player(float x, float y){
        _position = new Vector2f(x,y);
        _acceleration = 0.01f;
        
        model = new Path(0,0);
        model.lineTo(-20,60);
        model.lineTo(20,60);
        model.lineTo(0,0);
        //model.close();
        System.out.println("Player created");
        //model = new Rectangle(_position.x,_position.y,50,20);
    }
    public void Update(GameContainer gc, int delta){
        
        Input input = gc.getInput();
        _rotation = 0;
        //_velocity = new Vector2f(0,0);
        if(input.isKeyDown(Input.KEY_A))
        {
            _rotation -= _turnSpeed * delta;
        }

        if(input.isKeyDown(Input.KEY_D))
        {
            //_position.add(new Vector2f(_speed*delta,0));
            _rotation += _turnSpeed * delta;
        }
        //dodaj obrot do kąta kierunku
        _direction += _rotation;
        _direction = (_direction) % 360;
        if(input.isKeyDown(Input.KEY_W))
        {
           
            //poprawka na wiatr (-90 jest dla poprawienia kierunku, ma poruszac sie w strone dziubka)
            //_velocity.x += _acceleration*delta*(float)Math.cos(Math.toRadians((float)_direction-90));
            //_velocity.y += _acceleration*delta*(float)Math.sin(Math.toRadians((float)_direction-90));
            _velocity.x += delta*(((float)Math.cos(Math.toRadians((float)_direction-90)) * _maxSpeed) - _velocity.x) * _acceleration;
            _velocity.y += delta*(((float)Math.sin(Math.toRadians((float)_direction-90)) * _maxSpeed) - _velocity.y) * _acceleration;
            _position.add(_velocity);
            
        } else {
            _velocity.x *= delta*_deceleration;
            _velocity.y *= delta*_deceleration;
            _position.add(_velocity);
        }

        
        
        


        //_direction = _direction % 360;
        //System.out.println("velocity: " + _velocity + "direction = " + _direction);
    }
    public void Draw(Graphics g){
        //narysuj wektor predkosci i inne opcje domyslne dla klasy Entity
        super.DrawDebug(model,g);
        
        //model = (Path)model.transform(transform);
        model = (Path)model.transform(Transform.createRotateTransform((float)Math.toRadians((double)_rotation),0,30));
        model.setLocation(_position);
        g.setColor(Color.red);
        g.draw(model);
    }
}
