import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Monitor {

    protected final Queue<Integer> resources;
    protected final  int maxSize;
    protected final ReentrantLock lock;
    protected final Condition firstConsumer;
    protected final Condition restConsumers;
    protected final Condition firstProducer;
    protected final Condition restProducers;
    protected int firstConsumerSize = 0;
    protected int firstProducerSize = 0;
    protected int restConsumersSize = 0;
    protected int restProducersSize = 0;
    protected int productionCounter = 0;
    protected int consumptionCounter = 0;


    Monitor(int size) {
        lock = new ReentrantLock();
        firstConsumer = lock.newCondition();
        restConsumers = lock.newCondition();
        firstProducer = lock.newCondition();
        restProducers = lock.newCondition();
        this.maxSize = size;
        this.resources = new LinkedList<>();
    }

    protected void printStats(){
        System.out.println("Rozmiar FP: " + firstProducerSize);
        System.out.println("Rozmiar RP: " + restProducersSize);
        System.out.println("Rozmiar FC: " + firstConsumerSize);
        System.out.println("Rozmiar RC: " + restConsumersSize);
    }


    public abstract void produce(int number) throws InterruptedException;

    public abstract void consume(int number) throws InterruptedException;

    public int getProductionCounter() {
        return productionCounter;
    }

    public int getConsumptionCounter() {
        return consumptionCounter;
    }
}