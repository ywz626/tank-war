package cmd.tank1;

import java.util.Vector;

/**
 * @author 于汶泽
 */
public class EnemyTank extends Tank implements Runnable {
    private boolean alive = true;
    private Shot shot = null;
    private Vector<EnemyTank> enemyTanks = new Vector<>();
    private int tankType = 0; // 0=普通坦克, 1=快速坦克, 2=装甲坦克
    private int armor = 0; // 装甲值（只有装甲坦克有）
    private int speed = 1; // 移动速度

    public EnemyTank(int x, int y, int dir) {
        super(x, y, dir);
    }

    public int getTankType() {
        return tankType;
    }

    public void setTankType(int tankType) {
        this.tankType = tankType;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }

    public Shot getShot() {
        return shot;
    }

    public void setShot(Shot shot) {
        this.shot = shot;
    }

    //实现敌人坦克自己动
    @Override
    public void run() {
        while (alive) {
            // 根据坦克类型调整行为
            int moveDistance = 1;
            if (tankType == 1) { // 快速坦克移动更快
                moveDistance = speed;
            }

            switch (getDir()) {
                case 0:
                    for (int i = 0; i < 30; i++) {
                        if (getY() <= 0) break;
                        if (!isCollision()) setY(getY() - moveDistance);
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 1:
                    for (int i = 0; i < 30; i++) {
                        if (getY() + 70 >= 750) break;
                        if (!isCollision()) setY(getY() + moveDistance);
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 2:
                    for (int i = 0; i < 30; i++) {
                        if (getX() <= 0) break;
                        if (!isCollision()) setX(getX() - moveDistance);
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 3:
                    for (int i = 0; i < 30; i++) {
                        if (getX() + 50 >= 1000) break;
                        if (!isCollision()) setX(getX() + moveDistance);
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
            }

            // 根据坦克类型决定转向策略
            if (tankType == 1) { // 快速坦克更频繁转向，更具攻击性
                setDir((int) (Math.random() * 4));
            } else if (tankType == 2) { // 装甲坦克更缓慢地转向，但更顽固
                if (Math.random() < 0.3) {
                    setDir((int) (Math.random() * 4));
                }
            } else { // 普通坦克正常转向
                setDir((int) (Math.random() * 4));
            }
        }
    }

    //封装判断敌人坦克是否重叠的方法
    public boolean isCollision() {
        //先取出判断本体坦克的方向
        switch (this.getDir()) {
            case 0://自己是向上的
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (enemyTank != this) {
                        switch (enemyTank.getDir()) {
                            case 0:
                            case 1://敌方坦克是上下的xy的范围是
                                if (this.getX() >= enemyTank.getX()
                                        && this.getX() <= enemyTank.getX() + 50
                                        && this.getY() >= enemyTank.getY()
                                        && this.getY() <= enemyTank.getY() + 70
                                        || this.getX() + 50 >= enemyTank.getX()
                                        && this.getX() + 50 <= enemyTank.getX() + 50
                                        && this.getY() >= enemyTank.getY()
                                        && this.getY() <= enemyTank.getY() + 70)
                                    return true;
                                break;
                            case 2:
                            case 3://敌人坦克是左右的
                                if (this.getX() >= enemyTank.getX()
                                        && this.getX() <= enemyTank.getX() + 70
                                        && this.getY() >= enemyTank.getY()
                                        && this.getY() <= enemyTank.getY() + 50
                                        || this.getX() + 50 >= enemyTank.getX()
                                        && this.getX() + 50 <= enemyTank.getX() + 70
                                        && this.getY() >= enemyTank.getY()
                                        && this.getY() <= enemyTank.getY() + 50)
                                    return true;
                                break;
                        }
                    }
                }
                break;
            case 1://自己是向下的
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (enemyTank != this) {
                        switch (enemyTank.getDir()) {
                            case 0:
                            case 1://敌方坦克是上下的xy的范围是
                                if (this.getX() >= enemyTank.getX()
                                        && this.getX() <= enemyTank.getX() + 50
                                        && this.getY() + 70 >= enemyTank.getY()
                                        && this.getY() + 70 <= enemyTank.getY() + 70
                                        || this.getX() + 50 >= enemyTank.getX()
                                        && this.getX() + 50 <= enemyTank.getX() + 50
                                        && this.getY() + 70 >= enemyTank.getY()
                                        && this.getY() + 70 <= enemyTank.getY() + 70)
                                    return true;
                                break;
                            case 2:
                            case 3://敌人坦克是左右的
                                if (this.getX() >= enemyTank.getX()
                                        && this.getX() <= enemyTank.getX() + 70
                                        && this.getY() + 70 >= enemyTank.getY()
                                        && this.getY() + 70 <= enemyTank.getY() + 50
                                        || this.getX() + 50 >= enemyTank.getX()
                                        && this.getX() + 50 <= enemyTank.getX() + 70
                                        && this.getY() + 70 >= enemyTank.getY()
                                        && this.getY() + 70 <= enemyTank.getY() + 50)
                                    return true;
                                break;
                        }
                    }
                }
                break;
            case 2://自己是向左的
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (enemyTank != this) {
                        switch (enemyTank.getDir()) {
                            case 0:
                            case 1://敌方坦克是上下的xy的范围是
                                if (this.getX() >= enemyTank.getX()
                                        && this.getX() <= enemyTank.getX() + 50
                                        && this.getY() >= enemyTank.getY()
                                        && this.getY() <= enemyTank.getY() + 70
                                        || this.getX() >= enemyTank.getX()
                                        && this.getX() <= enemyTank.getX() + 50
                                        && this.getY() + 50 >= enemyTank.getY()
                                        && this.getY() + 50 <= enemyTank.getY() + 70)
                                    return true;
                                break;
                            case 2:
                            case 3://敌人坦克是左右的
                                if (this.getX() >= enemyTank.getX()
                                        && this.getX() <= enemyTank.getX() + 70
                                        && this.getY() >= enemyTank.getY()
                                        && this.getY() <= enemyTank.getY() + 50
                                        || this.getX() >= enemyTank.getX()
                                        && this.getX() <= enemyTank.getX() + 70
                                        && this.getY() + 50 >= enemyTank.getY()
                                        && this.getY() + 50 <= enemyTank.getY() + 50)
                                    return true;
                                break;
                        }
                    }
                }
                break;
            case 3://自己是向右的
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (enemyTank != this) {
                        switch (enemyTank.getDir()) {
                            case 0:
                            case 1://敌方坦克是上下的xy的范围是
                                if (this.getX() + 70 >= enemyTank.getX()
                                        && this.getX() + 70 <= enemyTank.getX() + 50
                                        && this.getY() >= enemyTank.getY()
                                        && this.getY() <= enemyTank.getY() + 70
                                        || this.getX() + 70 >= enemyTank.getX()
                                        && this.getX() + 70 <= enemyTank.getX() + 50
                                        && this.getY() + 50 >= enemyTank.getY()
                                        && this.getY() + 50 <= enemyTank.getY() + 70)
                                    return true;
                                break;
                            case 2:
                            case 3://敌人坦克是左右的
                                if (this.getX() + 70 >= enemyTank.getX()
                                        && this.getX() + 70 <= enemyTank.getX() + 70
                                        && this.getY() >= enemyTank.getY()
                                        && this.getY() <= enemyTank.getY() + 50
                                        || this.getX() + 70 >= enemyTank.getX()
                                        && this.getX() + 70 <= enemyTank.getX() + 70
                                        && this.getY() + 50 >= enemyTank.getY()
                                        && this.getY() + 50 <= enemyTank.getY() + 50)
                                    return true;
                                break;
                        }
                    }
                }
                break;
        }
        return false;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    @Override
    public int getY() {
        return super.getY();
    }

    @Override
    public void setY(int y) {
        super.setY(y);
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
    public int getDir() {
        return super.getDir();
    }

    @Override
    public void setDir(int dir) {
        super.setDir(dir);
    }
}
