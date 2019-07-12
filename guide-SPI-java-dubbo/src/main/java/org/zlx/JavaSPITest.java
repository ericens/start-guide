package org.zlx;

import org.zlx.spi.RobotService;
import org.junit.Test;


public class JavaSPITest {

    @Test
    public void sayHello() throws Exception {
        java.util.ServiceLoader<RobotService> serviceLoader = java.util.ServiceLoader.load(RobotService.class);
        System.out.println("Java SPI");

        serviceLoader.forEach(RobotService::sayHello);
    }
}