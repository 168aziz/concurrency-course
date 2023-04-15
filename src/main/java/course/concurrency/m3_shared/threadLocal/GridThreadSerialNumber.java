package course.concurrency.m3_shared.threadLocal;

public class GridThreadSerialNumber {
    /** The next serial number to be assigned. */
    private int nextSerialNum = 0;

    /** */
    public ThreadLocal<Integer> serialNum = new ThreadLocal<Integer>() {
        @Override protected synchronized Integer initialValue() {
            return nextSerialNum++;
        }
    };

    /**
     * @return Serial number value.
     */
    public int get() {
        return serialNum.get();
    }
}