import java.util.concurrent.ThreadLocalRandom;

public class Sender implements Runnable {
    private Data data;

    public Sender(Data data){
        this.data = data;
    }

    public void run() {
        String messages[] = {
                "Hello 1",
                "Hello 2",
                "Hello 3",
                "Bye"
        };

        for (String message : messages) {
            data.send(message);

            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 5000));
            } catch (InterruptedException e)  {
                Thread.currentThread().interrupt();
                System.out.println("Thread interrupted");
            }
        }
    }
}