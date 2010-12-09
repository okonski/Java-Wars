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
    static Vector2f rotate(Vector2f v, float degrees){

	float c = (float) Math.cos(Math.toRadians(degrees));
	float s = (float) Math.sin(Math.toRadians(degrees));
        float rx = v.x*c-v.y*s;
        float ry = v.x*s+v.y*c;
        v.x = rx;
	v.y = ry;

        return v;
    }
    abstract void Fire(Player player, Vector2f direction);
    public void Update(GameContainer gc, int delta){
        lastShot += delta;
    }
}
