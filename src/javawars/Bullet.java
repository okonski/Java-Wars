/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package javawars;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.fizzy.Circle;
import java.util.Iterator;
import java.util.LinkedList;
/**
 *
 * @author Piotrek
 */
public class Bullet {
    private Vector2f _position, _velocity;
    private float _speed;
    private int _penetration, _penetrated,_maxLife,_life;
    private Image image;
    private Player _player;
    public boolean removed;
    public Bullet(Player player,float x, float y,Image img){
        _velocity = new Vector2f(0,0);
        _position = new Vector2f(x,y);
        _player = player;
        _speed = 40;
        _penetration = 1;
        _penetrated = 0;
        _maxLife = 2000; //czas zycia w milisekundach

        _life = 0;
        removed = false; //jesli bedzie True, to zostanie usuniety przez gracza
        image = img;
    }
    public void setVelocity(float x, float y){
        _velocity.x = x;
        _velocity.y = y;
    }
    public Vector2f getPosition(){
        return _position;
    }
    public void setMaxLife(int life){
        _maxLife = life;
    }
    public void setPenetration(int pen){
        _penetration = pen;
    }
    public void setSpeed(float speed){
        _speed = speed;
    }
    public void setPosition(float x, float y){
        _position.x = x;
        _position.y = y;
    }
    public void setX(float x){
        _position.x = x;
    }
    public void setY(float y){
        _position.y = y;
    }
    public float getX(){
        return _position.x;
    }
    public float getY(){
        return _position.x;
    }
    public void Remove(){
        removed = true;
        _player._game.removedBullets.add(this);
    }
    public void Update(GameContainer gc, int delta){
        _life += delta;
        _position.x += _velocity.x*_speed;//*delta;
        _position.y += _velocity.y*_speed;//*delta;
        if (_life >= _maxLife){
           _player._game.removedBullets.add(this);
        }
        //sprawdz kolizje z przeciwnikami
        Iterator iterator = _player._game.enemies.iterator();
        while(iterator.hasNext()){
                Enemy en = (Enemy)iterator.next();
                float radius = ((Circle)en.getBody().getShape()).getRadius();
                float radiusB = 20;
                //dodac kolizje ze scianami
                if (this.getPosition().distance(en.getPosition()) <= radius+radiusB){
                    _player._game.explosionMan.addExplosion(en.getX(),en.getY());
                    
                    if (_penetrated == _penetration)
                        _player._game.removedBullets.add(this);
                    else
                        _penetrated++;
                    en.Kill();
                }
        }

    }
    public void Draw(Graphics g){
        g.pushTransform();
        g.rotate(_position.x, _position.y, (float)_velocity.getTheta());
        g.drawImage(image, _position.x-10, _position.y-10);
        //g.drawOval(_position.x, _position.y, 10,10);
        g.popTransform();
    }
}
