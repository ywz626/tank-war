package cmd.tank1;

/**
 * @author 于汶泽
 */
public class Bomb {
    private int x;
    private int y;
    private int life = 9;
    private boolean dead = true;

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void lifeDown() {
        if (life <= 0) dead = false;
        else --life;
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

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }
}
