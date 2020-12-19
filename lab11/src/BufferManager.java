import org.jcsp.lang.Any2OneChannel;
import org.jcsp.lang.CSProcess;

import java.util.LinkedList;
import java.util.Queue;

public class BufferManager implements CSProcess {
    private final Any2OneChannel<Message> channel; //channel for requests
    private final Queue<Message> emptyBuffers = new LinkedList<>();
    private final Queue<Message> fullBuffers = new LinkedList<>();
    private final Queue<Message> producers = new LinkedList<>();
    private final Queue<Message> consumers = new LinkedList<>();


    public BufferManager(Any2OneChannel<Message> channel) {
        this.channel = channel;
    }

    public void run() {
        while(true){
            Message message = channel.in().read();
            switch (message.getType()){
                case EMPTY ->{
                    if(!producers.isEmpty()){
                        Message produceRequest = producers.poll();
                        produceRequest.getSenderInboxChannel().out().write(new Message(MessageType.APPROVED, channel));
                        message.getSenderInboxChannel().out().write(new Message(MessageType.CHANNEL, channel, produceRequest.getChannel()));
                    } else{
                        emptyBuffers.add(message);
                    }
                }
                case FULL -> {
                    if(!consumers.isEmpty()){
                        Message consumeRequest = consumers.poll();
                        consumeRequest.getSenderInboxChannel().out().write(new Message(MessageType.APPROVED, channel));
                        message.getSenderInboxChannel().out().write(new Message(MessageType.CHANNEL, channel, consumeRequest.getChannel()));
                    } else{
                        fullBuffers.add(message);
                    }
                }
                case PRODUCE_REQUEST -> {
                    if(!emptyBuffers.isEmpty()){
                        Message emptyBuffer = emptyBuffers.poll();
                        message.getSenderInboxChannel().out().write(new Message(MessageType.APPROVED, channel));
                        emptyBuffer.getSenderInboxChannel().out().write(new Message(MessageType.CHANNEL, channel, message.getChannel()));
                    } else{
                        producers.add(message);
                    }
                }
                case CONSUME_REQUEST -> {
                    if(!fullBuffers.isEmpty()){
                        Message fullBuffer = fullBuffers.poll();
                        message.getSenderInboxChannel().out().write(new Message(MessageType.APPROVED, channel));
                        fullBuffer.getSenderInboxChannel().out().write(new Message(MessageType.CHANNEL, channel, message.getChannel()));
                    } else{
                        consumers.add(message);
                    }
                }
                default -> {
                    //wrong message type
                }
            }
        }
    }
}
