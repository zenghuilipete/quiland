package creative.fire.pattern.b.observer.dem;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class BabySource {
    private Logger logger = LogManager.getLogger(BabySource.class);
    private String name;
    private Set<BabyListener> wakeUpListenerSet = new LinkedHashSet<>();
    private Set<BabyListener> sleepListenerSet = new LinkedHashSet<>();

    public BabySource(String name) {
        this.name = name;
    }

    public void registerWakeUpListener(BabyListener... wakeUpListeners) {
        for (int i = 0; i < wakeUpListeners.length; i++) {
            wakeUpListenerSet.add(wakeUpListeners[i]);
        }
    }

    public void registerSleepListener(BabyListener... sleepListeners) {
        for (int i = 0; i < sleepListeners.length; i++) {
            sleepListenerSet.add(sleepListeners[i]);
        }
    }

    private void wakeUpNotify() {
        Iterator<BabyListener> iterator = wakeUpListenerSet.iterator();
        while (iterator.hasNext()) {
            iterator.next().onWakeUp(new BabyEvent(this));
        }
    }

    private void sleepNotify() {
        Iterator<BabyListener> iterator = sleepListenerSet.iterator();
        while (iterator.hasNext()) {
            iterator.next().onSleep(new BabyEvent(this));
        }
    }

    public String getName() {
        return name;
    }

    public void wakeUp() {
        logger.info("Ma Mi--");
        wakeUpNotify();
    }

    public void sleep() {
        logger.info("Hoo hoo--");
        sleepNotify();
    }
}