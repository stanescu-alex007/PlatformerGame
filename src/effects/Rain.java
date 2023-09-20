package effects;

import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Rain {

    private Point2D.Float[] drops;
    private Random random;
    private float rainSpeed = 1.25f;
    private BufferedImage rainParticle;

    public Rain() {

        random = new Random();
        drops = new Point2D.Float[1000];
        rainParticle = LoadSave.GetSpriteAtlas(LoadSave.RAIN_PARTICLE);
        initDrops();

    }

    private void initDrops() {
        for (int i = 0; i < drops.length; i++)
            drops[i] = getRandomPosition();
    }

    private Point2D.Float getRandomPosition() {
        return new Point2D.Float((int) getNewX(0), random.nextInt(Game.GAME_HEIGHT));
    }

    public void update(int xLvlOffset) {
        for (Point2D.Float p : drops) {
            p.y += rainSpeed;
            if (p.y >= Game.GAME_HEIGHT) {
                p.y = -20;
                p.x = getNewX(xLvlOffset);
            }
        }
    }

    private float getNewX(int xLvlOffset) {
        float value = (-Game.GAME_WIDTH) + random.nextInt((int)(Game.GAME_WIDTH * 3f)) + xLvlOffset;
        return value;
    }

    public void draw(Graphics g, int xLvlOffset) {
        for (Point2D.Float p : drops)
            g.drawImage(rainParticle, (int) p.getX() - xLvlOffset, (int) p.getY(), 3, 12, null);
    }
}
