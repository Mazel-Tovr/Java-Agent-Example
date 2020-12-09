package com.epam

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.lang.Exception

private val logger: Logger = LoggerFactory.getLogger("Main")

fun main() {
    try {
        println("Hello main from app")
        // val workingWithCollections = WorkingWithCollections()
        // workingWithCollections.list.forEach { logger.info(it.toString()) }
        val printer = Printer()
        printer.printVariable()
        printer.printTest()
        printer.printConstant()
    }catch (ex: Exception){
        println(ex)
    }

}
