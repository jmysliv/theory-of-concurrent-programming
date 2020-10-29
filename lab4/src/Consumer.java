public class Consumer implements  Runnable{
    private Monitor monitor;
    Consumer(Monitor monitor){
        this.monitor = monitor;
    }

    @Override
    public void run() {
        while(true){
            int howMuchConsume  = (int) (Math.random() * 10) + 1;
            try {
                monitor.consumeMany(howMuchConsume);
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