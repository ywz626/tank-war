package cmd.tank1;

import javax.swing.*;

/**
 * @author 于汶泽
 */
//画板
public class TankGame extends JFrame {
    Draw draw = null;

    public TankGame() {
        draw = new Draw();
        add(draw);
        addKeyListener(draw);
        setSize(1300, 750);
        new Thread(draw).start();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        TankGame tankGame = new TankGame();
    }
}
