package events;

public class Fog implements Event {

    private boolean active = false;

    @Override
    public void startEvent() {
        this.active = true;
        this.applyEffect();
    }

    @Override
    public boolean isActive() {
        return this.active;
    }

    @Override
    public void endEvent() {
        this.active = false;
    }

    @Override
    public String getName() {
        return "Fog";
    }

    @Override
    public void applyEffect() {
        //todo
    }
}
