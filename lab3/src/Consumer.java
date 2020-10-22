public class Consumer implements  Runnable{
    private Monitor monitor;
    Consumer(Monitor monitor){
        this.monitor = monitor;
    }

    @Override
    public void run() {
        while(true){
            monitor.consume();
            try{
                Thread.sleep((long)(Math.random() * 10000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
