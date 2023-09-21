package entities;

import gamestates.Playing;
import main.Game;

import java.awt.geom.Rectangle2D;

import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.Directions.RIGHT;
import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.*;


public class Shark extends Enemy{

    private int attackBoxOffsetX;
    private int flipX = 0;
    private int flipW = 1;

    public Shark(float x, float y) {
        super(x, y, SHARK_WIDTH, SHARK_HEIGHT, SHARK);
        initHitbox(18,22);
        iniAttackBox();
    }

    private void iniAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int) (20 * Game.SCALE), (int) (20 * Game.SCALE));
        attackBoxOffsetX = (int) (Game.SCALE * 20);
    }

    public void update(int[][] lvlData, Playing playing) {
        updateBehavior(lvlData, playing);
        updateAnimationTick();
        updateAttackBoxFlip();

    }

    private void updateAttackBoxFlip() {
        if (walkDir == RIGHT)
            attackBox.x = hitbox.x + hitbox.width;
        else
            attackBox.x = hitbox.x - attackBoxOffsetX;

        attackBox.y = hitbox.y;

    }

    private void updateBehavior(int[][] lvlData, Playing playing) {
        if (firstUpdate)
            firstUpdateCheck(lvlData);
        if (inAir)
            inAirChecks(lvlData, playing);
        else {
            switch (state) {
                case IDLE:
                    newState(RUNNING);
                    break;
                case RUNNING:

                    if (canSeePlayer(lvlData, playing.getPlayer())) {
                        turnTowardsPlayer(playing.getPlayer());
                        if (isPlayerCloseForAttack(playing.getPlayer()))
                            newState(ATTACK);
                    }

                    move(lvlData);
                    break;
                case ATTACK:
                    if (animationIndex == 0)
                        attackChecked = false;
                    else if (animationIndex == 3){
                        if (!attackChecked)
                            checkEnemyHit(attackBox, playing.getPlayer());
                        attackMove(lvlData, playing.getPlayer());
                    }
                    break;
                case HIT:
                    if (animationIndex <= GetSpriteAmount(enemyType, state) - 2)
                        pushBack(pushBackDir, lvlData, 1f);
                    updatePushBackDrawOffset();
                    break;
            }
        }
    }

    protected void attackMove(int[][] lvlData, Player player) {
        float xSpeed = 0;

        if (walkDir == LEFT)
            xSpeed = -walkSpeed;
        else
            xSpeed = walkSpeed;

        if (CanMoveHere(hitbox.x + xSpeed * 4, hitbox.y, hitbox.width, hitbox.height, lvlData))
            if (IsFloor(hitbox, xSpeed * 4, lvlData)) {
                hitbox.x += xSpeed * 4;
                return;
            }
        newState(IDLE);
    }




}

