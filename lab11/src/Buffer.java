import org.jcsp.lang.*;


public class Buffer implements CSProcess {
    private final Any2OneChannel<Message> channel;
    private final Any2OneChannel<Message> personalInboxChannel = Channel.any2one();
    private final int id;
    private int bufferData = -1;


    public Buffer(Any2OneChannel<Message> channel, int id) {
        this.channel = channel;
        this.id = id;
    }

    public void run() {
        Message emptyBufferMessage = new Message(MessageType.EMPTY, personalInboxChannel);
        Message fullBufferMessage = new Message(MessageType.FULL, personalInboxChannel);
        channel.out().write(emptyBufferMessage);
        while(true){
            Message message = personalInboxChannel.in().read();
            if(message.getType() == MessageType.CHANNEL){
                if(bufferData == -1){
                    bufferData = message.getChannel().in().read();
                    System.out.println("Buffer " + id + " otrzymal " + bufferData);
                    channel.out().write(fullBufferMessage);
                } else{
                    System.out.println("Buffer " + id + " wysyla " + bufferData);
                    message.getChannel().out().write(bufferData);
                    bufferData = -1;
                    channel.out().write(emptyBufferMessage);
                }
            }
        }
    }
}