public class Main {
    public static void main(String args[]){
        Runnable[] philosophers = new Runnable[5];
        Thread[] threads = new Thread[5];
        Semaphore[] forks = new Semaphore[5];

        for(int i=0; i<5; i++){
            forks[i] = new Semaphore(1);
        }

        for(int i=0; i<4; i++) {
            philosophers[i] = new Philosopher(i, forks[i], forks[i+1]);
            threads[i] = new Thread(philosophers[i]);
            threads[i].start();
        }
        philosophers[4] = new Philosopher(4, forks[0], forks[4]);
        threads[4] = new Thread(philosophers[4]);
        threads[4].start();


        for(int i=0; i<5; i++){
            try{
                threads[i].join();
            } catch(InterruptedException ex){ }

        }
    }
}
