/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package javawars;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;

/**
 *
 * @author Piotrek
 */
public class BlastWave extends Weapon {
    private Image image;
    BlastWave(Game game, String img){
        super(game);
        reloadTime = 1000;
        try{
            image = new Image(img);
        } catch(org.newdawn.slick.SlickException error){
            System.out.println("Tekstura pocisku nie znaleziona! "+img);
        }
    }

    public void Fire(Player player, Vector2f direction){
        if (lastShot >= reloadTime){
            Bullet bullet;
            float originX = player.getX();
            float originY = player.getY();
            //pierwszy pocisk
            for(int i=0; i < 90; i++){
                direction = rotate(direction,4);
                bullet = new Bullet(player,originX+direction.x*20,originY+direction.y*20,image);
                bullet.setMaxLife(1500);
                bullet.setPenetration(999);
                bullet.setSpeed(40);
                bullet.setVelocity(direction.x,direction.y);
                player.bullets.add(bullet);
            }
            lastShot = 0;
        }
    }
}
