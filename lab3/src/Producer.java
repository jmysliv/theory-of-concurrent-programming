public class Producer implements  Runnable{
    private Monitor monitor;
    Producer(Monitor monitor){
        this.monitor = monitor;
    }

    @Override
    public void run() {
        while(true){
            int randomInt  = (int) (Math.random() * 10) + 1;
            monitor.produce(randomInt);
            try{
                Thread.sleep((long)(Math.random() * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
