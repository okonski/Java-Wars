package javawars;

import org.newdawn.slick.particles.*;
import org.newdawn.slick.particles.ConfigurableEmitter.ColorRecord;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import java.util.Random;
public class ExplosionManager {
    private ParticleSystem particleSystem;
    private Game _game;
    private ConfigurableEmitter boom;
    public ExplosionManager(Game game){
        _game = game;
        try{
            particleSystem = ParticleIO.loadConfiguredSystem("resources/explosion.xml");
            boom = ((ConfigurableEmitter)particleSystem.getEmitter(0)).duplicate();
            particleSystem.removeAllEmitters();
            //usuwaj ukonczone efekty
            particleSystem.setRemoveCompletedEmitters(true);
        } catch(java.io.IOException error){
             System.out.println("Nie znaleziono pliku explosion.xml!");
        }
    }
    public int getEmitterCount(){
        return particleSystem.getEmitterCount();
    }
    public void addExplosion(float x, float y){
            if (particleSystem.getEmitterCount() > 50)
                return;
            ConfigurableEmitter new_boom = boom.duplicate();
            new_boom.setEnabled(true);
            new_boom.setPosition(x, y, false);
            Random rand = new Random();
            ((ColorRecord)new_boom.colors.get(1)).col = new Color(rand.nextFloat(),rand.nextFloat(),rand.nextFloat());
            ((ColorRecord)new_boom.colors.get(2)).col = new Color(rand.nextFloat(),rand.nextFloat(),rand.nextFloat());
            new_boom.initialLife.setMax(rand.nextFloat()*900+800);
            new_boom.emitCount.setMin(900);
            new_boom.emitCount.setMax(900+rand.nextInt(400));
            particleSystem.addEmitter(new_boom);
    }
    public void Update(GameContainer gc, int delta){
        particleSystem.update(delta);

    }
    public void Draw(Graphics g){
        particleSystem.render();
    }
    
}
