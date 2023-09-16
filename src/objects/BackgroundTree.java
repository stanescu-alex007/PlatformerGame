package objects;

import java.util.Random;

public class BackgroundTree {

    private int x, y, type, anumationIndex, animationTick;

    public BackgroundTree(int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;

        // Sets the aniIndex to a random value, to get some variations for the trees so
        // they all don't move in synch.
        Random r = new Random();
        anumationIndex = r.nextInt(4);

    }

    public void update() {
        animationTick++;
        if (animationTick >= 35) {
            animationTick = 0;
            anumationIndex++;
            if (anumationIndex >= 4)
                anumationIndex = 0;
        }
    }

    public int getAnimationIndex() {
        return anumationIndex;
    }

    public void setAnimationIndex(int aniIndex) {
        this.anumationIndex = aniIndex;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


}