/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package javawars;

import org.newdawn.slick.*;

public class Game extends BasicGame
{
     static int height = 720;
     static int width = 1280;

     static boolean fullscreen = false;
     static boolean showFPS = true;
     static String title = "Java Wars";

     static int fpslimit = 60;

     Player player;
     Area area;
     public Game(String title)
     {
          super(title);
          player = new Player(300,300);
          area = new Area(5,5,width-10,height-10);
     }

     public void init(GameContainer gc) throws SlickException
     {

     }
     public void processInput(GameContainer gc){
         Input input = gc.getInput();
         
     }
     public void update(GameContainer gc, int delta) throws SlickException
     {
        //przetworz wcisniete klawisze
        
        processInput(gc);
        player.Update(gc,delta);
        area.getModel();
        //if (player.getModel().intersects(area.getModel()));
            System.out.println("koniec mapy");
     }

     public void render(GameContainer gc, Graphics g) throws SlickException
     {
        area.Draw(g);
        player.Draw(g);

       
     }

     public static void main(String[] args) throws SlickException
     {
          AppGameContainer app = new AppGameContainer(new Game(title));
          app.setDisplayMode(width, height, fullscreen);
          app.setSmoothDeltas(true);
          app.setTargetFrameRate(fpslimit);
          app.setShowFPS(showFPS);
          app.start();
     }
}