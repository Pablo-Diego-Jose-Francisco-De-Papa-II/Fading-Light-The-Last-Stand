package events;

/**
 * Trieda reprezentujúca hmlový event.
 */
public class Fog implements Event {

    private boolean active = false;

    /**
     * Spustí hmlu a aplikuje jej efekt.
     */
    @Override
    public void startEvent() {
        this.active = true;
        this.applyEffect();
    }

    /**
     * Zistí, či je hmla aktívna.
     *
     * @return true, ak je hmla, inak false
     */
    @Override
    public boolean isActive() {
        return this.active;
    }

    /**
     * Ukončí hmlu.
     */
    @Override
    public void endEvent() {
        this.active = false;
    }

    /**
     * Vráti názov eventu - "Fog".
     *
     * @return názov "Fog"
     */
    @Override
    public String getName() {
        return "Fog";
    }

    /**
     * Aplikuje efekt hmly.
     */
    @Override
    public void applyEffect() {
        //todo
    }
}
