public class Consumer implements Runnable{

    private final Monitor monitor;
    private final long timeLimit;
    private final int maxNumber;
    Consumer(Monitor monitor, long timeLimit, int maxNumber) {
        this.timeLimit = timeLimit;
        this.monitor = monitor;
        this.maxNumber = maxNumber;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        while(System.currentTimeMillis() - startTime < timeLimit){
            try {
                this.monitor.consume((int) (Math.random() * maxNumber + 1));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

