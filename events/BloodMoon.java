package events;

/**
 * Trieda reprezentujúca udalosť krvavého mesiaca.
 */
public class BloodMoon implements Event {

    private boolean active = false;

    /**
     * Spustí udalosť krvavého mesiaca a aplikuje jeho efekt.
     */
    @Override
    public void startEvent() {
        this.active = true;
        this.applyEffect();
    }

    /**
     * Zistí, či je krvavý mesiac aktívny.
     *
     * @return true, ak je udalosť aktívna, inak false
     */
    @Override
    public boolean isActive() {
        return this.active;
    }

    /**
     * Ukončí udalosť krvavého mesiaca.
     */
    @Override
    public void endEvent() {
        this.active = false;
    }

    /**
     * Vráti názov - "Blood-moon".
     *
     * @return názov "Blood-moon"
     */
    @Override
    public String getName() {
        return "Blood-moon";
    }

    /**
     * Aplikuje efekt krvavého mesiaca.
     */
    @Override
    public void applyEffect() {
        //todo
    }
}
