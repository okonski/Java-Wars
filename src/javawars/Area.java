/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package javawars;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.*;
/**
 *
 * @author Piotrek
 */
public class Area {
    protected float _x0,_y0,_width,_height;
    private Rectangle model;
    public Area(float x0,float y0,float width, float height){
        _x0 = x0;
        _y0 = y0;
        _width = width;
        _height = height;
        model = new Rectangle(_x0, _y0, _width, _height);
    }
    public Shape getModel(){
        return model;
    }
    public void Draw(Graphics g){
        g.setColor(Color.yellow);
        g.drawRect(_x0, _y0, _width, _height);
    }
}
