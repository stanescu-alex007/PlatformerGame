package entities;

import static utilz.Constants.EnemyConstants.*;

public abstract class Enemy extends Entity {
    private int animationIndex, enemyState, enemyType;
    private int animationTick, animationSpeed = 25;

    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        initHitbox(x, y, width, height);
    }

    private void updateAnimationTick(){
        animationTick++;
        if (animationTick >= animationSpeed){
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= GetSpriteAmount(enemyType, enemyState)){
                animationIndex = 0;
            }

        }

    }

    public void update(){
        updateAnimationTick();
    }

    public int genAnimationIndex(){
        return animationIndex;
    }

    public int getEnemyState(){
        return enemyState;
    }

}
