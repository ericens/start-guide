package junit;

public class SimpleFlame {
    static int count=0;
    public static void main(String[] args) throws InterruptedException {
        SimpleFlame simpleFlame=new SimpleFlame();
        simpleFlame.foo();
    }

    public void foo() {
        bar();
    }

    private void bar() {
        bar2();
    }

    private void bar2() {
        while(true) {
            Math.sqrt(22000002);
        }
//        System.out.println("bar"+(count++));

    }
}
