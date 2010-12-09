/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package javawars;

import org.newdawn.slick.*;
import java.util.*;
import java.io.*;
import org.newdawn.fizzy.Body;
import org.newdawn.fizzy.Rectangle;
import org.newdawn.fizzy.World;

public class Game extends BasicGame
{
     static int height = 1080;
     static int width =  1920;
     static boolean fullscreen = false;
     static boolean showFPS = true;
     static boolean vsync = false;
     static boolean debug = false;
     static int fpslimit = 60;
     // liczba iteracji, więcej = dokładniej
     public static final int ITERATIONS = 15;
     public static final int UPDATES_PER_SECOND = 20;
     public World world;
     public Area gameArea;
     //sciany
     public Body ball,top,left,bottom,right;
     static String title = "Java Wars";     
     public ExplosionManager explosionMan;
     public GameplayManager gameplayMan;     
     public LinkedList removedEnemies;
     public LinkedList removedBullets;
     public LinkedList enemies = new LinkedList();
     Player player;
     private int timeSinceStart;
     public Game(String title)
     {
          super(title);
          removedEnemies = new LinkedList();
          removedBullets = new LinkedList();
          
           
     }
     static void readSettings(){
        Properties p = new Properties();
        try{
            p.load(new FileInputStream("settings.ini"));
        } catch(java.io.IOException error){
            System.out.println("Błąd odczytu pliku z konfiguracją!");
        }

        width = Integer.parseInt(p.getProperty("width", "1024"));
        height = Integer.parseInt(p.getProperty("height", "600"));
        fullscreen = Boolean.parseBoolean(p.getProperty("fullscreen","false"));
        vsync = Boolean.parseBoolean(p.getProperty("vsync","false"));
        showFPS = Boolean.parseBoolean(p.getProperty("showfps","false"));

     }
     @Override
     public void init(GameContainer gc) throws SlickException
     {

        world = new World(0,0,4000,4000,0f,ITERATIONS);
        gameArea = new Area(0,0,2000,1200);
        explosionMan = new ExplosionManager(this);
        gameplayMan = new GameplayManager(this);
        top = new Body(new Rectangle(4000,10), 0,0,true);
        bottom = new Body(new Rectangle(4000,10), 0,1200,true);
        left = new Body(new Rectangle(10,2400), 0,0,true);
        right = new Body(new Rectangle(10,2400), 2010,0,true);
        top.setFriction(0.1f);
        bottom.setFriction(0.1f);
        right.setFriction(0.1f);
        left.setFriction(0.1f);
        top.setRestitution(0.5f);
        left.setRestitution(0.5f);
        right.setRestitution(0.5f);
        bottom.setRestitution(0.5f);
        top.setUserData("wall");
        bottom.setUserData("wall");
        left.setUserData("wall");
        right.setUserData("wall");
        world.add(top);
        world.add(bottom);
        world.add(left);
        world.add(right);
        player = new Player(1000,600, this);


     }
     public void addEnemy(Enemy en){
         enemies.add(en);
     }

     public void processInput(GameContainer gc){
        Input input = gc.getInput();

         
     }
     @Override
     public void update(GameContainer gc, int delta) throws SlickException
     {
         
        if (!gc.hasFocus())
            return;
        timeSinceStart += delta;

        world.update(1f/UPDATES_PER_SECOND);
        //aktualizuj przeciwnikow
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

        gameplayMan.update(gc, timeSinceStart);
        explosionMan.Update(gc, delta);
        
     }
     public void addToRemoval(Enemy enemy){
         //sprawdz czy juz go nie usunelismy
         //zapobiega wysypaniu się silnika fizycznego
         //pzy probie usuniecia ciala po raz drugi
         if (removedEnemies.contains(enemy))
             return;
         else
             removedEnemies.add(enemy);
     }
     public void RemoveKilledEnemies(){
        Iterator iteratorB = removedBullets.iterator();
        while(iteratorB.hasNext()){
            Bullet b = (Bullet)iteratorB.next();
            player.bullets.remove(b);
        }
        removedBullets.clear();
        Iterator iterator = removedEnemies.iterator();
        while(iterator.hasNext()){
            Enemy en = (Enemy)iterator.next();
            
            world.remove(en.getBody());
            enemies.remove(en);
            
        }
        removedEnemies.clear();

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
        if (!gc.hasFocus())
            gc.pause();
        if (debug){
            g.setColor(Color.white);
            g.drawString("X: "+player.getX(), 20, 40);
            g.drawString("Y: "+player.getY(), 20, 55);
            g.drawString("Rot: "+player._direction, 20, 80);
            g.drawString("Time : " + timeSinceStart/1000, 20, 105);
            g.drawString("Explosions: "+explosionMan.getEmitterCount(), 20, 120);
            g.drawString("Bodies: "+world.getBodyCount(), 20, 135);
            g.drawString("Enemies: "+enemies.size(), 20, 150);
            g.drawString("Bullets: "+player.bullets.size(), 20, 165);
        }
        
        //wysrodkuj kamerę na graczu
        g.translate(-player.getX()+width/2, -player.getY()+height/2);
       
        
        //narysuj eksplozje
        explosionMan.Draw(g);
        //rysuj gracza
        player.Draw(g);
        //narysuj przeciwnikow
        Iterator iterator = enemies.iterator();
        while(iterator.hasNext())
            ((Enemy)iterator.next()).Draw(g);
        //narysuj strefę gry i gwiazdy w tle
        gameArea.Draw(g);
        
        
     }

     public static void main(String[] args) throws SlickException
     {
         //wczytaj ustawienia z pliku settings.ini
          readSettings();
          //
          AppGameContainer app = new AppGameContainer(new Game(title));
          app.setDisplayMode(width, height, fullscreen);
          app.setSmoothDeltas(true);
          app.setVSync(vsync);
          app.setTargetFrameRate(fpslimit);
          app.setShowFPS(showFPS);
          app.start();
     }
}