import java.util.List;

public class Proxy {
    private ActivationQueue activationQueue;
    private Buffer buffer;

    public Proxy(ActivationQueue activationQueue, Buffer buffer) {
        this.activationQueue = activationQueue;
        this.buffer = buffer;
    }

    public Future<List<Integer>> produce(int number) throws InterruptedException{
        Future<List<Integer>> future = new Future<List<Integer>>();
        this.activationQueue.put(new ProduceRequest(buffer,future, number));
        return future;
    }

    public Future<List<Integer>> consume(int number) throws InterruptedException{
        Future<List<Integer>> future = new Future<List<Integer>>();
        this.activationQueue.put(new ConsumeRequest(buffer, future, number));
        return future;
    }
}
