package com.epam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImpleUse {

    private final IDoable doable;
    private final Logger logger = LoggerFactory.getLogger(ImpleUse.class);

    public ImpleUse() {
        this.doable = new DoableImpl();
    }

    public void printInfo() {
        doable.printInfo();
    }

    public void calculate(int a, int b) {
        //logger.info(a + " some operator " + b + " = " +doable.calculate(a, b));//TODO FIX or Del//NULL POINTER!
        System.out.println(a + " some operator " + b + " = " + doable.calculate(a, b));
    }

    public void printInput(String input) {
        doable.printInput(input);
    }
}
