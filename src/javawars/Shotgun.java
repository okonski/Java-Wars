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
class Shotgun extends Weapon {
    private Image image;
    Shotgun(Game game, String img){
        super(game);
        reloadTime = 150;
        try{
            image = new Image(img);
        } catch(org.newdawn.slick.SlickException error){
            System.out.println("Tekstura pocisku nie znaleziona! "+img);
        }
    }

    public void Fire(Player player, Vector2f direction){
        if (lastShot >= reloadTime){
            float originX = player.getX();
            float originY = player.getY();
            Bullet bullet;
            //pierwszy pocisk
            direction = rotate(direction,6);
            bullet = new Bullet(player,originX+direction.x*20,originY+direction.y*20,image);
            bullet.setVelocity(direction.x,direction.y);
            bullet.setPenetration(3);
            bullet.setMaxLife(300);
            player.bullets.add(bullet);
            //srodkowy
            direction = rotate(direction,-6);
            bullet = new Bullet(player,originX+direction.x*20,originY+direction.y*20,image);
            bullet.setVelocity(direction.x,direction.y);
            bullet.setPenetration(3);
            bullet.setMaxLife(300);
            player.bullets.add(bullet);
            //drugi pocisk
            direction = rotate(direction,-6);
            bullet = new Bullet(player,originX+direction.x*20,originY+direction.y*20,image);
            bullet.setVelocity(direction.x,direction.y);
            bullet.setPenetration(3);
            bullet.setMaxLife(300);
            player.bullets.add(bullet);
            lastShot = 0;
        }
    }

}
