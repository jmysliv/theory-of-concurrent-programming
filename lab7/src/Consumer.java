import java.util.List;

public class Consumer implements Runnable{

    private Proxy proxy;
    private int maxNumber;
    private long timeLimit;

    public Consumer(Proxy proxy, int maxNumber, long timeLimit){
        this.proxy = proxy;
        this.maxNumber = maxNumber;
        this.timeLimit = timeLimit;
    }

    private void printResult( List<Integer> result, long time){
        System.out.println("Consumed: " + result.stream()
                .map(Object::toString)
                .reduce("", (acc, number) -> acc + " " + number) + " w czasie: " + time + "ms");
    }


    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        while(System.currentTimeMillis() - startTime < timeLimit){
            try {
                Future<List<Integer>> future = proxy.consume((int) (Math.random() * maxNumber + 1));
                System.out.println("Konsument robi coś innego");
                long start = System.currentTimeMillis();
                while(!future.isAvailable()){
                    //do something else
                    if(System.currentTimeMillis() - startTime > timeLimit) return;
                }
                long end = System.currentTimeMillis();
                printResult(future.getData(), end-start);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
