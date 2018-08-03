package foo.bar.baz;

import foo.bar.baz.impl.StandaloneSolution;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {

    private BlockingQueue<Integer> blockingQueue;
    private String name;
    private Integer count = 0;

    public Consumer(BlockingQueue<Integer> blockingQueue, String name) {
        this.blockingQueue = blockingQueue;
        this.name=name;
    }

    @Override
    public void run() {
        while (true){
            try {
                if (blockingQueue.isEmpty()){
                    Thread.sleep(50);
                    if (blockingQueue.isEmpty()) {
                        System.out.println("====================="+name + " got result " + count + " times");
                        break;
                    }
                }
                Integer acc = blockingQueue.take();
                System.out.println(name + " takes = "+ acc);
                Solution solution = new StandaloneSolution();
                System.out.println(name + " result (accuracy = " + acc + ") " + solution.leibnizPi2(acc));
                count++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
