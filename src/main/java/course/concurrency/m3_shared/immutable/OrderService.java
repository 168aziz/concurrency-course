package course.concurrency.m3_shared.immutable;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

public class OrderService {

    private final Map<Long, AtomicReference<Order>> currentOrders = new ConcurrentHashMap<>();
    private long nextId = 0L;

    private synchronized long nextId() {
        return nextId++;
    }

    public long createOrder(List<Item> items) {
        Order order = new Order(items);
        currentOrders.put(order.getId(), new AtomicReference<>(order));
        return order.getId();
    }

    public void updatePaymentInfo(long orderId, PaymentInfo paymentInfo) {
        Order current, paid;
        do {
            current = currentOrders.get(orderId).get();
            paid = current.withPaymentInfo(paymentInfo);
        } while (!currentOrders.get(orderId).compareAndSet(current, paid));

        if (paid.checkStatus()) {
            deliver(paid);
        }
    }

    public void setPacked(long orderId) {
        Order current, packed;

        do {
            current = currentOrders.get(orderId).get();
            packed = current.doPack();
        } while (!currentOrders.get(orderId).compareAndSet(current, packed));

        if (packed.checkStatus()) {
            deliver(packed);
        }
    }

    private void deliver(Order order) {
        /* ... */
        Order delivered, current;
        do {
            current = currentOrders.get(order.getId()).get();
            delivered = current.withStatus(Order.Status.DELIVERED);
        } while (!currentOrders.get(order.getId()).compareAndSet(current, delivered));
    }

    public boolean isDelivered(long orderId) {
        return currentOrders.get(orderId)
                .get()
                .getStatus()
                .equals(Order.Status.DELIVERED);
    }
}
