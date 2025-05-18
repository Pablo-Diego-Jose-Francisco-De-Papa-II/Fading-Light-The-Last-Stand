package events;

import java.util.ArrayList;
import java.util.List;

public class EventManager {

    private List<Event> activeEvents = new ArrayList<>();

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

    public void endCurrentEvents() {
        for (Event event : this.activeEvents) {
            if (event.isActive()) {
                event.endEvent();
            }
        }

        this.activeEvents.clear();
    }

    public List<String> getActiveEventNames() {
        List<String> names = new ArrayList<>();
        for (Event event : this.activeEvents) {
            if (event.isActive()) {
                names.add(event.getName());
            }
        }
        return names;
    }

    public boolean hasActiveEvents() {
        return !this.activeEvents.isEmpty();
    }

}
