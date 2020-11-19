import java.util.LinkedList;
import java.util.List;

public class ProduceRequest implements MethodRequest {

    private Buffer buffer;
    private Future<List<Integer>> result;
    private int number;

    public ProduceRequest(Buffer buffer, Future<List<Integer>> result, int number) {
        this.buffer = buffer;
        this.result = result;
        this.number = number;
    }

    @Override
    public boolean canBeCalled() {
        return buffer.freeSpace() >= this.number;
    }

    @Override
    public void call() {
        List<Integer> produced = new LinkedList<>();
        for(int i=0; i<number; i++)
        {
            int rand = (int) (Math.random() * 10 + 1);
            buffer.produce(rand);
            produced.add(rand);
        }
        result.setData(produced);
    }
}