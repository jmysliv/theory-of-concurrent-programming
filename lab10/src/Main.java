import org.jcsp.lang.*;

/**
 * Main program class for Producer/Consumer example.
 * Sets up channels, creates processes then
 * executes them in parallel, using JCSP.
 */
public final class Main {
    public static void main(String[] args) {
        final One2OneChannelInt[] prodChan = {Channel.one2oneInt(), Channel.one2oneInt()};
        final One2OneChannelInt[] consReq = {Channel.one2oneInt(), Channel.one2oneInt()};
        final One2OneChannelInt[] consChan = {Channel.one2oneInt(), Channel.one2oneInt()};
        CSProcess[] procList = {new Producer(prodChan[0], 0),
                new Producer(prodChan[1], 1),
                new Buffer(prodChan, consReq, consChan),
                new Consumer(consReq[0], consChan[0], 2),
                new Consumer(consReq[1], consChan[1], 3)};
        Parallel par = new Parallel(procList);
        par.run();
    }
}
