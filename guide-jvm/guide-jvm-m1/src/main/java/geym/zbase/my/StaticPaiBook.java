package geym.zbase.my;

class Father {
    public void hardChoice(_QQ hum) {
        System.out.println("father choice qq");
    }
    public void hardChoice(_360 hum) {
        System.out.println("father choice 360");
    }

}

class Son extends Father {

    public void hardChoice(_QQ hum) {
        System.out.println("son choice qq");
    }
    public void hardChoice(_360 hum) {
        System.out.println("son choice 360");
    }

}

class _QQ {
}

class _360 {
}


public class StaticPaiBook {

    static  {
        int x=3;
    }
    static int getPaiBook = 10;
    /**

     21 invokespecial #7 <geym/zbase/my/_QQ.<init>>
     24 invokevirtual #8 <geym/zbase/my/Father.hardChoice>
     27 aload_2
     28 new #9 <geym/zbase/my/_360>
     31 dup
     32 invokespecial #10 <geym/zbase/my/_360.<init>>
     35 invokevirtual #11 <geym/zbase/my/Father.hardChoice>
     38 return

     *
     * @param args
     */

    public int cal(){
        int a=100;
        int b=200;
        int c=300;

        return (0+b)*c;
    }

    public static void main(String[] args) {
        Father father = new Father();
        Father son=new Son();

        father.hardChoice(new _QQ());
        son.hardChoice(new _360());
    }
}