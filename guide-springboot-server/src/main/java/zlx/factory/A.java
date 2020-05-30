package zlx.factory;

import lombok.Data;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
public class A {
    B b;
    public A(){
        System.out.println("A is constuct");
    }

}
