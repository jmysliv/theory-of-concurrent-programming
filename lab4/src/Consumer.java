public class Consumer implements  Runnable{
    private Monitor monitor;
    Consumer(Monitor monitor){
        this.monitor = monitor;
    }

    @Override
    public void run() {
        while(true){
            try {
                monitor.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try{
                Thread.sleep((long)(Math.random() * 10000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}