import org.jcsp.lang.*;

/**
 * Producer class: produces 100 random integers and sends them on
 * output channel, then sends -1 and terminates.
 */
public class Producer implements CSProcess {
    private final One2OneChannelInt channel;
    private final int id;

    public Producer(One2OneChannelInt out, int id) {
        channel = out;
        this.id = id;
    }

    public void run() {
        int item;
        ChannelOutputInt channelOutput = channel.out();
        for (int k = 0; k < 100; k++) {
            item = (int) (Math.random() * 100) + 1;
            System.out.println("Producer: " + id + " produce " + item);
            channelOutput.write(item);
        }
        channelOutput.write(-1);
        System.out.println("Producer: " + id + " ended.");
    }
}