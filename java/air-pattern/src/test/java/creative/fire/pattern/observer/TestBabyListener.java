package creative.fire.pattern.observer;

import creative.fire.pattern.b.observer.dem.BabySource;
import creative.fire.pattern.b.observer.dem.BabyEvent;
import creative.fire.pattern.b.observer.dem.BabyListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class TestBabyListener {
    private Logger logger = LogManager.getLogger(TestBabyListener.class);
    private BabyListener babyObserver = new BabyListener() {
        @Override
        public void onWakeUp(BabyEvent babyEvent) {
            logger.info("{} wake up...", babyEvent.getSource().getName());
        }

        @Override
        public void onSleep(BabyEvent babyEvent) {
            logger.info("{} sleep...", babyEvent.getSource().getName());
        }
    };

    @Test
    public void testListener() {
        BabySource boy = new BabySource("Tom");
        boy.registerSleepListener(babyObserver);
        boy.registerWakeUpListener(babyObserver);

        boy.wakeUp();
        boy.sleep();
    }
}
