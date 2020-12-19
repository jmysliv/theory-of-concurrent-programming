import org.jcsp.lang.*;

public class Producer implements CSProcess {
    private final Any2OneChannel<Message> personalInboxChannel = Channel.any2one();
    private final One2OneChannelInt personalChannel = Channel.one2oneInt();
    private final Any2OneChannel<Message> channel;
    private final int id;

    public Producer(Any2OneChannel<Message> channel, int id) {
        this.channel = channel;
        this.id = id;
    }

    public void run() {
        for (int k = 0; k < 100; k++) {
            channel.out().write(new Message(MessageType.PRODUCE_REQUEST, personalInboxChannel, personalChannel));
            while (personalInboxChannel.in().read().getType() != MessageType.APPROVED);
            int item = (int) (Math.random() * 100) + 1;
            System.out.println("Producer: " + id + " produce " + item);
            personalChannel.out().write(item);
        }
    }
}