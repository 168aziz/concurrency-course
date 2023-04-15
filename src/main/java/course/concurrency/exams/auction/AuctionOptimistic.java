package course.concurrency.exams.auction;

import java.util.concurrent.atomic.AtomicStampedReference;

public class AuctionOptimistic implements Auction {

    private final Notifier notifier;

    public AuctionOptimistic(Notifier notifier) {
        this.notifier = notifier;
    }

    private final AtomicStampedReference<Bid> latestBid = new AtomicStampedReference<Bid>(new Bid(-1L, -1L, -1L), 0);

    public boolean propose(Bid bid) {
        Bid newBid, expected;
        do {
            expected = latestBid.getReference();

            newBid = bid;

            if (expected != null && newBid.getPrice() <= expected.getPrice())
                return false;

        } while (!latestBid.compareAndSet(expected, newBid, latestBid.getStamp(), latestBid.getStamp() + 1));

        notifier.sendOutdatedMessage(latestBid.getReference());
        return true;
    }

    public Bid getLatestBid() {
        return latestBid.getReference();
    }

    public Bid stopAuction() {
        return latestBid.getReference();
    }

}
