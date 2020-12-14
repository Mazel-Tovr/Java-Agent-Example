package com.epam

import javassist.ClassPool
import org.slf4j.Logger
import org.slf4j.LoggerFactory

private val logger: Logger = LoggerFactory.getLogger("Main")

fun main() {
    println("Hello from  main app")

    val  testDoable = ImpleUse()

    val classPool = ClassPool.getDefault()

    testDoable.printInfo()

    testDoable.printInput("I don't care")

    testDoable.calculate(3,2)
}

private fun printer() {
    val printer = Printer()
    printer.printVariable()
    printer.printTest()
    printer.printConstant()
    printer.javaClass.getMethod("generatedMethod").invoke(printer)
    printer.javaClass.getMethod("generatedMethod2").invoke(printer)
}
