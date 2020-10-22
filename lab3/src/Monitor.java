import java.util.LinkedList;

public class Monitor {
    private LinkedList<Integer> resources;

    public Monitor(){
        this.resources = new LinkedList<>();
    }

    public synchronized void produce(int number){
        while(resources.size() == 10){
            System.out.println("Producent czeka");
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        resources.push(number);
        System.out.println("Producent produkuje liczbe: " + number);
        notify();
    }

    public synchronized int consume(){
        while(resources.isEmpty()){
            System.out.println("Konsument czeka");
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int retval = resources.pollLast();
        System.out.println("Konsument pobrał liczbę: " + retval);
        notify();
        return retval;
    }
}
