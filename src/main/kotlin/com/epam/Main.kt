package com.epam

import org.slf4j.Logger
import org.slf4j.LoggerFactory

private val logger: Logger = LoggerFactory.getLogger("Main")

fun main() {
    try {
        println("Hello from  main app")
        val printer = Printer()
        printer.printVariable()
        printer.printTest()
        printer.printConstant()
        printer.javaClass.getMethod("generatedMethod").invoke(printer)
        printer.javaClass.getMethod("generatedMethod2").invoke(printer)
    } catch (ex: Exception) {
        println(ex)
    }

}
