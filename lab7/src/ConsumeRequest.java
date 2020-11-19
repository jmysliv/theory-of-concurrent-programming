import java.util.LinkedList;
import java.util.List;

public class ConsumeRequest implements MethodRequest{

    private Buffer buffer;
    private Future<List<Integer>> result;
    private int number;

    public ConsumeRequest(Buffer buffer, Future<List<Integer>> result, int number) {
        this.buffer = buffer;
        this.result = result;
        this.number = number;
    }

    @Override
    public boolean canBeCalled() {
        return buffer.getSize() >= this.number;
    }

    @Override
    public void call() {
        List<Integer> consumed = new LinkedList<>();
        for(int i=0; i<number; i++)
        {
           consumed.add(buffer.consume());
        }
        result.setData(consumed);
    }
}
