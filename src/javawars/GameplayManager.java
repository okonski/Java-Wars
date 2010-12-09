package javawars;
import java.util.ArrayList;
import java.util.Random;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Vector2f;

/**
 *
 * @author Piotrek
 */
public class GameplayManager {
    private ArrayList spawnPoints;
    private Game _game;
    private int lastSpawnedAt,interval,checkpointInterval,currentCheckpoint,_maxEnemies;
    private String[] textures;
    private Vector2f lastUsedSpawn;
    public GameplayManager(Game game){
        //tekstury dla różnych przeciwników
        textures = new String[8];
        lastUsedSpawn = new Vector2f(0,0);
        textures[0] = "pentagon";
        textures[1] = "circle";
        textures[2] = "circle2";
        textures[3] = "circle3";
        textures[4] = "circle4";
        textures[5] = "circle5";
        textures[6] = "rhombus";
        textures[7] = "star";
        _game = game;
        _maxEnemies = 300;
        lastSpawnedAt = 0;
        interval = 1000;
        checkpointInterval = 1000;
        currentCheckpoint = 0;
        spawnPoints = new ArrayList(4);
        //dodaj punkty w ktorych beda pojawiac sie przeciwnicy
        spawnPoints.add(new Vector2f(60,60));
        spawnPoints.add(new Vector2f(60,900));
        spawnPoints.add(new Vector2f(60,450));
        spawnPoints.add(new Vector2f(1950,450));
        spawnPoints.add(new Vector2f(1950,60));
        spawnPoints.add(new Vector2f(1950,900));
        spawnPoints.add(new Vector2f(950,60));
        spawnPoints.add(new Vector2f(950,900));
    }
    public void update(GameContainer gc, int elapsedTime){
        //aktualizuj czas gry i spawnuj nowych przeciwnkow zaleznie od czasu
       if (elapsedTime >= currentCheckpoint + checkpointInterval){

           interval -= 60;
           if (interval < 100)
               interval = 100;
           currentCheckpoint = elapsedTime;
       }
        if (elapsedTime >= lastSpawnedAt+interval){
            Random rand = new Random();
            Vector2f randomPoint;
            do{
                randomPoint = (Vector2f)spawnPoints.get(rand.nextInt(spawnPoints.size()));
            } while(randomPoint.equals(lastUsedSpawn) || randomPoint.distance(_game.player.getPosition()) < 400.0f);
            
            lastUsedSpawn = randomPoint;
            if (_game.enemies.size() < _maxEnemies){
                _game.addEnemy(new Enemy(randomPoint.x,randomPoint.y,_game,"resources/"+textures[rand.nextInt(textures.length)]+".png"));
            }
            lastSpawnedAt = elapsedTime;
        }
    }
}
