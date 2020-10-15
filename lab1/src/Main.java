public class Main {

    public static void runThreads(int number) {
        Runnable[] runners = new Runnable[number];
        Thread[] threads = new Thread[number];
        IntOperation intOperation = new IntOperation();
        Semaphore semaphore = new Semaphore();

        for(int i=0; i<number; i++) {
            runners[i] = new MyRunnable(i, intOperation, semaphore);
            threads[i] = new Thread(runners[i]);
            threads[i].start();
        }

        for(int i=0; i<number; i++){
            try{
                threads[i].join();
            } catch(InterruptedException ex){ }

        }
        intOperation.print();
    }

    public static void main(String args[]) {
       runThreads(10000);
    }
}