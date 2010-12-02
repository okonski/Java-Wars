/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package javawars;
import org.newdawn.slick.GameContainer;
import java.util.Iterator;
import java.util.LinkedList;
import org.newdawn.slick.geom.*;
/**
 *
 * @author Piotrek
 */
abstract class Weapon {
    //po jakim czasie mozna znowu wystrzelic
    protected float reloadTime;
    //ile czasu uplynelo od ostatniego strzalu
    protected float lastShot;
    //lista wystrzelonych pociskow (niszczone po trafieniu w coś)
    protected LinkedList bullets;
    protected Game _game;
    Weapon(Game game){
        _game = game;
        reloadTime = 100; //czas w milisekundach
        bullets = new LinkedList();
    }
    abstract void Fire(Player player, Vector2f direction);
    abstract void Update(GameContainer gc, int delta);
    abstract void Collide(LinkedList entities);
}
