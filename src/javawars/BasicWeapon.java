/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package javawars;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;
import org.newdawn.fizzy.Body;
import org.newdawn.fizzy.Circle;
import java.util.Iterator;
import java.util.LinkedList;
/**
 *
 * @author Piotrek
 */
class BasicWeapon extends Weapon {
    private Image image;
    BasicWeapon(Game game, String img){
        super(game);
        reloadTime = 80;
        try{
            image = new Image(img);
        } catch(org.newdawn.slick.SlickException error){
            System.out.println("Tekstura pocisku nie znaleziona! "+img);
        }
    }
    public void Fire(Player player, Vector2f direction){
        if (lastShot >= reloadTime){
            /*
            Body bullet = new Body(new Circle(3),player.getX()+direction.x*50,player.getY()+direction.y*50);

            bullet.setDensity(0.1f);
            //ustaw taga dla pocisku, zeby bylo wiadomo co usuwac
            bullet.setUserData("bullet");
            _game.world.add(bullet);
            bullet.setVelocity(player.getBody().getXVelocity()*direction.x*300000, player.getBody().getYVelocity()*direction.y*300000);
            bullet.setFriction(0);
            bullet.setRestitution(0);
            bullets.add(bullet);
            
             */
            Bullet bullet = new Bullet(player,player.getX()+direction.x*40,player.getY()+direction.y*40,image);
            bullet.setVelocity(direction.x*1.8f,direction.y*1.8f);
            player.bullets.add(bullet);
            lastShot = 0;
        }
    }
    public void Update(GameContainer gc, int delta){
        lastShot += delta;
    }
    public void Collide(LinkedList entities){
    }
}
