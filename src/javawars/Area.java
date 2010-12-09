/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package javawars;
import java.util.Random;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.*;
import java.util.Iterator;
import java.util.LinkedList;
/**
 *
 * @author Piotrek
 */
public class Area {
    protected float _x0,_y0,_width,_height;
    private LinkedList stars = new LinkedList();
    public Area(float x0,float y0,float width, float height){
        _x0 = x0;
        _y0 = y0;
        _width = width;
        _height = height;
        //utworz sciany
        //wygeneruj losowe pozycje gwiazd
        Random rand = new Random();
        int x,y;
        for(int i=0; i < 200; i++){
            x = rand.nextInt(((int)_width+1));
            y = rand.nextInt(((int)_height+1));
            stars.add(new Vector2f(x,y));
        }
    }
    public void Draw(Graphics g){
        Iterator iterator = stars.iterator();
        g.setColor(new Color(255,255,255,0.8f));
        while(iterator.hasNext()){
            Vector2f star = (Vector2f)iterator.next();
            g.drawLine(star.x,star.y,star.x,star.y);
        }
        g.drawRect(0,0,_width,_height);
    }
}
