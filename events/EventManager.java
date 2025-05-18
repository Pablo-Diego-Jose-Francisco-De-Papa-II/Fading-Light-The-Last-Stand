package events;

import java.util.ArrayList;
import java.util.List;

/**
 * Správca herných eventov, ktorý rozhoduje, ktoré eventy sa majú aktivovať v daný deň.
 */
public class EventManager {

    private List<Event> activeEvents = new ArrayList<>();

    /**
     * Skontroluje aktuálny deň a na základe pravidiel spustí príslušné eventy.
     *
     * @param day aktuálny deň v hre
     */
    public void checkAndStartEvent(int day) {
        this.activeEvents.clear();

        if (day % 10 == 0) {
            System.out.println("scrap+");
        }

        if (day % 7 == 0) {
            Event bloodMoon = new BloodMoon();
            bloodMoon.startEvent();
            this.activeEvents.add(bloodMoon);
        }

        if (day % 5 == 0) {
            Event rain = new Rain();
            rain.startEvent();
            this.activeEvents.add(rain);
        }

        if (day % 3 != 0) {
            Event fog = new Fog();
            fog.startEvent();
            this.activeEvents.add(fog);
        }
    }

    /**
     * Ukončí všetky momentálne aktívne eventy.
     */
    public void endCurrentEvents() {
        for (Event event : this.activeEvents) {
            if (event.isActive()) {
                event.endEvent();
            }
        }

        this.activeEvents.clear();
    }

    /**
     * Vráti zoznam názvov všetkých aktívnych eventov.
     *
     * @return zoznam názvov aktívnych eventov
     */
    public List<String> getActiveEventNames() {
        List<String> names = new ArrayList<>();
        for (Event event : this.activeEvents) {
            if (event.isActive()) {
                names.add(event.getName());
            }
        }
        return names;
    }

    /**
     * Zistí, či sú momentálne aktívne nejaké eventy.
     *
     * @return true, ak existujú aktívne eventy, inak false
     */
    public boolean hasActiveEvents() {
        return !this.activeEvents.isEmpty();
    }
}
