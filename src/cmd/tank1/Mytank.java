package cmd.tank1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

/**
 * @author 于汶泽
 */
public class Mytank extends Tank {
    private Shot shot;
    private Vector<Shot> shots = new Vector<>();
    private boolean isLive = true;
    private int life = 3;//我的坦克有三点生命值
    private int speed = 20; // 默认速度
    private boolean shield = false; // 护盾状态
    private int shieldTime = 0; // 护盾持续时间
    private int firePower = 1; // 火力等级
    private int firePowerTime = 0; // 火力增强持续时间
    private int speedBoostTime = 0; // 速度提升持续时间

    public Mytank(int x, int y, int dir) {
        super(x, y, dir);
    }

    public void lifeDown() {
        life--;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public Vector<Shot> getShots() {
        return shots;
    }

    public Shot getShot() {
        return shot;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean hasShield() {
        return shield;
    }

    public void setShield(boolean shield) {
        this.shield = shield;
    }

    public int getShieldTime() {
        return shieldTime;
    }

    public void setShieldTime(int shieldTime) {
        this.shieldTime = shieldTime;
    }

    public int getFirePower() {
        return firePower;
    }

    public void setFirePower(int firePower) {
        this.firePower = firePower;
    }

    public int getFirePowerTime() {
        return firePowerTime;
    }

    public void setFirePowerTime(int firePowerTime) {
        this.firePowerTime = firePowerTime;
    }

    public int getSpeedBoostTime() {
        return speedBoostTime;
    }

    public void setSpeedBoostTime(int speedBoostTime) {
        this.speedBoostTime = speedBoostTime;
    }

    //发射子弹的方法
    public void shotBullet() {
        // 限制最大子弹数量为5
        if (shots.size() >= 5) {
            return;
        }

        // 根据火力等级发射不同数量和类型的子弹
        if (firePower == 1) {
            // 单发子弹
            Shot newShot = createShot();
            shots.add(newShot);
            new Thread(newShot).start();
        } else if (firePower == 2) {
            // 双发子弹 - 间隔发射
            Shot newShot1 = createShot();
            shots.add(newShot1);
            new Thread(newShot1).start();

            // 第二颗子弹稍有偏移
            Shot newShot2 = createShotWithOffset(10);
            shots.add(newShot2);
            new Thread(newShot2).start();
        } else {
            // 三发子弹 - 扇形发射
            Shot newShot1 = createShot();
            shots.add(newShot1);
            new Thread(newShot1).start();

            Shot newShot2 = createShotWithOffset(10);
            shots.add(newShot2);
            new Thread(newShot2).start();

            Shot newShot3 = createShotWithOffset(-10);
            shots.add(newShot3);
            new Thread(newShot3).start();
        }
    }

    // 创建普通子弹
    private Shot createShot() {
        Shot newShot;
        switch (getDir()) {
            case 0: // 上
                newShot = new Shot(getX() + 25, getY() - 10, 20, getDir());
                break;
            case 1: // 下
                newShot = new Shot(getX() + 25, getY() + 50, 20, getDir());
                break;
            case 2: // 左
                newShot = new Shot(getX() - 10, getY() + 25, 20, getDir());
                break;
            case 3: // 右
                newShot = new Shot(getX() + 80, getY() + 25, 20, getDir());
                break;
            default:
                newShot = new Shot(getX() + 25, getY() - 10, 20, 0);
        }

        // 火力增强时的子弹颜色和伤害
        if (firePower > 1) {
            newShot.setColor(java.awt.Color.RED);
            newShot.setWidth(7);
            newShot.setHeight(7);
        }

        return newShot;
    }

    // 创建有偏移的子弹
    private Shot createShotWithOffset(int offset) {
        Shot newShot;
        switch (getDir()) {
            case 0: // 上
                newShot = new Shot(getX() + 25 + offset, getY() - 10, 20, getDir());
                break;
            case 1: // 下
                newShot = new Shot(getX() + 25 + offset, getY() + 50, 20, getDir());
                break;
            case 2: // 左
                newShot = new Shot(getX() - 10, getY() + 25 + offset, 20, getDir());
                break;
            case 3: // 右
                newShot = new Shot(getX() + 80, getY() + 25 + offset, 20, getDir());
                break;
            default:
                newShot = new Shot(getX() + 25 + offset, getY() - 10, 20, 0);
        }

        // 火力增强时的子弹颜色和伤害
        if (firePower > 1) {
            newShot.setColor(java.awt.Color.RED);
            newShot.setWidth(7);
            newShot.setHeight(7);
        }

        return newShot;
    }

    @Override
    public int getX() {
        return super.getX();
    }

    @Override
    public void setX(int x) {
        super.setX(x);
    }

    @Override
    public int getY() {
        return super.getY();
    }

    @Override
    public void setY(int y) {
        super.setY(y);
    }
}
