import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor {

    private final Queue<Integer> full;
    private final Queue<Integer> empty;
    private final ReentrantLock lock;
    private final Condition producers;
    private final Condition consumers;
    private int size;


    Monitor(int size) {
        this.size = size;
        lock = new ReentrantLock();
        full = new LinkedList<>();
        empty = new LinkedList<>();
        producers = lock.newCondition();
        consumers = lock.newCondition();
        for(int i =0; i<size; i++){
            empty.add(i);
        }
    }

    public int produce_begin() throws InterruptedException{
        lock.lock();

        while(empty.isEmpty()) {
            producers.await();
        }

        int i = empty.poll();
        System.out.println(" w hashmapie jest: " + (this.size - empty.size() - full.size()));
        lock.unlock();
        return i;
    }

    public void produce_end(int i) throws InterruptedException{
        lock.lock();
        full.add(i);
        consumers.signal();
        lock.unlock();
    }

    public int consume_begin() throws InterruptedException{
        lock.lock();

        while(full.isEmpty()){
            consumers.await();
        }

        int i = full.poll();
        System.out.println(" w hashmapie jest: " + (this.size - empty.size() - full.size()));
        lock.unlock();
        return i;
    }

    public void consume_end(int i) throws InterruptedException{
        lock.lock();
        empty.add(i);
        producers.signal();
        lock.unlock();
    }
}