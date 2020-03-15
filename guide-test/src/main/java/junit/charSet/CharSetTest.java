package junit.charSet;

import joptsimple.internal.Strings;
import lombok.extern.slf4j.Slf4j;

import org.junit.Test;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class CharSetTest {

    @Test
    public void bitTest(){
        int a=3,b=2,c=1;
        if( true | a++ >0 ){
            log.info("ss");
        }
        if( false & c++ >0 ){
            log.info("ss");
        }
//  |  & 非短路操作
//        || &&  短路操作
        log.info("a:{}, c:{}",a,c);

        a=0x12345678;
        log.info("a:{}",Integer.toHexString(a));

        a=a>>4;
        log.info("a:{}",Integer.toHexString(a));

        a=0xf0345678;
        b=a>>>4;
        log.info("a:{},ax:{}, b:{},  bx:{}",a,Integer.toHexString(a) , b,Integer.toHexString(b));



    }

    private static class Pair<T> {
        final T reference;
        final int stamp;
        private Pair(T reference, int stamp) {
            this.reference = reference;
            this.stamp = stamp;
        }
        static <T> Pair<T> of(T reference, int stamp) {
            return new Pair<T>(reference, stamp);
        }
    }

    public void test3(){


    }


    @Test
    public void test(){
        String str="└╧┬э";
        //└╧┬э  老马    c1:GB18030,c2:IBM866,result:└╧┬э
        //              c1:IBM866,c2:GBK,result:老马
        log.info("xx {}",Charset.forName("Windows-1252"));

        log.info(":{}", "老马".getBytes( Charset.forName("GB18030")));
        log.info(":{}", "└╧┬э".getBytes( Charset.forName("IBM866")));
        log.info(":{}", "哈哈的发送".getBytes( Charset.forName("Windows-1252")));


        byte byteOrigin[]="老马".getBytes( Charset.forName("GB18030"));  // 编码成byte
        String str1=new String(byteOrigin,Charset.forName("Windows-1252")); // 错误的解码成 str
        byte[] byteUtf8=str1.getBytes(Charset.forName("UTF-8"));            // 根据错误str, 转码成UTF-8
        String gbkStr=new String(byteUtf8,Charset.forName("GB18030"));      // 解码 失败
        log.info("byteOrigin:{},str1:{},byteUtf8:{}, bgkStr:{}",byteOrigin,str1,byteUtf8,gbkStr);
        testAllCharSet(gbkStr);


        for (int i = 0; i < byteOrigin.length; i++) {
            log.info("b {}", Integer.toHexString( byteOrigin[i]));
        }

    }
    public void testAllCharSet(String str){
        List<Charset> charsets=Charset.availableCharsets().entrySet().stream()
                .filter( en->en.getKey().startsWith("U") || en.getKey().startsWith("G")
                        || en.getKey().startsWith("W") )
                .map(en-> en.getValue())
                .collect(Collectors.toList());

        charsets.clear();
        charsets.add(Charset.forName("Windows-1252"));
        charsets.add(Charset.forName("GB18030"));
        charsets.add(Charset.forName("UTF-8"));
        charsets.forEach(
                i-> log.info("{}",i)
        );



        charsets.forEach(c1-> charsets.forEach(
                c2->charsets.forEach(
                        c3-> charsets.forEach(
                                c4->{
                                    byte[] bytes=str.getBytes(c1);
                                    String testStr0=new String(bytes,c2);
                                    byte []bs=testStr0.getBytes(c3);
                                    String testStr=new String(bs,c4);
                                    if( "老马".equals(testStr)) {
                                        log.info("bytes:%x{},bs:{}", bytes,bs);
                                        log.info("c1:{},c2:{},c3:{},c4:{},testStr0:{},testStr:{}",c1,c2,c3,c4,testStr0, testStr);

                                        log.info("原始编码c4:{},byte:{},str:{}",c4,testStr.getBytes(c4), testStr);
                                    }

                                    else if("老马".equals(testStr0) ) {
                                        log.info("c1:{},c2:{},c3:{},c4:{},testStr0:{},testStr:{}",c1,c2,c3,c4, testStr0);
                                    }


                                }
                        )

                )
                )

        );


        charsets.forEach(
                c1->charsets.forEach(
                        c2->{
                            try{
                                byte[] bytes=str.getBytes(c1);  //相当于B 步骤是 c1
                                String testStr0=new String(bytes,c2);  //相当于A 步骤是 c2
                                if(testStr0.equals("老马")){
                                    log.info("c1:{},c2:{},result:{},byte:{}",c1,c2,testStr0, bytes);
                                }
                            }catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                )


        );


    }




}
