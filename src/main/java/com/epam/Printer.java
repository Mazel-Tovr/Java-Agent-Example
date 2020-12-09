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
        long a = System.nanoTime();
        logger.warn(variableValue);
    }
}
