package creative.fire.pattern.callback4.observer;

import java.util.ArrayList;
import java.util.List;

public class Execute implements ExecuteObservable {
    private final List<CallbackObserver> parts;
    private String info;

    public Execute() {
        this.parts = new ArrayList<CallbackObserver>();
    }

    public void service(int callTime) {
        for (int i = 0; i < callTime; i++) {
            info = "processing " + i;
            notifyObservers();
        }
    }

    @Override
    public void notifyObservers() {
        for (CallbackObserver part : parts) {
            part.update(this);
        }
    }

    @Override
    public void registerObserver(CallbackObserver observer) {
        this.parts.add(observer);
    }

    @Override
    public String getInfo() {
        return info;
    }
}
