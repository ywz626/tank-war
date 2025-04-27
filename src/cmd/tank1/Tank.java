package cmd.tank1;

/**
 * @author 于汶泽
 */
public class Tank {
    private int x;
    private int y;
    private int dir;


    public Tank(int x, int y, int dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
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
}
