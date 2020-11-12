import java.util.HashMap;

public class Producer implements Runnable {

    private final Monitor monitor;
    private HashMap<Integer, Integer> map;
    Producer(Monitor monitor,  HashMap<Integer, Integer> map) {
        this.monitor = monitor;
        this.map = map;
    }

    @Override
    public void run() {
        while (true) {
            try {
                int i = this.monitor.produce_begin();
                int number = (int) (Math.random() * 10 + 1);
                System.out.println("Producer produkuje " + number + " w polu " + i);
                map.put(i, number);
                Thread.sleep(1000);
                this.monitor.produce_end(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
