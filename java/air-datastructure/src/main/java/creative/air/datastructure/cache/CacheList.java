package creative.air.datastructure.cache;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CacheList<E> {

    private Lock lock = new ReentrantLock();
    private final int maxLength = 10;
    private ArrayList<E> list = new ArrayList<E>();

    public boolean remove(E o) {
        lock.lock();
        try {
            return list.remove(o);
        } finally {
            lock.unlock();
        }
    }

    public boolean contains(E o) {
        lock.lock();
        try {
            return list.contains(o);
        } finally {
            lock.unlock();
        }
    }

    public void store(E o) {
        lock.lock();
        try {
            if (list.size() == maxLength) {
                list.remove(maxLength - 1);
            }
            list.add(o);
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        // CacheList<Integer> list = new CacheList<Integer>();
        // for (int i = 0; i < 20; i++) {
        // list.store(i);
        // }
        //
        // boolean b1=list.remove(-1);
        // boolean b2=list.remove(0);
        // list.store(111);
        // boolean b3=list.remove(1);
    }
}