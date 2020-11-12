import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        int consumersNumber = 2;
        int producersNumber = 2;

        Monitor monitor = new Monitor(10);
        Thread[] consumersThreads = new Thread[consumersNumber];
        Thread[] producersThreads = new Thread[producersNumber];
        Consumer[] consumers = new Consumer[consumersNumber];
        Producer[] producers = new Producer[producersNumber];
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < consumersNumber; i++) {
            consumers[i] = new Consumer(monitor, map);
            consumersThreads[i] = new Thread(consumers[i]);
            consumersThreads[i].start();
        }
        for (int i = 0; i < producersNumber; i++) {
            producers[i] = new Producer(monitor, map);
            producersThreads[i] = new Thread(producers[i]);
            producersThreads[i].start();
        }

        for(int i=0; i<consumersNumber ; i++){
            try {
                consumersThreads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for(int i=0; i<producersNumber ; i++){
            try {
                producersThreads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}


