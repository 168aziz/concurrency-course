package course.concurrency.m2_async.cf.min_price;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class PriceAggregator {

    private PriceRetriever priceRetriever = new PriceRetriever();

    public void setPriceRetriever(PriceRetriever priceRetriever) {
        this.priceRetriever = priceRetriever;
    }

    private Collection<Long> shopIds = Set.of(10l, 45l, 66l, 345l, 234l, 333l, 67l, 123l, 768l);

    public void setShops(Collection<Long> shopIds) {
        this.shopIds = shopIds;
    }

    public double getMinPrice(long itemId) {
        Function<Long, CompletableFuture<Double>> priceFunction =
                id -> CompletableFuture.supplyAsync(() -> priceRetriever.getPrice(itemId, id))
                        .completeOnTimeout(Double.POSITIVE_INFINITY, 3000, TimeUnit.MILLISECONDS)
                        .handle((res, ex) -> res != null ? res : Double.POSITIVE_INFINITY);

        CompletableFuture[] completableFutures = shopIds.stream()
                .map(priceFunction)
                .toArray(CompletableFuture[]::new);

        CompletableFuture.allOf(completableFutures).join();

        return Arrays.stream(completableFutures)
                .mapToDouble(a-> (double) a.join())
                .filter(Double::isFinite)
                .min()
                .orElse(Double.NaN);
    }
}
