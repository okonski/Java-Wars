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
    private Rectangle top,bottom,left,right;
    public Area(float x0,float y0,float width, float height){
        _x0 = x0;
        _y0 = y0;
        _width = width;
        _height = height;
        //utworz sciany
        bottom = new Rectangle(_x0, _y0+_height, _width+20, 20);
        top = new Rectangle(_x0, _y0, _width+20, 20);
        left = new Rectangle(_x0, _y0, 20, _height+20);
        right = new Rectangle(_x0+_width, _y0, 20, _height+20);
        //wygeneruj losowe pozycje gwiazd
        Random rand = new Random();
        int x,y;
        for(int i=0; i < 200; i++){
            x = rand.nextInt(((int)_width+1));
            y = rand.nextInt(((int)_height+1));
            stars.add(new Vector2f(x,y));
        }
    }
    public void Collide(Entity entity){
        Circle tempModel = (Circle)entity.getModel();
        if (entity._velocity.y <=0 && tempModel.intersects(top))
            entity._position.y = top.getMaxY();
        else if(entity._velocity.y >= 0 && tempModel.intersects(bottom))
            entity._position.y = bottom.getY()+0.5f-2*tempModel.radius;
        if(entity._velocity.x <= 0 && tempModel.intersects(left))
            entity._position.x = left.getMaxX()+0.5f;
        if (entity._velocity.x >=0 && tempModel.intersects(right))
            entity._position.x = right.getX()+0.5f-2*tempModel.radius;
        
    }
    public void Update(int delta){

    }
    public void Draw(Graphics g){
        Iterator iterator = stars.iterator();
        g.setColor(new Color(255,255,255,0.5f));
        while(iterator.hasNext()){
            Vector2f star = (Vector2f)iterator.next();
            g.drawLine(star.x,star.y,star.x,star.y);
        }
            
        g.setColor(Color.yellow);
        g.drawRect(left.getX(), left.getY(), left.getWidth(), left.getHeight());
        g.drawRect(right.getX(), right.getY(), right.getWidth(), right.getHeight());
        g.drawRect(top.getX(), top.getY(), top.getWidth(), top.getHeight());
        g.drawRect(bottom.getX(), bottom.getY(), bottom.getWidth(), bottom.getHeight());
        g.setColor(new Color(0,0,255,0.04f));
        
        g.fillRect(_x0, _y0, _width, _height);
    }
}
