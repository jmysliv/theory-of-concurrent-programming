public class Semaphore{
    private int resource;

    public Semaphore(int initialValue){
        this.resource = initialValue;
    }

    public synchronized void semwait(){
        while(this.resource == 0){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        resource--;
    }

    public synchronized void semsignal(){
        resource++;
        this.notify();
    }
}