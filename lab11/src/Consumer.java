import org.jcsp.lang.*;

public class Consumer implements CSProcess {
    private final Any2OneChannel<Message> personalInboxChannel = Channel.any2one();
    private final One2OneChannelInt personalChannel = Channel.one2oneInt();
    private final Any2OneChannel<Message> channel;
    private final int id;

    public Consumer(Any2OneChannel<Message> channel, int id) {
        this.id = id;
        this.channel = channel;
    }

    public void run() {
        while (true) {
            channel.out().write(new Message(MessageType.CONSUME_REQUEST, personalInboxChannel, personalChannel));
            while (personalInboxChannel.in().read().getType() != MessageType.APPROVED);
            int item = personalChannel.in().read();
            System.out.println("Consumer: " + id + " consumed " + item);
        }
    }
}