package cn.gyw.corejava.concurrent;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PrimeUtil {

    /**
     * 1s 素数生成器
     * @return
     * @throws InterruptedException
     */
    List<BigInteger> aSencondOfPrimes() throws InterruptedException {
        PrimeGenerator generator = new PrimeGenerator();
        new Thread(generator).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } finally {
            generator.cancel();
        }

        return generator.getPrimes();
    }
}
