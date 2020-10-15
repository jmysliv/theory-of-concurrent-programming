class Philosopher implements Runnable {
    private int id;
    private Semaphore left;
    private Semaphore right;

    Philosopher( int id, Semaphore right, Semaphore left) {
        this.id = id;
        this.right = right;
        this.left = left;
    }

    @Override
    public void run() {
        while(true){
            System.out.println("Filozof nr" + id + " spi");
            try{
                Thread.sleep((long)(Math.random() * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            left.semwait();
            right.semwait();
            System.out.println("Filozof nr" + id + " je");
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            left.semsignal();
            right.semsignal();
        }

    }
}
