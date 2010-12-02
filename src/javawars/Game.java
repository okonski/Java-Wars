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
import org.newdawn.fizzy.Body;
import org.newdawn.fizzy.CollisionEvent;
import org.newdawn.fizzy.CompoundShape;
import org.newdawn.fizzy.Rectangle;
import org.newdawn.fizzy.Shape;
import org.newdawn.fizzy.Circle;
import org.newdawn.fizzy.World;
import org.newdawn.fizzy.WorldListener;

import java.util.Random;
public class Game extends BasicGame implements WorldListener
{
     static int height = 768;
     static int width =  1024;
     // liczba iteracji, więcej = dokładniej
     public static final int ITERATIONS = 30;
     public static final int UPDATES_PER_SECOND = 20;
     public World world;
     public Body ball,top,left,bottom,right;
     static boolean fullscreen = false;
     static boolean showFPS = true;
     static String title = "Java Wars";
     public LinkedList enemies = new LinkedList();
     public ExplosionManager explosionMan = new ExplosionManager(this);
     static int fpslimit = 60;
     public LinkedList removedEnemies;
     public LinkedList removedBullets;
     Player player;
     private int timeSinceStart;
     public Game(String title)
     {
          super(title);
          removedEnemies = new LinkedList();
          removedBullets = new LinkedList();
           
           
     }
     public void destroyEnemyWithBody(Body enemyBody){
        Iterator iterator = enemies.iterator();
        while(iterator.hasNext()){
            Enemy en = (Enemy)iterator.next();
            if (en.getBody().equals(enemyBody)){
                en.Kill();
                iterator.remove();
            }
        }
     }
     public void bulletCollidedWithEnemy(Body bullet,Body enemy){
                explosionMan.addExplosion(bullet.getX(), bullet.getY());
                world.remove(bullet);
                destroyEnemyWithBody(enemy);
                
     }
     public void bulletCollidedWithWall(Body bullet){
                explosionMan.addExplosion(bullet.getX(), bullet.getY());
                world.remove(bullet);
                System.out.println("Kolizja ze sciana");
     }
     @Override
     public void collided(CollisionEvent event) {
        Body bullet;
        if (event.getBodyA().getUserData() == "bullet"){
            if (event.getBodyB().getUserData() == "enemy"){
                bulletCollidedWithEnemy(event.getBodyA(),event.getBodyB());
            } else if (event.getBodyB().getUserData() == "wall"){
                bulletCollidedWithWall(event.getBodyA());
            }
        }
        if (event.getBodyB().getUserData() == "bullet"){
            if (event.getBodyA().getUserData() == "enemy"){
                bulletCollidedWithEnemy(event.getBodyB(),event.getBodyA());
            } else if (event.getBodyA().getUserData() == "wall"){
                bulletCollidedWithWall(event.getBodyB());
            }
        }
           
     }

     @Override
     public void separated(CollisionEvent event) {

     }

     public void init(GameContainer gc) throws SlickException
     {
        
        world = new World(0,0,4000,4000,0f,ITERATIONS);
        
        top = new Body(new Rectangle(4000,10), 0,0,true);
        bottom = new Body(new Rectangle(4000,10), 0,935,true);
        left = new Body(new Rectangle(10,1870), 0,0,true);
        right = new Body(new Rectangle(10,1870), 2010,0,true);
        top.setFriction(0);
        bottom.setFriction(0);
        right.setFriction(0);
        left.setFriction(0);
        top.setRestitution(0.1f);
        left.setRestitution(0.1f);
        right.setRestitution(0.1f);
        bottom.setRestitution(0.1f);
        top.setUserData("wall");
        bottom.setUserData("wall");
        left.setUserData("wall");
        right.setUserData("wall");
        world.add(top);
        world.add(bottom);
        world.add(left);
        world.add(right);
        player = new Player(150,150, this);
        int i;
        Random rand = new Random();
          for(i = 0; i < 15; i++)
            for(int j=0; j < 15; j++)
            enemies.add(new Enemy(50+rand.nextInt(((int)width+1)),rand.nextInt(((int)height+1)),this));

            //enemies.add(player);
        world.addListener(this);
     }
     public void processInput(GameContainer gc){
        Input input = gc.getInput();

         
     }

     public void update(GameContainer gc, int delta) throws SlickException
     {
         
        
        timeSinceStart += delta;

        world.update(1f/UPDATES_PER_SECOND);
        //przetworz wcisniete klawisze
        
       
        

        
        //area.getModel();
        //if (player.getModel().intersects(((Enemy)enemies.get(0)).getModel()))
        //    System.out.println("kolizja");
        Iterator iterator = enemies.iterator();
        while(iterator.hasNext()){
            Enemy en = (Enemy)iterator.next();
            en.Update(gc,delta);          
         }

        //System.out.println(delta);
        RemoveKilledEnemies();
        player.Update(gc,delta);
        player.Collide(enemies);
        processInput(gc);
        
        explosionMan.Update(gc, delta);
        
     }
     public void RemoveKilledEnemies(){
        Iterator iterator = removedEnemies.iterator();
        while(iterator.hasNext()){
            Enemy en = (Enemy)iterator.next();
            enemies.remove(en);
        }
        removedEnemies.clear();
        Iterator iteratorB = removedBullets.iterator();
        while(iteratorB.hasNext()){
            Bullet b = (Bullet)iteratorB.next();
            player.bullets.remove(b);
        }
        removedBullets.clear();
     }
     public void drawRectangle(Graphics g,Body b){
        g.pushTransform();
        g.translate(b.getX(), b.getY());
        g.translate(((Rectangle)b.getShape()).getXOffset(), ((Rectangle)b.getShape()).getYOffset());

        g.drawRect(b.getX(), b.getY(), ((Rectangle)b.getShape()).getWidth()/2,((Rectangle)b.getShape()).getHeight()/2);
        g.popTransform();
     }
     @Override
     public void render(GameContainer gc, Graphics g) throws SlickException
     {
         Input input = gc.getInput();
         g.setColor(Color.white);
        g.drawString("X: "+player.getX(), 20, 40);
        g.drawString("Y: "+player.getY(), 20, 55);
        g.drawString("Rot: "+player._direction, 20, 80);
        g.drawString("Time : " + timeSinceStart/1000, 20, 105);
        g.drawString("Explosions: "+explosionMan.getEmitterCount(), 20, 120);
        g.drawString("Bodies: "+world.getBodyCount(), 20, 135);
        g.drawString("Enemies: "+enemies.size(), 20, 150);
        g.drawString("Bullets: "+player.bullets.size(), 20, 165);
        //narysuj sciany
        
        //wysrodkuj kamerę na graczu
        //g.drawLine(width/2, height/2, width/2, height/2);
        float rad = ((Circle)player.getBody().getShape()).getRadius();
        
        g.translate(-player.getX()+width/2, -player.getY()+height/2);
        g.drawRect(0,0,2000,935);
        g.setColor(Color.yellow);
        drawRectangle(g,top);
        drawRectangle(g,left);
        drawRectangle(g,right);
        drawRectangle(g,bottom);
        //g.drawRect(top.getX(), top.getY(), ((Rectangle)top.getShape()).getWidth(),((Rectangle)top.getShape()).getHeight());
        //g.pushTransform();
        //g.translate(player.getX()-width/2, player.getY()-height/2);
        explosionMan.Draw(g);
        //g.popTransform();
        //g.drawOval(ball.getX(), ball.getY(), ((Circle)ball.getShape()).getRadius(),((Circle)ball.getShape()).getRadius());
        player.Draw(g);
        
        //narysuj przeciwnikow
        Iterator iterator = enemies.iterator();
        while(iterator.hasNext())
            ((Enemy)iterator.next()).Draw(g);
        //narysuj gracza
        
        g.setColor(Color.white);
        
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