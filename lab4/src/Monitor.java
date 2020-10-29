import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor {
    private LinkedList<Integer> resources;
    private int maxSize;
    private final Lock lock;
    private final Condition isEmpty;
    private final Condition isFull;

    public Monitor(int size){
        this.resources = new LinkedList<>();
        lock = new ReentrantLock();
        isEmpty = lock.newCondition();
        isFull = lock.newCondition();
        this.maxSize = size;
    }

    public void produce(int number) throws InterruptedException {
        lock.lock();

        while(resources.size() == maxSize)
        {
            System.out.println("Producent czeka");
            isFull.await();
        }
        resources.push(number);
        System.out.println("Producent produkuje liczbe: " + number);
        isEmpty.signal();
        lock.unlock();
    }

    public void produceMany(int number) throws InterruptedException {
        lock.lock();

        for(int i=0; i<number; i++){
            while(resources.size() == maxSize)
            {
                System.out.println("Producent czeka");
                isFull.await();
            }
            resources.push(number);
        }
        System.out.println("Producent wyprodukował: " + number);
        System.out.println("Rozmiar kolejki: " + resources.size());
        isEmpty.signal();
        lock.unlock();
    }

    public int consume() throws InterruptedException {
        lock.lock();
        while(resources.isEmpty()){
            System.out.println("Konsument czeka");
            isEmpty.await();
        }
        int retval = resources.pollLast();
        System.out.println("Konsument pobrał liczbę: " + retval);
        isFull.signal();
        lock.unlock();
        return retval;
    }

    public void consumeMany(int number) throws InterruptedException {
        lock.lock();

        for(int i=0; i<number; i++){
            while(resources.isEmpty()){
                System.out.println("Konsument czeka");
                isEmpty.await();
            }
            resources.pollLast();
        }
        System.out.println("Konsument pobrał: " + number);
        System.out.println("Rozmiar kolejki: " + resources.size());
        isFull.signal();
        lock.unlock();
    }
}