package game;

public class GameState {
    private static int dayCount = 1;
    private static int scrap = 0;

    public static int getDayCount() {
        return dayCount;
    }

    public static void nextDay() {
        dayCount++;
    }

    public static int getScrap() {
        return scrap;
    }

    public static void addScrap(int amount) {
        scrap += amount;
    }

    public static boolean spendScrap(int amount) {
        if (scrap >= amount) {
            scrap -= amount;
            return true;
        }
        return false;
    }

    public static void reset() {
        dayCount = 1;
        scrap = 0;
    }

}

