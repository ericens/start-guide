package zlx.factory;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class B {

    A a;
    public B(){
        System.out.println("B is constuct");
    }

}
