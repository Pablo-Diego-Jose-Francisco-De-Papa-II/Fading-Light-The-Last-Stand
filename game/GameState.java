package game;

/**
 * Trieda GameState reprezentuje globálny stav hry,
 * konkrétne počet dní a množstvo dostupného scrapu.
 *
 * Obsahuje statické premenné a metódy na manipuláciu s týmito hodnotami,
 * ktoré sú zdieľané počas celej hry.
 */
public class GameState {

    private static int dayCount = 1;
    private static int scrap = 0;

    /**
     * Vráti aktuálny počet dní od začiatku hry.
     *
     * @return počet dní
     */
    public static int getDayCount() {
        return dayCount;
    }

    /**
     * Posunie hru na nasledujúci deň.
     */
    public static void nextDay() {
        dayCount++;
    }

    /**
     * Vráti aktuálne množstvo scrapu.
     *
     * @return množstvo scrapu
     */
    public static int getScrap() {
        return scrap;
    }

    /**
     * Pridá určené množstvo scrapu k aktuálnemu stavu.
     *
     * @param amount počet jednotiek scrapu, ktoré sa majú pridať
     */
    public static void addScrap(int amount) {
        scrap += amount;
    }

    /**
     * Pokúsi sa odpočítať zadané množstvo scrapu.
     *
     * @param amount počet jednotiek scrapu, ktoré sa majú minúť
     * @return true, ak mal hráč dostatok scrapu a bol odpočítaný;
     *         false, ak nemal dostatok scrapu
     */
    public static boolean spendScrap(int amount) {
        if (scrap >= amount) {
            scrap -= amount;
            return true;
        }

        return false;
    }

    /**
     * Obnoví stav hry na východzie hodnoty.
     * Nastaví počet dní na 1 a množstvo scrapu na 0.
     */
    public static void reset() {
        dayCount = 1;
        scrap = 0;
    }

}

