public class Data {
    private String message;

    // True if receiver should wait
    // False if sender should wait
    private boolean transfer = true;

    public synchronized void send(String packet) {
        while (!transfer) {
            try {
                wait();
            } catch (InterruptedException e)  {
                Thread.currentThread().interrupt();
                System.out.println("Thread interrupted");
            }
        }
        transfer = false;
        this.message = packet;
        notifyAll();
    }

    public synchronized String receive() {
        while (transfer) {
            try {
                wait();
            } catch (InterruptedException e)  {
                Thread.currentThread().interrupt();
                System.out.println("Thread interrupted");
            }
        }
        transfer = true;

        notifyAll();
        return message;
    }
}