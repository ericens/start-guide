package org.zlx;

import org.zlx.spi.RobotService;
import org.junit.Test;

public class DubboSPITest {

    @Test
    public void sayHello() throws Exception {
        com.alibaba.dubbo.common.extension.ExtensionLoader<RobotService> extensionLoader =
                com.alibaba.dubbo.common.extension.ExtensionLoader.getExtensionLoader(RobotService.class);


        RobotService optimusPrime = extensionLoader.getExtension("optimusPrime");
        optimusPrime.sayHello();

        RobotService bumblebee = extensionLoader.getExtension("bumblebee");
        bumblebee.sayHello();

        RobotService sdf = extensionLoader.getExtension("sdf");
        sdf.sayHello();

    }
}
