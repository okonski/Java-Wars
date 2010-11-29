/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package javawars;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.particles.*;
import java.util.Iterator;
import java.util.LinkedList;
import org.jbox2d.collision.*;
import java.util.Random;
public class Game extends BasicGame
{
     static int height = 768;
     static int width = 1024;

     static boolean fullscreen = false;
     static boolean showFPS = true;
     static String title = "Java Wars";
     private LinkedList enemies = new LinkedList();
     static int fpslimit = 60;
     
     Player player,player2,player3;
     Area area;
     private int time;
     public Game(String title)
     {
          super(title);

           
           
     }

     public void init(GameContainer gc) throws SlickException
     {
        player = new Player(300,300);

          area = new Area(5,5,2000,950);
          int i;
        Random rand = new Random();
          for(i = 0; i < 20; i++)
            for(int j=0; j < 10; j++)
            enemies.add(new Enemy(player,50+rand.nextInt(((int)width+1)),rand.nextInt(((int)height+1))));
     }
     public void processInput(GameContainer gc){
         
         
     }
     public void update(GameContainer gc, int delta) throws SlickException
     {
         
        //przetworz wcisniete klawisze
        time += delta;
        processInput(gc);
        area.Update(delta);
        player.Update(gc,delta);
        player.Collide(enemies);
        area.Collide(player);
        //area.getModel();
        //if (player.getModel().intersects(((Enemy)enemies.get(0)).getModel()))
        //    System.out.println("kolizja");
        Iterator iterator = enemies.iterator();
        while(iterator.hasNext()){
            Enemy en = (Enemy)iterator.next();
            en.Collide(enemies);
            en.Update(gc,delta);
            area.Collide(en);
            
         }
        //System.out.println(delta);
        RemoveKilledEnemies();
     }
     public void RemoveKilledEnemies(){
        Iterator iterator = enemies.iterator();
        while(iterator.hasNext()){
            Enemy en = (Enemy)iterator.next();
            if (en.killed)
                iterator.remove();
        }
     }
     public void render(GameContainer gc, Graphics g) throws SlickException
     {
         Input input = gc.getInput();
        g.drawString("X: "+player._position.x, 20, 40);
        g.drawString("Y: "+player._position.y, 20, 55);
        g.drawString("Rot: "+player._direction, 20, 80);
        g.drawString("Time : " + time/1000, 20, 105);
        //wysrodkuj Kamerę na graczu
        g.translate(-player._position.x+width/2, -player._position.y+height/2);
        area.Draw(g);
        //narysuj przeciwnikow
        Iterator iterator = enemies.iterator();
        while(iterator.hasNext())
            ((Enemy)iterator.next()).Draw(g);
        //narysuj gracza
        player.Draw(g);
        //g.setColor(Color.white);
        //g.drawLine(input.getMouseX(), input.getMouseY(), player.getModel().getCenterX(), player.getModel().getCenterY());

        
     }

     public static void main(String[] args) throws SlickException
     {

          
          AppGameContainer app = new AppGameContainer(new Game(title));
          app.setDisplayMode(width, height, fullscreen);
          app.setSmoothDeltas(true);
          app.setVSync(true);
          app.setTargetFrameRate(fpslimit);
          app.setShowFPS(showFPS);
          app.start();
     }
}