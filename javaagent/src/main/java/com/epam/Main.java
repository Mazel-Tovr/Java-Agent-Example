package com.epam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.Instrumentation;

public class Main {
    private final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void premain(String args, Instrumentation instrumentation) {
        try {
            instrumentation.addTransformer(new SimpleClassTransformer());
            // instrumentation.addTransformer(new JavaC());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
