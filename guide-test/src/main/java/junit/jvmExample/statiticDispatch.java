package junit.jvmExample;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.Serializable;

@Slf4j
public class statiticDispatch {
    static class _360{

    }
    static class QQ{

    }
    static class Father{
        public void choose(QQ p){
            log.info("father qq " );
        }
        public void choose(_360 p){
            log.info("father 360 " );
        }
    }
    static class Son extends Father{
        public void choose(QQ p){
            log.info("son qq " );
        }
        public void choose(_360 p){
            log.info("son 360 " );
        }
    }

    @Test
    public void hardChoose(){
        Father father=new Father();
        Father son=new Son();

        father.choose(new QQ());
        father.choose(new _360());

        son.choose(new QQ());
        son.choose(new _360());
    }


    public void print(char a){ //1
      log.info("char " );
    }
//    public void print(int a){ //2
//        log.info("int " );
//    }
    public void print(Integer a){
        log.info("Integer " );
    }
//    public void print(long a){ //3
//        log.info("long " );
//    }

//    public void print(float a){ //4
//        log.info("float " );
//    }
    public void print(boolean a){ //4
        log.info("float " );
    }
//    public void print(double a){ //5
//        log.info("double " );
//    }

//    public void print(short a){
//        log.info("short " );
//    }
//    public void print(byte a){
//        log.info("byte " );
//    }


    //    public void print(Character a){ //6
//        log.info("Character " );
//    }
//    public void print(Serializable a){ //7
//        log.info("Serializable " );
//    }
    public void print(Object a){ //8
        log.info("Object " );
    }

//    public void print(Comparable a){ //同时存在 both method print(Serializable) in statiticDispatch and method print(Comparable) in statiticDispatch match
//        log.info("Comparable " );
//    }

    @Test
    public void test(){

//        print('a');
        print( (byte)23);
    }

    /**
     * 一个char是可以表示汉字的
     * char是16位的,无符号的，short是16位有符号，不能自动转换
     */
    @Test
    public void canNotAutoConvert(){
        System.out.println(Byte.MIN_VALUE + " " + Byte.MAX_VALUE);
        System.out.println(Character.MIN_VALUE + " " + Character.MAX_VALUE);
        System.out.println( Integer.valueOf(Character.MIN_VALUE)  + " " + Integer.valueOf(Character.MAX_VALUE));
        System.out.println(Short.MIN_VALUE + " " + Short.MAX_VALUE);

        char c='中';
        System.out.println(Integer.valueOf(c));
        System.out.println(Character.valueOf( (char)20013));  //一个char是可以表示汉字的

    }

    @Test
    public void StringTest(){
        String str = " ";
        System.out.println(str.length());

        log.info("len:{}","".length());
        log.info("len:{}"," ".length());
    }

    public static void main(String... args){
        String str = " ";
        System.out.println(str.length());

        String str2 = "a";
        System.out.println(str2.length());

        String str3 = "aa";
        System.out.println(str3.length());
    }


    @Test
    public void equal(){
        Object x="str";
        String a=new String("str");

        boolean flag=a.equals(x);
        System.out.println(flag);
    }
}
