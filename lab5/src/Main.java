public class Main {
    public static void main(String[] args) {
        int consumersNumber = 100;
        int producersNumber = 100;
        long timeLimit = 100000L;
        int bufferSize = 50;
        int maxNumber = (int) bufferSize/2;
        //choose one
//        Monitor monitor = new MonitorWithBoolean(bufferSize);
        Monitor monitor = new MonitorWithHasWaiters(10);
        Thread[] consumersThreads = new Thread[consumersNumber];
        Thread[] producersThreads = new Thread[producersNumber];
        Consumer[] consumers = new Consumer[consumersNumber];
        Producer[] producers = new Producer[producersNumber];

        for (int i = 0; i < consumersNumber; i++) {
            consumers[i] = new Consumer(monitor, timeLimit, maxNumber);
            consumersThreads[i] = new Thread(consumers[i]);
            consumersThreads[i].start();
        }
        for (int i = 0; i < producersNumber; i++) {
            producers[i] = new Producer(monitor, timeLimit, maxNumber);
            producersThreads[i] = new Thread(producers[i]);
            producersThreads[i].start();
        }
        long startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() - startTime < timeLimit){}
        System.out.println("Wyprodukowano: " + monitor.getProductionCounter());
        System.out.println("Skonsumowano: " + monitor.getConsumptionCounter());


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


