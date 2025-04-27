package cmd.tank1;

/**
 * @author 于汶泽
 */
public class Information {
    private static int dead = 0;

    public static int getDead() {
        return dead;
    }

    public static void setDead(int value) {
        dead = value;
    }

    public static void addDead() {
        dead++;
    }
}
