package junit.jvmExample;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;

public  class Son extends Fater{
    public void print(){
        System.out.println("son start");


        super.print();
//        super.super.print();   报错

        //使用method handle来实现
        MethodType methodType=MethodType.methodType(void.class);
        try {
            Field m=MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP");
            m.setAccessible(true);

            MethodHandle methodHandle= ((MethodHandles.Lookup)m.get(null)).findSpecial(GrandFater.class,"print",methodType,GrandFater.class);
            methodHandle.invoke(this);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        System.out.println("son end");
    }
}