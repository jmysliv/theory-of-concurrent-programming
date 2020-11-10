public class Main {
    public static void main(String[] args) {
        int consumersNumber = 2;
        int producersNumber = 20;
        //choose one
//        Monitor monitor = new MonitorWithBoolean(10);
        Monitor monitor = new MonitorWithHasWaiters(10);
        Thread[] consumersThreads = new Thread[consumersNumber];
        Thread[] producersThreads = new Thread[producersNumber];
        Consumer[] consumers = new Consumer[consumersNumber];
        Producer[] producers = new Producer[producersNumber];

        for (int i = 0; i < consumersNumber; i++) {
            consumers[i] = new Consumer(monitor);
            consumersThreads[i] = new Thread(consumers[i]);
            consumersThreads[i].start();
        }
        for (int i = 0; i < producersNumber; i++) {
            producers[i] = new Producer(monitor);
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


