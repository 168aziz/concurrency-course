package course.concurrency.m3_shared.immutable;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Order {

    public enum Status {NEW, IN_PROGRESS, DELIVERED}

    private final Long id;
    private final List<Item> items;
    private final PaymentInfo paymentInfo;
    private final boolean isPacked;
    private final Status status;

    private static final AtomicLong nextId = new AtomicLong();

    public Order(List<Item> items) {
        this(nextId.incrementAndGet(), items, null, false, Order.Status.NEW);
    }

    private Order(Long id, List<Item> items, PaymentInfo paymentInfo, boolean isPacked, Order.Status status) {
        this.id = id;
        this.items = items;
        this.status = status;
        this.isPacked = isPacked;
        this.paymentInfo = paymentInfo;
    }

    public Order withStatus(Order.Status status) {
        return new Order(this.id, this.items, this.paymentInfo, this.isPacked, status);
    }

    public Order withPaymentInfo(PaymentInfo paymentInfo) {
        return new Order(this.id, this.items, paymentInfo, this.isPacked, this.status);
    }

    public Order doPack() {
        return new Order(this.id, this.items, this.paymentInfo, true, this.status);

    }


    public boolean checkStatus() {
        return paymentInfo != null && isPacked;
    }

    public Long getId() {
        return id;
    }

    public List<Item> getItems() {
        return items;
    }

    public PaymentInfo getPaymentInfo() {
        return paymentInfo;
    }

    public boolean isPacked() {
        return isPacked;
    }

    public Status getStatus() {
        return status;
    }

}
