import java.util.LinkedList;
import java.util.Queue;
public class Buffer {
    private final Queue<Integer> resources;
    private final  int maxSize;

    Buffer(int size) {
        this.maxSize = size;
        this.resources = new LinkedList<>();
    }

    public void produce(int number) {
        resources.add(number);
        System.out.println("Producent wyprodukował: " + number);
        System.out.println("Rozmiar kolejki: " + resources.size());
    }

    public  int consume() {
        int number = resources.poll();
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

}
