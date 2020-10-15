class MyRunnable implements Runnable {
    private int id;
    private IntOperation intOperation;
    private Semaphore semaphore;

    MyRunnable( int id, IntOperation intOperation, Semaphore semaphore) {
        this.id = id;
        this.intOperation = intOperation;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try{
            Thread.sleep((long)(Math.random() * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        semaphore.semwait();
        if(id % 2 == 0){
            intOperation.increment();
        } else{
            intOperation.decrement();
        }
        semaphore.semsignal();
    }
}
