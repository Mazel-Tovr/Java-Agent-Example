package com.epam

import javassist.*
import javassist.expr.ExprEditor
import javassist.expr.MethodCall
import org.slf4j.LoggerFactory
import java.io.ByteArrayInputStream
import java.lang.instrument.ClassFileTransformer
import java.security.ProtectionDomain
import java.util.concurrent.atomic.AtomicInteger

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
//        logger.info("This class $clazzName was loaded. Count of classes ${classCounter.getAndIncrement()}")

        if (clazzName!!.startsWith("com.epam.Printer")) {

            val pool: ClassPool = ClassPool.getDefault()
            val ctClass = pool.makeClass(ByteArrayInputStream(classfileBuffer))

            /*region add profiler*/
            for (method in ctClass.declaredMethods) {
                method.run {
                    instrument(object : ExprEditor() {
                        override fun edit(expr: MethodCall) {
                            expr.replace("{ long a = System.nanoTime(); \$_ = \$proceed(\$\$); logger.info(\"Method worked \"+(System.nanoTime()-a));}")
                        }
                    })
                }
            }
            /*endregion*/

            /*region generate new method*/
            val ctMethod: CtMethod = CtNewMethod.make(
                """public void generatedMethod(){
                |logger.info("Hello from generated method");
                |}""".trimMargin(), ctClass
            )
            ctClass.addMethod(ctMethod)
            /*endregion*/

            /*region add field*/
//           val constantValue = ctClass.getField("constantValue")
//           val variableValue = ctClass.getField("variableValue")
            val fieldName = "extraClass";
            val ctField = CtField.make("private com.epam.ExtraClass $fieldName = new com.epam.ExtraClass();", ctClass)
            ctClass.addField(ctField)

            ctClass.declaredMethods.forEach {
                it.insertAfter("$fieldName.addElement(new Throwable().getStackTrace()[0].getMethodName());")
            }

            /*endregion*/

            val ctMethod2: CtMethod = CtNewMethod.make(
                """public void generatedMethod2(){
                |$fieldName.printList();
                |}""".trimMargin(), ctClass
            )
            ctClass.addMethod(ctMethod2)


            return ctClass.toBytecode()
        }

        return classfileBuffer!!
    }

    fun generateClass(classPool: ClassPool): CtClass {

        val generatedClass = classPool.makeClass("GeneratedClass")

        generatedClass.addField(
            CtField.make(
                "private org.slf4j.Logger logger = LoggerFactory.getLogger(GeneratedClass.class);",
                generatedClass
            )
        )
        generatedClass.addField(CtField.make("private Sting value = null;", generatedClass))

        generatedClass.addConstructor(
            CtNewConstructor.make(
                """
            public GeneratedClass(String value){
            this.value = value;
            }
        """.trimIndent(), generatedClass
            )
        )
        generatedClass.addMethod(
            CtMethod.make(
                """
            private void invokeGenMethod(){
                logger.info(value);
            }
        """.trimIndent(), generatedClass
            )
        )

        return generatedClass
    }

}
