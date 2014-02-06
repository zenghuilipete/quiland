package creative.fire.pattern.callback4.observer;

public interface ExecuteObservable {
    void registerObserver(CallbackObserver observer);

    String getInfo();

    void notifyObservers();
}
