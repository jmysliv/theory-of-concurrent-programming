import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor {

    private final Queue<Integer> resources;
    private final  int maxSize;

    private final ReentrantLock lock;
    private final Condition firstConsumer;
    private final Condition restConsumers;
    private final Condition firstProducer;
    private final Condition restProducers;
    private int firstConsumerSize = 0;
    private int firstProducerSize = 0;
    private int restConsumersSize = 0;
    private int restProducersSize = 0;
    private boolean isFirstProducer = false;
    private boolean isFirstConsumer = false;


    Monitor(int size) {
        lock = new ReentrantLock();
        firstConsumer = lock.newCondition();
        restConsumers = lock.newCondition();
        firstProducer = lock.newCondition();
        restProducers = lock.newCondition();
        this.maxSize = size;
        this.resources = new LinkedList<>();
    }

    private void printStats(){
        System.out.println("Rozmiar FP: " + firstProducerSize);
        System.out.println("Rozmiar RP: " + restProducersSize);
        System.out.println("Rozmiar FC: " + firstConsumerSize);
        System.out.println("Rozmiar RC: " + restConsumersSize);
    }


    public void produce(int number) throws InterruptedException {
        lock.lock();
        printStats();

        while(lock.hasWaiters(firstProducer)) {
            System.out.println("Producent czeka na reszcie");
            restProducersSize++;
            restProducers.await();
            restProducersSize--;
        }

        while(maxSize - this.resources.size() < number){
            firstProducerSize ++;
            System.out.println("Producent pierwszy w kolejce");
            isFirstProducer = true;
            firstProducer.await();
            isFirstProducer = false;
            firstProducerSize --;
        }

        for(int i=0; i<number; i++){
            resources.add(number);
        }

        System.out.println("Producent wyprodukował: " + number);
        System.out.println("Rozmiar kolejki: " + resources.size());
        restProducers.signal();
        firstConsumer.signal();
        lock.unlock();
    }

    public  void consume(int number) throws InterruptedException {
        lock.lock();
        printStats();

        while(lock.hasWaiters(firstConsumer)){
            restConsumersSize++;
            System.out.println("Konsument czeka na reszcie");
            restConsumers.await();
            restConsumersSize --;
        }

        while(this.resources.size() < number) {
            firstConsumerSize ++;
            System.out.println("Konsument pierwszy w kolejce");
            isFirstConsumer = true;
            firstConsumer.await();
            isFirstConsumer = false;
            firstConsumerSize --;
        }
        for (int i = 0; i < number; i++) {
            resources.poll();
        }

        System.out.println("Konsument pobrał: " + number);
        System.out.println("Rozmiar kolejki: " + resources.size());
        restConsumers.signal();
        firstProducer.signal();
        lock.unlock();
    }
}