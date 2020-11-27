public class Producer implements Runnable {

    private final Monitor monitor;
    private final int maxNumber;
    private final long timeLimit;
    Producer(Monitor monitor, long timeLimit, int maxNumber) {
        this.monitor = monitor;
        this.timeLimit = timeLimit;
        this.maxNumber = maxNumber;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        while(System.currentTimeMillis() - startTime < timeLimit){
            try {
                this.monitor.produce((int) (Math.random() * maxNumber + 1));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
