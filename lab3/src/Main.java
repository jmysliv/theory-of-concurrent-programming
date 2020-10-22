public class Main {
    public static void main(String args[]){
        int SIZE = 5;
        Runnable[] producers = new Runnable[SIZE];
        Runnable[] consumers = new Runnable[SIZE];
        Thread[] producersThreads = new Thread[SIZE];
        Thread[] consumersThreads = new Thread[SIZE];
        Monitor monitor = new Monitor();


        for(int i=0; i<SIZE; i++) {
            producers[i] = new Producer(monitor);
            consumers[i] = new Consumer(monitor);
            producersThreads[i] = new Thread(producers[i]);
            producersThreads[i].start();
            consumersThreads[i] = new Thread(consumers[i]);
            consumersThreads[i].start();
        }

        for(int i=0; i<SIZE; i++){
            try{
                producersThreads[i].join();
                consumersThreads[i].join();
            } catch(InterruptedException ex){ }

        }
    }

}
