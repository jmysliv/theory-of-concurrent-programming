import java.util.LinkedList;
import java.util.Queue;

public class Scheduler implements Runnable{
    private Queue<ConsumeRequest> consumeRequests;
    private Queue<ProduceRequest> produceRequests;
    private ConsumeRequest firstConsumerRequest;
    private ProduceRequest firstProducerRequest;
    private ActivationQueue activationQueue;

    public Scheduler(ActivationQueue activationQueue){
        this.consumeRequests = new LinkedList<>();
        this.produceRequests = new LinkedList<>();
        this.activationQueue = activationQueue;
    }

    private void addProduceRequest(ProduceRequest request){
        if(firstProducerRequest != null){
            produceRequests.add(request);
        } else firstProducerRequest = request;
    }

    private void addConsumeRequest(ConsumeRequest request){
        if(firstConsumerRequest != null){
            consumeRequests.add(request);
        } else firstConsumerRequest = request;
    }

    private void schedule() throws InterruptedException {
        while(true){
            if(firstProducerRequest != null && firstProducerRequest.canBeCalled()){
                firstProducerRequest.call();
                if(!produceRequests.isEmpty()) firstProducerRequest = produceRequests.poll();
                else firstProducerRequest = null;
            } else if(firstConsumerRequest != null && firstConsumerRequest.canBeCalled()){
                firstConsumerRequest.call();
                if(!consumeRequests.isEmpty()) firstConsumerRequest = consumeRequests.poll();
                else firstConsumerRequest = null;
            } else{
                MethodRequest request = activationQueue.getMethodRequest();
                if( request instanceof  ProduceRequest) addProduceRequest((ProduceRequest) request);
                else addConsumeRequest( (ConsumeRequest) request);
            }
        }
    }

    @Override
    public void run() {
        try {
            this.schedule();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
