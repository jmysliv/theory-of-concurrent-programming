import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ActivationQueue {
    private BlockingQueue<MethodRequest> requests;

    public ActivationQueue() {
        this.requests = new LinkedBlockingQueue<>();
    }

    public void put(MethodRequest request) throws InterruptedException {
        requests.put(request);
    }

    public MethodRequest getMethodRequest() throws InterruptedException {
        return requests.take();
    }
}

