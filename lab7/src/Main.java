public class Main {
    public static void main(String[] args) {
        int consumersNumber = 2;
        int producersNumber = 2;
        int bufferSize = 10;
        Long worktime = 1000L;
        int maxNumber = 5;

        Buffer buffer = new Buffer(bufferSize);
        ActivationQueue activationQueue = new ActivationQueue();
        Proxy proxy = new Proxy(activationQueue, buffer);
        Scheduler scheduler = new Scheduler(activationQueue);
        Thread schedulerThread = new Thread(scheduler);
        schedulerThread.start();

        Thread[] consumersThreads = new Thread[consumersNumber];
        Thread[] producersThreads = new Thread[producersNumber];
        Consumer[] consumers = new Consumer[consumersNumber];
        Producer[] producers = new Producer[producersNumber];

        for (int i = 0; i < consumersNumber; i++) {
            consumers[i] = new Consumer(proxy, maxNumber, worktime);
            consumersThreads[i] = new Thread(consumers[i]);
            consumersThreads[i].start();
        }
        for (int i = 0; i < producersNumber; i++) {
            producers[i] = new Producer(proxy, maxNumber, worktime);
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

        try {
            schedulerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
