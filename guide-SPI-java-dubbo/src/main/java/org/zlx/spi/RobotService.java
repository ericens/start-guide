package org.zlx.spi;

import com.alibaba.dubbo.common.extension.SPI;

@SPI
public interface RobotService {
    void sayHello();
}