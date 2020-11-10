public class MonitorWithHasWaiters extends Monitor{
    MonitorWithHasWaiters(int size) {
        super(size);
    }

    public void produce(int number) throws InterruptedException {
        lock.lock();
        printStats();

        while(lock.hasWaiters(firstProducer)) {
            System.out.println("Producent czeka na reszcie");
            restProducersSize++;
            restProducers.await();
            restProducersSize--;
        }

        while(maxSize - this.resources.size() < number){
            firstProducerSize ++;
            System.out.println("Producent pierwszy w kolejce");
            firstProducer.await();
            firstProducerSize --;
        }

        for(int i=0; i<number; i++){
            resources.add(number);
        }

        System.out.println("Producent wyprodukował: " + number);
        System.out.println("Rozmiar kolejki: " + resources.size());
        restProducers.signal();
        firstConsumer.signal();
        lock.unlock();
    }

    public void consume(int number) throws InterruptedException {
        lock.lock();
        printStats();

        while(lock.hasWaiters(firstConsumer)){
            restConsumersSize++;
            System.out.println("Konsument czeka na reszcie");
            restConsumers.await();
            restConsumersSize --;
        }

        while(this.resources.size() < number) {
            firstConsumerSize ++;
            System.out.println("Konsument pierwszy w kolejce");
            firstConsumer.await();
            firstConsumerSize --;
        }
        for (int i = 0; i < number; i++) {
            resources.poll();
        }

        System.out.println("Konsument pobrał: " + number);
        System.out.println("Rozmiar kolejki: " + resources.size());
        restConsumers.signal();
        firstProducer.signal();
        lock.unlock();
    }
}
