public class Semaphore{
    private boolean resource = true;


    public synchronized void semwait(){
        while(!resource){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        resource = false;
    }

    public synchronized void semsignal(){
        resource = true;
        this.notify();
    }
}