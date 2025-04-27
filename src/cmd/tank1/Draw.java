package cmd.tank1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.Vector;

/**
 * @author 于汶泽
 */
public class Draw extends JPanel implements KeyListener, Runnable {
    Mytank mytank = null;
    //EnemyTank enemy = null;
    Vector<EnemyTank> enemy = new Vector<>();
    Vector<Bomb> bomb = new Vector<>();
    Vector<PowerUp> powerUps = new Vector<>();
    Image image1;
    Image image2;
    Image image3;
    Image healthImage;
    Image speedImage;
    Image shieldImage;
    private int enemySize = 6;
    private int score = 0;
    private Random random = new Random();
    private int level = 1;
    private int enemiesDefeated = 0;
    private boolean gameOver = false;
    private long gameStartTime = System.currentTimeMillis();
    private boolean paused = false;

    public Draw() {
        //构造器实现自己和敌人坦克的初始化，还有爆炸效果图片的初始化
        initGame();

        // 加载图片
//        image1 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("8f97caa622cb70f2bd50fc49041200bc.png"));
//        image2 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("714bbdb6d5d1ae185f1ce52d2cac8445.png"));
//        image3 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("cd45e03f1d13988726198464d45a1d07.png"));
//
        // 加载道具图片 - 实际项目中需要添加相应图片资源
        healthImage = image1; // 用爆炸图片临时替代
        speedImage = image2;  // 用爆炸图片临时替代
        shieldImage = image3; // 用爆炸图片临时替代
    }

    //显示基本信息的画笔
    public void inf(Graphics g) {
        g.setColor(Color.black);
        Font font = new Font("宋体", Font.BOLD, 25);
        g.setFont(font);
        g.drawString("您累计击毁坦克", 1020, 30);
        g.drawString("您剩余生命值", 1020, 180);
        g.drawString("得分: " + score, 1020, 230);
        g.drawString("关卡: " + level, 1020, 280);
        g.drawString("游戏时间: " + getGameTime(), 1020, 330);

        drawTank(g, 1020, 60, 1, 0);
        g.setColor(Color.black);
        g.drawString(Information.getDead() + "", 1080, 100);
        if (mytank != null) {
            g.drawString(mytank.getLife() + "", 1220, 180);

            // 显示坦克状态
            if (mytank.hasShield()) {
                g.setColor(Color.green);
                g.drawString("护盾保护中", 1020, 380);
            }

            if (mytank.getFirePower() > 1) {
                g.setColor(Color.red);
                g.drawString("火力增强: " + mytank.getFirePower(), 1020, 430);
            }

            if (mytank.getSpeed() > 20) {
                g.setColor(Color.blue);
                g.drawString("速度提升: " + mytank.getSpeed() / 20, 1020, 480);
            }
        }
    }

    private String getGameTime() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = (currentTime - gameStartTime) / 1000;
        long minutes = elapsedTime / 60;
        long seconds = elapsedTime % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    //键盘控制
    @Override
    public void keyPressed(KeyEvent e) {
        if (mytank != null) {
            if (e.getKeyCode() == KeyEvent.VK_A) {
                mytank.setDir(2);
                if (mytank.getX() >= 20) mytank.setX(mytank.getX() - mytank.getSpeed());
            }
            if (e.getKeyCode() == KeyEvent.VK_D) {
                mytank.setDir(3);
                if (mytank.getX() + 50 <= 980) mytank.setX(mytank.getX() + mytank.getSpeed());
            }
            if (e.getKeyCode() == KeyEvent.VK_W) {
                mytank.setDir(0);
                if (mytank.getY() >= 20) mytank.setY(mytank.getY() - mytank.getSpeed());
            }
            if (e.getKeyCode() == KeyEvent.VK_S) {
                mytank.setDir(1);
                if (mytank.getY() + 70 <= 730) mytank.setY(mytank.getY() + mytank.getSpeed());
            }
            if (e.getKeyCode() == KeyEvent.VK_J) {
                mytank.shotBullet();
            }
            // 使用空格键暂停游戏
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                togglePause();
            }
            // 使用R键重新开始游戏
            if (e.getKeyCode() == KeyEvent.VK_R && gameOver) {
                restartGame();
            }
        }
        this.repaint();
    }

    private void togglePause() {
        paused = !paused;
    }

    private void restartGame() {
        gameOver = false;
        score = 0;
        level = 1;
        enemiesDefeated = 0;
        Information.setDead(0);
        gameStartTime = System.currentTimeMillis();

        // 重新初始化坦克和敌人
        initGame();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    private void initGame() {
        mytank = new Mytank(100, 100, 0);
        enemy.clear();
        powerUps.clear();

        for (int i = 0; i < enemySize; i++) {
            createEnemyTank();
        }

        for (int i = 0; i < enemy.size(); i++) {
            EnemyTank enemyTank = enemy.get(i);
            enemyTank.setEnemyTanks(enemy);
        }
    }

    // 创建不同类型的敌方坦克
    private void createEnemyTank() {
        int x = 100 + random.nextInt(8) * 100;
        int y = 100 + random.nextInt(5) * 100;
        int dir = random.nextInt(4);

        // 根据当前关卡随机创建不同类型的敌方坦克
        EnemyTank enemyTank;
        int tankType = random.nextInt(3);

        if (tankType == 0 || level < 2) {
            // 普通坦克
            enemyTank = new EnemyTank(x, y, dir);
        } else if (tankType == 1) {
            // 快速坦克
            enemyTank = new EnemyTank(x, y, dir);
            enemyTank.setSpeed(level + 1); // 速度随关卡增加
            enemyTank.setTankType(1); // 设置为快速坦克类型
        } else {
            // 装甲坦克
            enemyTank = new EnemyTank(x, y, dir);
            enemyTank.setArmor(level); // 装甲值随关卡增加
            enemyTank.setTankType(2); // 设置为装甲坦克类型
        }

        new Thread(enemyTank).start();
        enemy.add(enemyTank);
    }

    //画笔方法
    public void paint(Graphics g) {
        super.paint(g);
        //画黑色幕布
        g.fillRect(0, 0, 1000, 750);
        inf(g);

        // 如果游戏结束，显示游戏结束信息
        if (gameOver) {
            showGameOver(g);
            return;
        }

        // 如果游戏暂停，显示暂停信息
        if (paused) {
            showPausedScreen(g);
            return;
        }

        //画自己的子弹
        if (mytank != null) {
            for (int i = 0; i < mytank.getShots().size(); i++) {
                Shot shot = mytank.getShots().get(i);
                if (shot != null && shot.isAlive()) {
                    //System.out.println("子弹出现");
                    g.setColor(shot.getColor());
                    g.fill3DRect(shot.getX(), shot.getY(), shot.getWidth(), shot.getHeight(), false);
                } else {
                    mytank.getShots().remove(shot);
                }
            }
        }

        //画己方坦克
        if (mytank != null) {
            drawTank(g, mytank.getX(), mytank.getY(), 0, mytank.getDir());
            // 如果有护盾，画护盾
            if (mytank.hasShield()) {
                g.setColor(new Color(0, 255, 255, 100));
                g.fillOval(mytank.getX() - 5, mytank.getY() - 5, 60, 80);
            }
        }

        //画敌方坦克
        for (int i = 0; i < enemy.size(); i++) {
            if (enemy.get(i).isAlive()) {
                EnemyTank tank = enemy.get(i);
                int identity = 1;

                // 根据坦克类型设置不同颜色
                if (tank.getTankType() == 1) { // 快速坦克
                    identity = 3; // 使用特殊标识
                } else if (tank.getTankType() == 2) { // 装甲坦克
                    identity = 4; // 使用特殊标识
                }

                drawTank(g, tank.getX(), tank.getY(), identity, tank.getDir());

                // 如果是装甲坦克，显示装甲值
                if (tank.getTankType() == 2 && tank.getArmor() > 0) {
                    g.setColor(Color.WHITE);
                    g.drawString("" + tank.getArmor(), tank.getX() + 20, tank.getY() - 5);
                }
            }
        }

        //画敌方子弹
        for (int i = 0; i < enemy.size(); i++) {
            if (enemy.get(i).getShot() != null && enemy.get(i).getShot().isAlive()) {
                Shot shot = enemy.get(i).getShot();
                g.setColor(Color.WHITE);
                g.fill3DRect(shot.getX(), shot.getY(), 5, 5, false);
            }
        }

        // 画道具
        for (int i = 0; i < powerUps.size(); i++) {
            PowerUp powerUp = powerUps.get(i);
            if (powerUp.isAlive()) {
                drawPowerUp(g, powerUp);
            } else {
                powerUps.remove(powerUp);
                i--;
            }
        }

        //休眠10毫秒,防止第一次爆炸消失
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //实现爆炸特效
        for (int i = 0; i < bomb.size(); i++) {
            Bomb bomb1 = bomb.get(i);
            if (bomb1.getLife() > 6) {
                g.drawImage(image1, bomb1.getX(), bomb1.getY(), 60, 60, this);
            } else if (bomb1.getLife() > 3) {
                g.drawImage(image2, bomb1.getX(), bomb1.getY(), 60, 60, this);
            } else {
                g.drawImage(image3, bomb1.getX(), bomb1.getY(), 60, 60, this);
            }
            bomb1.lifeDown();
            if (bomb1.getLife() <= 0) bomb.remove(bomb1);
        }
    }

    private void showGameOver(Graphics g) {
        g.setColor(Color.RED);
        Font font = new Font("宋体", Font.BOLD, 50);
        g.setFont(font);
        g.drawString("游戏结束!", 400, 300);
        g.setFont(new Font("宋体", Font.BOLD, 30));
        g.drawString("最终得分: " + score, 420, 350);
        g.drawString("击毁坦克数: " + Information.getDead(), 400, 400);
        g.drawString("坚持时间: " + getGameTime(), 410, 450);
        g.setColor(Color.WHITE);
        g.drawString("按R键重新开始游戏", 380, 520);
    }

    private void showPausedScreen(Graphics g) {
        // 绘制半透明背景
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, 1000, 750);

        g.setColor(Color.WHITE);
        Font font = new Font("宋体", Font.BOLD, 50);
        g.setFont(font);
        g.drawString("游戏暂停", 400, 300);
        g.setFont(new Font("宋体", Font.BOLD, 30));
        g.drawString("按空格键继续", 410, 350);
    }

    // 绘制道具
    private void drawPowerUp(Graphics g, PowerUp powerUp) {
        switch (powerUp.getType()) {
            case PowerUp.HEALTH:
                g.setColor(Color.RED);
                g.fillOval(powerUp.getX(), powerUp.getY(), 30, 30);
                g.setColor(Color.WHITE);
                g.drawString("+", powerUp.getX() + 10, powerUp.getY() + 20);
                break;
            case PowerUp.SPEED:
                g.setColor(Color.BLUE);
                g.fillOval(powerUp.getX(), powerUp.getY(), 30, 30);
                g.setColor(Color.WHITE);
                g.drawString("S", powerUp.getX() + 10, powerUp.getY() + 20);
                break;
            case PowerUp.SHIELD:
                g.setColor(Color.GREEN);
                g.fillOval(powerUp.getX(), powerUp.getY(), 30, 30);
                g.setColor(Color.WHITE);
                g.drawString("P", powerUp.getX() + 10, powerUp.getY() + 20);
                break;
            case PowerUp.FIREPOWER:
                g.setColor(Color.ORANGE);
                g.fillOval(powerUp.getX(), powerUp.getY(), 30, 30);
                g.setColor(Color.WHITE);
                g.drawString("F", powerUp.getX() + 10, powerUp.getY() + 20);
                break;
        }
    }

    //封装画坦克的方法
    public void drawTank(Graphics g, int x, int y, int identity, int face) {
        switch (identity) {//用颜色区分敌我
            case 0: // 我方坦克
                g.setColor(Color.CYAN);
                break;
            case 1: // 普通敌方坦克
                g.setColor(Color.YELLOW);
                break;
            case 3: // 快速敌方坦克
                g.setColor(Color.RED);
                break;
            case 4: // 装甲敌方坦克
                g.setColor(Color.DARK_GRAY);
                break;
            default:
                System.out.println("还未作处理");
        }
        switch (face) {//0:向上；1：向下；2：向左；3：向右
            case 0:
                g.fill3DRect(x, y, 10, 70, false);
                g.fill3DRect(x + 40, y, 10, 70, false);
                g.fill3DRect(x + 10, y + 10, 30, 50, false);
                g.fillOval(x + 10, y + 20, 30, 30);
                g.drawLine(x + 25, y + 20, x + 25, y - 10);
                break;
            case 1:
                g.fill3DRect(x, y, 10, 70, false);
                g.fill3DRect(x + 40, y, 10, 70, false);
                g.fill3DRect(x + 10, y + 10, 30, 50, false);
                g.fillOval(x + 10, y + 20, 30, 30);
                g.drawLine(x + 25, y + 80, x + 25, y + 20);
                break;
            case 2:// 朝向左
                g.fill3DRect(x, y, 70, 10, false);//左轮胎
                g.fill3DRect(x, y + 40, 70, 10, false);//右轮胎
                g.fill3DRect(x + 10, y + 10, 50, 30, false);
                g.fillOval(x + 20, y + 10, 30, 30);
                g.drawLine(x + 20, y + 25, x - 10, y + 25);
                break;
            case 3:
                g.fill3DRect(x, y, 70, 10, false);//左轮胎
                g.fill3DRect(x, y + 40, 70, 10, false);//右轮胎
                g.fill3DRect(x + 10, y + 10, 50, 30, false);
                g.fillOval(x + 20, y + 10, 30, 30);
                g.drawLine(x + 20, y + 25, x + 80, y + 25);
        }
    }

    //封装子弹碰到敌人坦克的方法
    public void hit(Shot shot, Tank T) {
        switch (T.getDir()) {
            case 0:
            case 1:
                if (shot.getX() >= T.getX() && shot.getY() >= T.getY() && shot.getX() <= T.getX() + 50 &&
                        shot.getY() <= T.getY() + 70) {
                    shot.setAlive(false);
                    if (T.getClass() == EnemyTank.class) {
                        EnemyTank enemyTank = (EnemyTank) T;

                        // 如果是装甲坦克，减少装甲值而不是直接摧毁
                        if (enemyTank.getTankType() == 2 && enemyTank.getArmor() > 0) {
                            enemyTank.setArmor(enemyTank.getArmor() - 1);
                            if (enemyTank.getArmor() <= 0) {
                                destroyEnemyTank(enemyTank);
                            }
                        } else {
                            destroyEnemyTank(enemyTank);
                        }
                    } else if (T.getClass() == Mytank.class) {
                        Mytank mytank = (Mytank) T;
                        // 如果有护盾，不减少生命值
                        if (mytank.hasShield()) {
                            mytank.setShieldTime(mytank.getShieldTime() - 1);
                            if (mytank.getShieldTime() <= 0) {
                                mytank.setShield(false);
                            }
                            return;
                        }

                        mytank.lifeDown();
                        if (mytank.getLife() == 0) {
                            mytank.setLive(false);
                            this.mytank = null;
                            bomb.add(new Bomb(T.getX(), T.getY()));
                            gameOver = true;
                        }
                    }
                }
                break;
            case 2:
            case 3:
                if (shot.getX() >= T.getX() && shot.getY() >= T.getY() &&
                        shot.getX() <= T.getX() + 70 && shot.getY() <= T.getY() + 50) {
                    shot.setAlive(false);
                    if (T.getClass() == EnemyTank.class) {
                        EnemyTank enemyTank = (EnemyTank) T;

                        // 如果是装甲坦克，减少装甲值而不是直接摧毁
                        if (enemyTank.getTankType() == 2 && enemyTank.getArmor() > 0) {
                            enemyTank.setArmor(enemyTank.getArmor() - 1);
                            if (enemyTank.getArmor() <= 0) {
                                destroyEnemyTank(enemyTank);
                            }
                        } else {
                            destroyEnemyTank(enemyTank);
                        }
                    } else if (T.getClass() == Mytank.class) {
                        Mytank mytank = (Mytank) T;
                        // 如果有护盾，不减少生命值
                        if (mytank.hasShield()) {
                            mytank.setShieldTime(mytank.getShieldTime() - 1);
                            if (mytank.getShieldTime() <= 0) {
                                mytank.setShield(false);
                            }
                            return;
                        }

                        mytank.lifeDown();
                        if (mytank.getLife() == 0) {
                            mytank.setLive(false);
                            this.mytank = null;
                            bomb.add(new Bomb(T.getX(), T.getY()));
                            gameOver = true;
                        }
                    }
                }
                break;
        }
    }

    // 摧毁敌方坦克
    private void destroyEnemyTank(EnemyTank enemyTank) {
        enemyTank.setAlive(false);
        bomb.add(new Bomb(enemyTank.getX(), enemyTank.getY()));
        Information.setDead(Information.getDead() + 1);

        // 增加得分，根据坦克类型给不同分数
        int pointsEarned = 100;
        if (enemyTank.getTankType() == 1) { // 快速坦克
            pointsEarned = 200;
        } else if (enemyTank.getTankType() == 2) { // 装甲坦克
            pointsEarned = 300;
        }
        score += pointsEarned * level; // 分数与关卡挂钩

        // 击毁敌人数+1
        enemiesDefeated++;

        // 关卡进度
        if (enemiesDefeated >= 10 * level) {
            levelUp();
        }

        // 随机掉落道具(20%几率)
        if (random.nextInt(5) == 0) {
            int powerUpType = random.nextInt(4); // 4种道具
            powerUps.add(new PowerUp(enemyTank.getX(), enemyTank.getY(), powerUpType));
        }
    }

    // 升级关卡
    private void levelUp() {
        level++;
        enemiesDefeated = 0;

        // 增加敌人数量
        enemySize = Math.min(6 + level, 12); // 最多12个敌人

        // 为玩家恢复一点生命值
        if (mytank != null) {
            mytank.setLife(Math.min(mytank.getLife() + 1, 5));
        }

        // 清空敌人，创建新的敌人
        enemy.clear();
        for (int i = 0; i < enemySize; i++) {
            createEnemyTank();
        }

        for (int i = 0; i < enemy.size(); i++) {
            EnemyTank enemyTank = enemy.get(i);
            enemyTank.setEnemyTanks(enemy);
        }
    }

    // 检查玩家是否碰到道具
    private void checkPowerUpCollision() {
        if (mytank == null) return;

        for (int i = 0; i < powerUps.size(); i++) {
            PowerUp powerUp = powerUps.get(i);

            if (powerUp.isAlive() &&
                    powerUp.getX() >= mytank.getX() - 30 &&
                    powerUp.getX() <= mytank.getX() + 70 &&
                    powerUp.getY() >= mytank.getY() - 30 &&
                    powerUp.getY() <= mytank.getY() + 70) {

                // 玩家碰到道具，触发效果
                applyPowerUp(powerUp);
                powerUp.setAlive(false);
            }
        }
    }

    // 应用道具效果
    private void applyPowerUp(PowerUp powerUp) {
        switch (powerUp.getType()) {
            case PowerUp.HEALTH:
                mytank.setLife(Math.min(mytank.getLife() + 1, 5));
                break;
            case PowerUp.SPEED:
                mytank.setSpeed(Math.min(mytank.getSpeed() + 10, 50));
                mytank.setSpeedBoostTime(300); // 持续30秒
                break;
            case PowerUp.SHIELD:
                mytank.setShield(true);
                mytank.setShieldTime(100); // 持续10秒
                break;
            case PowerUp.FIREPOWER:
                mytank.setFirePower(Math.min(mytank.getFirePower() + 1, 3));
                mytank.setFirePowerTime(200); // 持续20秒
                break;
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            // 如果游戏暂停或结束，跳过游戏逻辑
            if (paused || gameOver) {
                this.repaint();
                continue;
            }

            // 检查玩家是否碰到道具
            checkPowerUpCollision();

            // 更新玩家状态
            if (mytank != null) {
                // 更新速度提升状态
                if (mytank.getSpeedBoostTime() > 0) {
                    mytank.setSpeedBoostTime(mytank.getSpeedBoostTime() - 1);
                    if (mytank.getSpeedBoostTime() <= 0) {
                        mytank.setSpeed(20); // 恢复默认速度
                    }
                }

                // 更新火力提升状态
                if (mytank.getFirePowerTime() > 0) {
                    mytank.setFirePowerTime(mytank.getFirePowerTime() - 1);
                    if (mytank.getFirePowerTime() <= 0) {
                        mytank.setFirePower(1); // 恢复默认火力
                    }
                }
            }

            //敌方坦克发射子弹
            for (int j = 0; j < enemy.size(); j++) {
                EnemyTank T = enemy.get(j);
                if (T.getShot() == null ||
                        !T.getShot().isAlive() && T.isAlive() && T != null) {
                    Shot shot = null;
                    switch (T.getDir()) {
                        case 0:
                            shot = new Shot(T.getX() + 25, T.getY() - 10, 20, T.getDir());
                            break;
                        case 1:
                            shot = new Shot(T.getX() + 25, T.getY() + 50, 20, T.getDir());
                            break;
                        case 2:
                            shot = new Shot(T.getX() - 10, T.getY() + 25, 20, T.getDir());
                            break;
                        case 3:
                            shot = new Shot(T.getX() + 80, T.getY() + 25, 20, T.getDir());
                            break;
                    }
                    T.setShot(shot);
                    new Thread(T.getShot()).start();
                }
            }

            // 如果敌人太少，添加新敌人
            if (enemy.size() < enemySize / 2) {
                createEnemyTank();
            }

            //判断敌方子弹是否击中己方
            for (int i = 0; i < enemy.size(); i++) {
                Shot shot = enemy.get(i).getShot();
                if (shot != null && shot.isAlive() && mytank != null) {
                    hit(shot, mytank);
                }
            }
            //判断己方子弹是否击中敌方
            if (mytank != null) {
                for (int i = 0; i < mytank.getShots().size(); i++) {
                    Shot shot = mytank.getShots().get(i);
                    if (shot != null && shot.isAlive()) {
                        for (int j = 0; j < enemy.size(); j++) {
                            EnemyTank T = enemy.get(j);
                            hit(shot, T);
                        }
                    }
                }
            }
            this.repaint();
        }
    }
}
