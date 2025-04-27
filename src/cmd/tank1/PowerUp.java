package cmd.tank1;

/**
 * @author 于汶泽
 * 游戏道具类
 */
public class PowerUp {
    // 道具类型常量
    public static final int HEALTH = 0;    // 生命值
    public static final int SPEED = 1;     // 速度提升
    public static final int SHIELD = 2;    // 护盾
    public static final int FIREPOWER = 3; // 火力提升

    private int x;
    private int y;
    private int type;
    private boolean alive = true;
    private int lifeTime = 300; // 道具存在时间，30秒

    /**
     * 构造函数
     *
     * @param x    道具X坐标
     * @param y    道具Y坐标
     * @param type 道具类型
     */
    public PowerUp(int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    /**
     * 更新道具状态，每帧调用
     */
    public void update() {
        lifeTime--;
        if (lifeTime <= 0) {
            alive = false;
        }
    }

    // Getters and Setters
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

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime(int lifeTime) {
        this.lifeTime = lifeTime;
    }
} 