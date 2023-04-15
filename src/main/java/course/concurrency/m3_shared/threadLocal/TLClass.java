package course.concurrency.m3_shared.threadLocal;

public class TLClass {

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                GridThreadSerialNumber gridThreadSerialNumber = new GridThreadSerialNumber();
                System.out.println(gridThreadSerialNumber.serialNum.get());
                System.out.println(gridThreadSerialNumber.get());
            }).start();
        }
    }


}
