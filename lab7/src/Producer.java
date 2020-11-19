import java.util.List;

public class Producer implements Runnable{

    private Proxy proxy;
    private int maxNumber;
    private long worktime;

    public Producer(Proxy proxy, int maxNumber, long worktime){
        this.proxy = proxy;
        this.maxNumber = maxNumber;
        this.worktime = worktime;
    }

    private void printResult( List<Integer> result){
        System.out.println("Produced: " + result.stream()
                .map(Object::toString)
                .reduce("", (acc, number) -> acc + " " + number));
    }


    @Override
    public void run() {
        while(true){
            try {
                Future<List<Integer>> future = proxy.produce((int) (Math.random() * maxNumber + 1));
                System.out.println("Producent robi coś innego");
                Thread.sleep(worktime);
                while(!future.isAvailable()){
                    Thread.sleep(100);
                }
                printResult(future.getData());
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
