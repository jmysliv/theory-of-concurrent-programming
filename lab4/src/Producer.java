public class Producer implements  Runnable{
    private Monitor monitor;
    Producer(Monitor monitor){
        this.monitor = monitor;
    }

    @Override
    public void run() {
        while(true){
            int howMuchProduce  = (int) (Math.random() * 10) + 1;
            try {
                monitor.produceMany(howMuchProduce);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try{
                Thread.sleep((long)(Math.random() * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}