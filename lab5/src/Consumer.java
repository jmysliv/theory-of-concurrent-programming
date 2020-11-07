public class Consumer implements Runnable{

    private final Monitor monitor;
    Consumer(Monitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public void run() {
        while (true){
            try {
                this.monitor.consume((int) (Math.random() * 5 + 1));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

