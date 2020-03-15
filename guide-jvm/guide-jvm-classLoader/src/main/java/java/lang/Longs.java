package java.lang;

public class Longs {
    public void testClassLoader(){
        System.out.println("自定义Long类被"+ Longs.class.getClassLoader()+"加载了");
    }
    public static void main(String[] args) {
        System.out.println("Long");
    }
}