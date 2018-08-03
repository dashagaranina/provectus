package foo.bar.baz;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {

    private BlockingQueue<Integer> blockingQueue;

    public Producer(BlockingQueue<Integer> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        Random random = new Random();

        for (int i = 0; i < 50; i++) {
            try {
                Thread.sleep(i);
                Integer value = random.nextInt(5)+3;
                blockingQueue.put(value);
                System.out.println("queue size "+ blockingQueue.size()+", value = "+ value+ ", "+i+" iteration");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
