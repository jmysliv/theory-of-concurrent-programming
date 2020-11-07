public class Producer implements Runnable {

    private final Monitor monitor;
    Producer(Monitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.monitor.produce((int) (Math.random() * 5 + 1));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
