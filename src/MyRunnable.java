class MyRunnable implements Runnable {
    private int id;
    private IntOperation intOperation;

    MyRunnable( int id, IntOperation intOperation) {
        this.id = id;
        this.intOperation = intOperation;
    }

    @Override
    public void run() {
        if(id % 2 == 0){
            intOperation.increment();
        } else{
            intOperation.decrement();
        }
    }
}
