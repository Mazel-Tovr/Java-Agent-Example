package com.epam

import javassist.*
import org.slf4j.*
import java.io.ByteArrayInputStream
import java.lang.Exception
import java.lang.instrument.*
import java.security.*
import java.util.concurrent.atomic.*

class SimpleClassTransformer : ClassFileTransformer {

    private val logger = LoggerFactory.getLogger(SimpleClassTransformer::class.java)

    private val classCounter = AtomicInteger()

    private val countProjectClasses = AtomicInteger()

    override fun transform(
        loader: ClassLoader?,
        className: String?,
        classBeingRedefined: Class<*>?,
        protectionDomain: ProtectionDomain?,
        classfileBuffer: ByteArray?
    ): ByteArray {

        val clazzName = className?.replace("/".toRegex(), ".")
//        if (!className!!.startsWith("com.epam")) return classfileBuffer!!

//        logger.info("This class $clazzName was loaded. Count of classes ${classCounter.getAndIncrement()}")


        if (clazzName!!.startsWith("com.epam.Printer")) {
            logger.info(className)
            val pool: ClassPool = ClassPool.getDefault()
            /// val ctClass = pool.get(clazzName)
            val ctClass = pool.makeClass(ByteArrayInputStream(classfileBuffer))
            ctClass.declaredMethods.forEach { logger.info(it.toString()) }

//            ctClass.declaredMethods.forEach {
//                it.insertBefore("logger.warn(\"Inset into byte code before\");")
//                it.insertAfter("logger.warn(\"Inset into byte code after\");")
//            }
//

            ctClass.declaredMethods.forEach {
                it.insertBefore("long a = System.nanoTime();logger.warn(\"Inset into byte code before \"+ a);")
                it.insertAfter(
                    "long b = System.nanoTime() - a; logger.warn(\"Method worked \" + b);"
                )
            }

//            for (method in ctClass.declaredMethods) {
//                method.setBody("""{
//                    |long a = System.nanoTime();
//                    |System.out.println(\"I am new body eeeeee\");
//                    |System.out.println(\"Method worked\" + (System.nanoTime() - a));
//                    |}""".trimMargin())
////                method.insertBefore("long a = System.nanoTime();")
////                method.insertAfter("System.out.println(\"Method worked\" + (System.nanoTime() - a));")
//            }
            ctClass.fields.forEach {
                logger.info(it.toString())
            }
            val a = ctClass.getField("variableValue");

            logger.error(a.toString())
//            ctClass.let { clazz ->
//                logger.info("\n Class $clazzName contains fields = ${clazz.fields};\n Methods = ${clazz.methods};\n Count of project classes ${countProjectClasses.getAndIncrement()}")
//            }
            return ctClass.toBytecode()
        }


        return classfileBuffer!!
    }

}
