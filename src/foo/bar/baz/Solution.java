package foo.bar.baz;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public interface Solution {

    CompletableFuture<BigDecimal> leidnizPi(Integer accuracy) throws ExecutionException, InterruptedException;
}
