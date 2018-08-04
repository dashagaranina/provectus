package foo.bar.baz;

import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public interface Solution {

    Future<BigDecimal> leidnizPi(Integer accuracy) throws ExecutionException, InterruptedException;
}
