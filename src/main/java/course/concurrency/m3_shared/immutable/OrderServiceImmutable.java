package course.concurrency.m3_shared.immutable;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

public class OrderServiceImmutable {

    // AtomicReference is used to prevent lost updates via CAS operations
    private final ConcurrentHashMap<Long, AtomicReference<OrderImmutable>> currentOrders = new ConcurrentHashMap<>();

    public long createOrder(List<Item> items) {
        OrderImmutable order = new OrderImmutable(items);
        currentOrders.put(order.getId(), new AtomicReference<>(order));
        return order.getId();
    }

    public void updatePaymentInfo(long orderId, PaymentInfo paymentInfo) {
        OrderImmutable current, paid;
        do {
            current = currentOrders.get(orderId).get();
            paid = current.withPaymentInfo(paymentInfo);
        } while (!currentOrders.get(orderId).compareAndSet(current, paid));

        // paid is a local immutable object, we can safely check its status without synchronization
        if (paid.checkStatus()) {
            deliver(paid);
        }
    }

    // CAS update is a classic partner to immutable objects
    public void setPacked(long orderId) {
        OrderImmutable current, packed;
        do {
            current = currentOrders.get(orderId).get();
            packed = current.doPack();
        } while (!currentOrders.get(orderId).compareAndSet(current, packed));

        if (packed.checkStatus()) {
            deliver(packed);
        }
    }

    private void deliver(OrderImmutable order) {
        OrderImmutable current, delivered;
        long orderId = order.getId();
        do {
            current = currentOrders.get(orderId).get();
            delivered = current.withStatus(OrderImmutable.Status.DELIVERED);
        } while (!currentOrders.get(orderId).compareAndSet(current, delivered));

    }

    public boolean isDelivered(long orderId) {
        return currentOrders.get(orderId).get().getStatus().equals(OrderImmutable.Status.DELIVERED);
    }
}
