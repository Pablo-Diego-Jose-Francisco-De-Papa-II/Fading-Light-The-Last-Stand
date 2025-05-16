package events;

public interface Event {

    void startEvent();

    boolean isActive();

    void endEvent();

    String getName();

    void applyEffect();

}
