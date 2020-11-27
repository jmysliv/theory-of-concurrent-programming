import java.util.LinkedList;
import java.util.Queue;
public class Buffer {
    private final Queue<Integer> resources;
    private final  int maxSize;
    private int productionCounter = 0;
    private int consumptionCounter = 0;

    Buffer(int size) {
        this.maxSize = size;
        this.resources = new LinkedList<>();
    }

    public void produce(int number) {
        resources.add(number);
        productionCounter++;
        System.out.println("Producent wyprodukował: " + number);
        System.out.println("Rozmiar kolejki: " + resources.size());
    }

    public  int consume() {
        int number = resources.poll();
        consumptionCounter++;
        System.out.println("Konsument pobrał: " + number);
        System.out.println("Rozmiar kolejki: " + resources.size());
        return number;
    }

    public int getSize(){
        return this.resources.size();
    }

    public int freeSpace(){
        return maxSize - getSize();
    }

    public int getProductionCounter() {
        return productionCounter;
    }

    public int getConsumptionCounter() {
        return consumptionCounter;
    }
}
