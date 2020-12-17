package com.epam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Printer {
    private final Logger logger = LoggerFactory.getLogger(Printer.class);
    private final String constantValue = "I am a constant value";
    private String variableValue = "I am a variable value";

    public void printTest() {
        logger.warn("Just print test");
    }

    public void printConstant() {
        logger.warn(constantValue);
    }

    public void printVariable() {
        logger.warn(variableValue);
    }

    public void methodWithParams(int num, String string) {
        logger.warn("Hello from methodWithParams ");
    }

}
