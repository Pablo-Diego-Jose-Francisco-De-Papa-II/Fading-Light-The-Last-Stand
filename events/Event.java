package events;

/**
 * Rozhranie pre herné eventy, ktoré môžu nastať v hre.
 */
public interface Event {

    /**
     * Spustí event a aplikuje jej efekt.
     */
    void startEvent();

    /**
     * Skontroluje, či je event aktívny.
     *
     * @return true, ak je event aktívny, inak false
     */
    boolean isActive();

    /**
     * Ukončí event a deaktivuje jej efekt.
     */
    void endEvent();


    /**
     * Vráti názov eventu.
     *
     * @return názov eventu
     */
    String getName();

    /**
     * Aplikuje efekt eventu na hru.
     */
    void applyEffect();

}
