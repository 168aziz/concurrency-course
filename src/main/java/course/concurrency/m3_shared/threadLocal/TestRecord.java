package course.concurrency.m3_shared.threadLocal;



class Super { static String ID = "QBANK"; }

class Sub extends Super{
    static { System.out.print("In Sub"); }
}
public class TestRecord{
    public static void main(String[] args) {
        Movable m = new Donkey();
        m.move(10);
        m.moveBack(20);
        System.out.println(m.location);
    }
}

class A{ }
class B extends A{ }
class C extends B{ }

class X{
    protected B getB(){ return new B(); }
}

class Y extends X{
    protected B getB(){ return new C(); }

}


interface Movable {
    int location = 0;
    void move(int by);
    public void moveBack(int by);
}

class Donkey implements Movable{
    int location = 200;
    public void move(int by) {
        location = location+by;
    }
    public void moveBack(int by) {
        location = location-by;
    }
}