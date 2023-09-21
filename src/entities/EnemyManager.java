package entities;

import gamestates.Playing;
import levels.Level;
import utilz.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilz.Constants.EnemyConstants.*;

public class EnemyManager {

    private Playing playing;
    private BufferedImage[][] crabbyArray, sharkArray;
    private ArrayList<Crabby> crabbies = new ArrayList<>();
    private ArrayList<Shark> sharks = new ArrayList<>();

    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImgs();

    }

    public void loadEnemies(Level level) {
        crabbies = level.getCrabs();
        sharks = level.getSharks();
        System.out.println("size of sharks: " + sharks.size());

    }

    public void update(int[][] lvlData) {
        boolean isAnyActive = false;
        for (Crabby c : crabbies){
            if (c.isActive()){
                c.update(lvlData, playing);
                isAnyActive = true;}
        }
        for (Shark s : sharks){
            if (s.isActive()){
                s.update(lvlData, playing);
                isAnyActive = true;}
        }
        if (!isAnyActive)
            playing.setLevelCompleted(true);
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawCrabs(g, xLvlOffset);
        drawSharks(g, xLvlOffset);

    }

    private void drawSharks(Graphics g, int xLvlOffset) {
        for (Shark s : sharks)

            if (s.isActive()) {
                g.drawImage(sharkArray[s.getEnemyState()][s.getAnimationIndex()],
                        (int) s.getHitbox().x - xLvlOffset - SHARK_DRAWOFFSET_X + s.flipX(),
                        (int) s.getHitbox().y - SHARK_DRAWOFFSET_Y + (int) s.getPushDrawOffset(),
                        SHARK_WIDTH * s.flipW(),
                        SHARK_HEIGHT, null);
               // s.drawHitbox(g, xLvlOffset);
               // s.drawAttackBox(g, xLvlOffset);
            }
    }

    private void drawCrabs(Graphics g, int xLvlOffset) {
        for (Crabby c : crabbies)
            if (c.isActive()) {
                g.drawImage(crabbyArray[c.getEnemyState()][c.getAnimationIndex()],
                        (int) c.getHitbox().x - xLvlOffset - CRABBY_DRAWOFFSET_X + c.flipX(),
                        (int) c.getHitbox().y - CRABBY_DRAWOFFSET_Y + (int) c.getPushDrawOffset(),
                        CRABBY_WIDTH * c.flipW(),
                        CRABBY_HEIGHT, null);
               // c.drawHitbox(g, xLvlOffset);
               // c.drawAttackBox(g, xLvlOffset);
            }
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        for (Crabby c : crabbies)
            if (c.getCurrentHealth() > 0)
            if (c.isActive())
                if (attackBox.intersects(c.getHitbox())) {
                    c.hurt(20);
                    return;
                }
        for (Shark s : sharks)
            if (s.getCurrentHealth() > 0)
            if (s.isActive())
                if (attackBox.intersects(s.getHitbox())) {
                    s.hurt(20);
                    return;
                }
    }

    private void loadEnemyImgs() {
        crabbyArray = new BufferedImage[5][9];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.CRABBY_SPRITE);
        for (int j = 0; j < crabbyArray.length; j++)
            for (int i = 0; i < crabbyArray[j].length; i++)
                crabbyArray[j][i] = temp.getSubimage(i * CRABBY_WIDTH_DEFAULT, j * CRABBY_HEIGHT_DEFAULT, CRABBY_WIDTH_DEFAULT, CRABBY_HEIGHT_DEFAULT);

        sharkArray = new BufferedImage[5][8];
        BufferedImage tempShark = LoadSave.GetSpriteAtlas(LoadSave.SHARK_ATLAS);
        for (int j = 0; j < sharkArray.length; j++)
            for (int i = 0; i < sharkArray[j].length; i++)
                sharkArray[j][i] = tempShark.getSubimage(i * SHARK_WIDTH_DEFAULT, j * SHARK_HEIGHT_DEFAULT, SHARK_WIDTH_DEFAULT, SHARK_HEIGHT_DEFAULT);
    }

    public void resetAllEnemies(){
        for (Crabby c : crabbies)
            c.resetEnemy();
        for (Shark s : sharks)
            s.resetEnemy();
    }


}
