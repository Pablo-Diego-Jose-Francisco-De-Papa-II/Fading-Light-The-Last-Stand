package events;

/**
 * Trieda reprezentujúca dažďový event.
 */
public class Rain implements Event {

    private boolean active = false;

    /**
     * Spustí dážď a aplikuje jeho efekt.
     */
    @Override
    public void startEvent() {
        this.active = true;
        this.applyEffect();
    }

    /**
     * Zistí, či je dážď aktívny.
     *
     * @return true, ak prší, inak false
     */
    @Override
    public boolean isActive() {
        return this.active;
    }

    /**
     * Ukončí dažď.
     */
    @Override
    public void endEvent() {
        this.active = false;
    }

    /**
     * Vráti názov eventu - Rain.
     *
     * @return názov "Rain"
     */
    @Override
    public String getName() {
        return "Rain";
    }

    /**
     * Aplikuje efekt dažďa.
     */
    @Override
    public void applyEffect() {
        //todo
    }
}
