import org.jcsp.lang.*;

public final class Main {
    public static void main(String[] args) {
        final Any2OneChannel<Message> bufferManagerChannel = Channel.any2one();
        CSProcess[] procList = {new Producer(bufferManagerChannel, 0),
                new Producer(bufferManagerChannel, 1),
                new Producer(bufferManagerChannel, 2),
                new BufferManager(bufferManagerChannel),
                new Buffer(bufferManagerChannel, 0),
                new Buffer(bufferManagerChannel, 1),
                new Consumer(bufferManagerChannel, 0),
                new Consumer(bufferManagerChannel, 1)};
        Parallel par = new Parallel(procList);
        par.run();
    }
}
