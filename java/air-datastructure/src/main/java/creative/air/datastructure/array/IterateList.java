package creative.air.datastructure.array;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hanl
 */
public class IterateList {
    
    static final Logger logger = Logger.getLogger(IterateList.class.getName());
    static ArrayList<Integer> list = new ArrayList<Integer>();
    static final CountDownLatch gateLatch1 = new CountDownLatch(1);
    static final CountDownLatch gateLatch2 = new CountDownLatch(1);
    
    public void addMock() {
        int top = 0;
        while (top < 20) {
            list.add(top++);
        }
    }
    
    public void iterating1() {
        try {
            //java.lang.IndexOutOfBoundsException:
            //for (int i = 0, len =list.size(); i < len; i++) {
            for (int i = 0; i < list.size(); i++) {
                if (i == 3) {
                    gateLatch1.countDown();
                }
                logger.log(Level.INFO, "i={0} list({1})='{'{2}'}'", new Object[]{i, i, list.get(i)});
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, null, e);
        }
    }
    
    public void iterating2() {
        try {
            int i = 0;
            //java.util.ConcurrentModificationException:
            for (int n : list) {
                if (i == 3) {
                    gateLatch2.countDown();
                }
                logger.log(Level.INFO, "i={0} list({1})='{'{2}'}'", new Object[]{i, i, n});
                i++;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, null, e);
        }
    }
    
    public static void main(String[] ss) {
        IterateList test = new IterateList();
        Thread remover1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    gateLatch1.await();
                } catch (InterruptedException ex) {
                    logger.log(Level.SEVERE, null, ex);
                }
                if (!list.isEmpty()) {
                    list.remove(0);
                }
                
                logger.log(Level.INFO, "list size:{0}", list.size());
            }
        });
        Thread remover2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    gateLatch2.await();
                } catch (InterruptedException ex) {
                    logger.log(Level.SEVERE, null, ex);
                }
                if (!list.isEmpty()) {
                    list.remove(0);
                }
                logger.log(Level.INFO, "list size:{0}", list.size());
            }
        });
        remover1.start();
        remover2.start();
        test.addMock();
        test.iterating1();
        test.iterating2();
    }
}