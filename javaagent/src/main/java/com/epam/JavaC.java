package com.epam;

import javassist.*;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Arrays;

public class JavaC implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

        String clazzName = className.replaceAll("/", ".");
//        if (!className!!.startsWith("com.epam")) return classfileBuffer!!

//        logger.info("This class $clazzName was loaded. Count of classes ${classCounter.getAndIncrement()}")


        if (clazzName.startsWith("com.epam.Printer")) {

            ClassPool classPool = ClassPool.getDefault();
            try {
                CtClass ctClass = classPool.get(className);
//                Arrays.stream(ctClass.getDeclaredMethods()).forEach(e ->
//                        {
//                            try {
//                                e.insertBefore("long a = System.nanoTime();");
//                                e.insertAfter("logger.warn(\"Method worked \" + (System.nanoTime() - a));");
//                            } catch (CannotCompileException cannotCompileException) {
//                                cannotCompileException.printStackTrace();
//                            }
//                        }
//                );

                for (CtMethod declaredMethod : ctClass.getDeclaredMethods()) {
                    declaredMethod.insertBefore("long a = System.nanoTime();");
                    declaredMethod.insertAfter("logger.warn(\"Method worked \" + (System.nanoTime() - a));");
                }

                return ctClass.toBytecode();

            } catch (NotFoundException | CannotCompileException | IOException e) {
                e.printStackTrace();
            }

        }
        return classfileBuffer;
    }
}
