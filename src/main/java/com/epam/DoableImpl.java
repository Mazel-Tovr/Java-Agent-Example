package com.epam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DoableImpl implements IDoable {

    private Logger logger = LoggerFactory.getLogger(DoableImpl.class);

    @Override
    public void printInfo() {
        logger.info("Hello from " + DoableImpl.class);
    }

    @Override
    public int calculate(int a, int b) {
        return a + b;
    }

    @Override
    public void printInput(String input) {
        logger.info(input);
    }
}
