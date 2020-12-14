package com.epam;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Arrays;

public class JavaC implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

        String clazzName = className.replaceAll("/", ".");
        ClassPool classPool = ClassPool.getDefault();

        if (clazzName.startsWith("com.epam.Printer")) {

            try {
                CtClass ctClass = classPool.get(className);

                Arrays.stream(ctClass.getDeclaredMethods()).forEach(e->{
                    try {
                        e.instrument(new ExprEditor(){
                            @Override
                            public void edit(MethodCall e) throws CannotCompileException {
                               e.replace("{ System.out.println(\"Hello\"); $_ = $proceed($$); logger.warn($_); }");
                            }
                        });
                    } catch (CannotCompileException cannotCompileException) {
                        cannotCompileException.printStackTrace();
                    }

                });
                return ctClass.toBytecode();

            } catch (NotFoundException | CannotCompileException | IOException e) {
                e.printStackTrace();
            }

        }

        if (clazzName.startsWith("com.epam.TestDoable")){
            CtClass ctClass = new SimpleClassTransformer().generateTestClass(classPool);
            try {
                ctClass.toClass(loader,protectionDomain);
            } catch (CannotCompileException e) {
                e.printStackTrace();
            }
        }


        return classfileBuffer;
    }
}
