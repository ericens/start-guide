package java.lang;

public class Long {
    public int x=0;
    public void testClassLoader(){
        System.out.println("自定义Long类被"+Long.class.getClassLoader()+"加载了");
    }
    public static Long valueOf(long var0) {
        Long instance=new Long();
        return instance;
    }

    public static void main(String[] args) {
        System.out.println("Long");
    }
}