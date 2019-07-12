package org.zlx.spi.impl;

import org.zlx.spi.RobotService;

public class OptimusPrimeImpl implements RobotService {

    @Override
    public void sayHello() {
        System.out.println("Hello, I am Optimus Prime.");
    }
}