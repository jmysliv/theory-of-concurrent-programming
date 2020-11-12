import java.util.HashMap;

public class Consumer implements Runnable{

    private final Monitor monitor;
    private HashMap<Integer, Integer> map;
    Consumer(Monitor monitor,  HashMap<Integer, Integer> map) {
        this.monitor = monitor;
        this.map = map;
    }

    @Override
    public void run() {
        while (true){
            try {
                int i = this.monitor.consume_begin();
                int number = map.get(i);
                System.out.println("Konsument konsumuje " + number + " z pola " + i);
                Thread.sleep(1000);
                this.monitor.consume_end(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

