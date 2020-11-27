public class Main {
    public static void main(String[] args) {
        int consumersNumber = 100;
        int producersNumber = 100;
        int bufferSize = 50;
        int maxNumber = (int) bufferSize/2;
        long timeLimit = 1000L;

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
            consumers[i] = new Consumer(proxy, maxNumber, timeLimit);
            consumersThreads[i] = new Thread(consumers[i]);
            consumersThreads[i].start();
        }
        for (int i = 0; i < producersNumber; i++) {
            producers[i] = new Producer(proxy, maxNumber, timeLimit);
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

        System.out.println("Wyprodukowano: " + buffer.getProductionCounter());
        System.out.println("Skonsumowano: " + buffer.getConsumptionCounter());

        try {
            schedulerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
