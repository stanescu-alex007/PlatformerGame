package objects;

import gamestates.Playing;
import utilz.LoadSave;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilz.Constants.ObjectConstants.*;

public class ObjectManager {

    private Playing playing;
    private BufferedImage[][] potionsImg, containerImgs;
    private ArrayList<Potion> potions;
    private ArrayList<GameContainer> containers;

    public ObjectManager(Playing playing) {
        this.playing = playing;
        loadImgs();

        potions = new ArrayList<>();
        potions.add(new Potion(300,300,RED_POTION));
        potions.add(new Potion(400,300,BLUE_POTION));

        containers = new ArrayList<>();
        containers.add(new GameContainer(500,300,BARREL));
        containers.add(new GameContainer(600,300,BOX));
    }

    private void loadImgs() {
        BufferedImage potionSprite = LoadSave.GetSpriteAtlas(LoadSave.POTION_ATLAS);
        potionsImg = new BufferedImage[2][7];

        for (int j = 0; j < potionsImg.length; j++)
            for (int i = 0; i < potionsImg[j].length; i++)
                potionsImg[j][i] = potionSprite.getSubimage(12 * i, 16 * j, 12, 16);

        BufferedImage containerSprite = LoadSave.GetSpriteAtlas(LoadSave.CONTAINER_ATLAS);
        containerImgs = new BufferedImage[2][8];

        for (int j = 0; j < containerImgs.length; j++)
            for (int i = 0; i < containerImgs[j].length; i++)
                containerImgs[j][i] = containerSprite.getSubimage(40 * i, 30 * j, 40, 30);
    }

    public void update() {
        for (Potion p : potions)
            if (p.active)
                p.update();

        for (GameContainer gc : containers)
            if (gc.active)
                gc.update();

    }

    public void draw(Graphics g, int xLvlOffset) {
        drawPotions(g, xLvlOffset);
        drawContainers(g, xLvlOffset);
    }

    private void drawContainers(Graphics g, int xLvlOffset) {
        for (GameContainer gc : containers) {
           if (gc.isActive()){
               int type = 0;
               if (gc.getObjType() == BARREL)
                   type = 1;
               g.drawImage(containerImgs[type][gc.animationIndex], (int) (gc.getHitbox().x - gc.getxDrawOffset() - xLvlOffset), (int) (gc.getHitbox().y - gc.getyDrawOffset()), CONTAINER_WIDTH, CONTAINER_HEIGHT, null);

           }
        }
    }

    private void drawPotions(Graphics g, int xLvlOffset) {
        for (Potion p : potions)
            if (p.active){
                int type = 0;
                if (p.getObjType() == RED_POTION)
                    type = 1;
                g.drawImage(potionsImg[type][p.animationIndex], (int) (p.getHitbox().x - p.getxDrawOffset() - xLvlOffset), (int) (p.getHitbox().y - p.getyDrawOffset()), POTION_WIDTH, POTION_HEIGHT, null);

            }

    }


}
