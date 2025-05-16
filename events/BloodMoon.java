package events;

public class BloodMoon implements Event {

    private boolean active = false;

    @Override
    public void startEvent() {
        this.active = true;
        applyEffect();
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
        return "Blood-moon";
    }

    @Override
    public void applyEffect() {
        //todo
    }
}
