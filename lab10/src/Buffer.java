import org.jcsp.lang.*;

/**
 * Buffer class: Manages communication between Producer
 * and Consumer classes.
 */
public class Buffer implements CSProcess {
    private final One2OneChannelInt[] in; // Input from Producer
    private final One2OneChannelInt[] req; // Request for data from Consumer
    private final One2OneChannelInt[] out; // Output to Consumer

    private final int[] buffer = new int[10];
    int producedCounter = -1;
    int consumedCounter = -1;

    public Buffer(One2OneChannelInt[] in, One2OneChannelInt[] req, One2OneChannelInt[] out) {
        this.in = in;
        this.req = req;
        this.out = out;
    }

    public void run() {
        final Guard[] guards = {in[0].in(), in[1].in(), req[0].in(), req[1].in()};

        final Alternative alt = new Alternative(guards);
        int countdown = 4;
        while (countdown > 0) {
            int index = alt.select();
            switch (index) {
                case 0:
                case 1:
                    if (producedCounter < consumedCounter + 11) // Space available
                    {
                        ChannelInputInt channelInputInt = in[index].in();
                        int item = channelInputInt.read();
                        if (item < 0)
                            countdown--;
                        else {
                            producedCounter++;
                            buffer[producedCounter % buffer.length] = item;
                        }
                    }
                    break;
                case 2:
                case 3: // A Consumer is ready to read
                    ChannelInputInt channelInputInt2 = req[index - 2].in();
                    ChannelOutputInt channelOutputInt2 = out[index - 2].out();
                    if (consumedCounter < producedCounter) // Item(s) available
                    {
                        channelInputInt2.read();
                        consumedCounter++;
                        int item = buffer[consumedCounter % buffer.length];
                        channelOutputInt2.write(item);
                    } else if (countdown <= 2) // Signal consumer to end
                    {
                        channelInputInt2.read();
                        channelOutputInt2.write(-1);
                        countdown--;
                    }
                    break;
            }
        }
        System.out.println("Buffer ended.");
    }
}