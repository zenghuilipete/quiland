package creative.fire.pattern.b.observer.dem;

public interface BabyListener extends java.util.EventListener {
    void onWakeUp(BabyEvent babyEvent);
    void onSleep(BabyEvent babyEvent);
}
