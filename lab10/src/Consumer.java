import org.jcsp.lang.*;

/**
 * Consumer class: reads ints from input channel, displays them, then
 * terminates when a negative value is read.
 */
public class Consumer implements CSProcess {
    private final One2OneChannelInt in;
    private final One2OneChannelInt req;
    private final int id;

    public Consumer(One2OneChannelInt req, One2OneChannelInt in, int id) {
        this.req = req;
        this.in = in;
        this.id = id;
    }

    public void run() {
        int item;
        while (true) {
            ChannelOutputInt channelOutputInt = req.out();
            channelOutputInt.write(0);
            ChannelInputInt channelInputInt = in.in();
            item = channelInputInt.read();
            if (item < 0)
                break;
            System.out.println("Consumer: " + id + " consumed " + item);
        }
        System.out.println("Consumer: " + id + " ended.");
    }
}