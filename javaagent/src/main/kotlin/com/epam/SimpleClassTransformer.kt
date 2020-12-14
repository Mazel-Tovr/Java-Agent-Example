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

        val clazzName = className!!.replace("/".toRegex(), ".")

        val pool: ClassPool = ClassPool.getDefault()

        if (clazzName.startsWith("com.epam.Printer")) {

            val ctClass = pool.makeClass(ByteArrayInputStream(classfileBuffer))


            for (method in ctClass.declaredMethods) {
                method.run {
                    instrument(object : ExprEditor() {
                        override fun edit(expr: MethodCall) {
                            expr.replace("{ long a = System.nanoTime(); \$_ = \$proceed(\$\$); logger.info(\"Method worked \"+(System.nanoTime()-a));}")
                        }
                    })
                }
            }

            ctClass.addMethod(
                CtNewMethod.make(
                    """public void generatedMethod(){
                |logger.info("Hello from generated method");
                |}""".trimMargin(), ctClass
                )
            )

            val fieldName = "extraClass";
            val ctField = CtField.make("private com.epam.ExtraClass $fieldName = new com.epam.ExtraClass();", ctClass)
            ctClass.addField(ctField)

            ctClass.declaredMethods.forEach {
                it.insertAfter("$fieldName.addElement(\"${it.name}\");")
            }

            ctClass.addMethod(
                CtNewMethod.make(
                    """public void generatedMethod2(){
                |$fieldName.printList();
                |}""".trimMargin(), ctClass
                )
            )

            return ctClass.toBytecode()
        }

        if (clazzName.startsWith("com.epam.ImpleUse")) {

            val clazz = generateTestClass(pool).toClass(loader, protectionDomain)//adding class to class path

            val ctClass = pool.makeClass(ByteArrayInputStream(classfileBuffer))

            ctClass.constructors.first().setBody(
                """{
                |this.doable = new ${clazz.name}();
                |}
            """.trimMargin()
            )

            return ctClass.toBytecode()
        }

        return classfileBuffer!!
    }

    fun generateTestClass(classPool: ClassPool): CtClass {

        val generatedClass = classPool.makeClass("DoableGeneratedImpl")
        generatedClass.interfaces = arrayOf(classPool.get("com.epam.IDoable"))
        generatedClass.addField(
            CtField.make(
                "private org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DoableGeneratedImpl.class);",
                generatedClass
            )
        )
        generatedClass.addMethod(
            CtMethod.make(
                """public void printInfo() {
            |logger.info("Hello from " + DoableGeneratedImpl.class);
            |}
        """.trimMargin(), generatedClass
            )
        )

        generatedClass.addMethod(
            CtMethod.make(
                """public int calculate(int a, int b) {
            | return a * b;
            | }
        """.trimMargin(), generatedClass
            )
        )

        generatedClass.addMethod(
            CtMethod.make(
                """public void printInput(String input) {
            |logger.info("Input from generated class: " + input);
            |}
        """.trimMargin(), generatedClass
            )
        )
        return generatedClass
    }

}
