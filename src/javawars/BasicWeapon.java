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
class BasicWeapon extends Weapon {
    private Image image;
    BasicWeapon(Game game, String img){
        super(game);
        reloadTime = 100;
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
            Bullet bullet = new Bullet(player,originX+direction.x*40,originY+direction.y*40,image);
            bullet.setVelocity(direction.x,direction.y);
            bullet.setPenetration(4);
            player.bullets.add(bullet);
            lastShot = 0;
        }
    }
}
