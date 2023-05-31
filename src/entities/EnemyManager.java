package entities;

import gamestates.Playing;
import utilz.LoadSave;

import static utilz.Constants.EnemyConstants.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class EnemyManager {

    private Playing playing;
    private BufferedImage[][] crabbyArray;
    private ArrayList<Crabby> crabbies = new ArrayList<>();

    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImgs();
        addEnemies();

    }

    private void addEnemies() {
        crabbies = LoadSave.GetCrabs();

    }

    public void update(int[][] lvlData) {
        for (Crabby c : crabbies)
            c.update(lvlData);
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawCrabs(g, xLvlOffset);
    }

    private void drawCrabs(Graphics g, int xLvlOffset) {
        for (Crabby c : crabbies) {
            g.drawImage(crabbyArray[c.getEnemyState()][c.genAnimationIndex()],  (int) (c.getHitbox().x - CRABBY_DRAWOFFSET_X) - xLvlOffset, (int) (c.getHitbox().y - CRABBY_DRAWOFFSET_Y), CRABBY_WIDTH,
                    CRABBY_HEIGHT, null);
            //c.drawHitbox(g, xLvlOffset);
        }
    }

    private void loadEnemyImgs() {
        crabbyArray = new BufferedImage[5][9];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.CRABBY_SPRITE);
        for (int j = 0; j < crabbyArray.length; j++)
            for (int i = 0; i < crabbyArray[j].length; i++)
                crabbyArray[j][i] = temp.getSubimage(i * CRABBY_WIDTH_DEFAULT, j * CRABBY_HEIGHT_DEFAULT, CRABBY_WIDTH_DEFAULT, CRABBY_HEIGHT_DEFAULT);
    }

}
