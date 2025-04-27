package cmd.tank1;

import java.awt.Color;

/**
 * @author 于汶泽
 */
public class Shot implements Runnable {
    // 添加子弹颜色属性
    private Color color = Color.RED; // 默认红色
    // 添加子弹大小属性
    private int width = 5;
    private int height = 5;
    private int x;
    private int y;
    private int speed;
    private int dir;
    private boolean alive = true;

    public Shot(int x, int y, int speed, int dir) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.dir = dir;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    @Override
    public void run() {
        //子弹运动
        while (true) {
            switch (dir) {
                case 0:
                    y -= speed;
                    break;
                case 1:
                    y += speed;
                    break;
                case 2:
                    x -= speed;
                    break;
                case 3:
                    x += speed;
                    break;
            }
            //System.out.println("x=" + x + ", y=" + y);
            if (!(x >= 0 && x <= 1000 && y >= 0 && y <= 750 && alive)) {
                //System.out.println("子弹销毁");

                alive = false;
                break;
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
