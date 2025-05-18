package events;

public class Rain implements Event {

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
        return "Rain";
    }

    @Override
    public void applyEffect() {
        //todo
    }
}
