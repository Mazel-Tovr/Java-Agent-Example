package com.epam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ExtraClass {

    private Logger logger = LoggerFactory.getLogger(ExtraClass.class);

    private List<String> list = new ArrayList<>();

    public void addElement(String e) {
        list.add(e);
    }

    public void printList() {
        list.forEach(e -> logger.info(e));
    }

}
