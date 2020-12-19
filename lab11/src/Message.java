import org.jcsp.lang.Any2OneChannel;
import org.jcsp.lang.One2OneChannelInt;

public class Message {
    private final MessageType type;
    private final Any2OneChannel<Message> senderInboxChannel;
    private One2OneChannelInt channel;

    public Message(MessageType type, Any2OneChannel<Message> senderInboxChannel) {
        this.type = type;
        this.senderInboxChannel = senderInboxChannel;
    }

    public Message(MessageType type, Any2OneChannel<Message> senderInboxChannel, One2OneChannelInt channel) {
        this.type = type;
        this.senderInboxChannel = senderInboxChannel;
        this.channel = channel;
    }

    public MessageType getType() {
        return type;
    }

    public One2OneChannelInt getChannel() {
        return channel;
    }

    public Any2OneChannel<Message> getSenderInboxChannel() {
        return senderInboxChannel;
    }
}
