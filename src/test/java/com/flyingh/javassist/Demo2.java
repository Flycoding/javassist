package com.flyingh.javassist;

import java.io.IOException;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.Modifier;
import javassist.NotFoundException;

import org.junit.Test;

public class Demo2 {
	/**
	 * <pre>
	 * package com.flyingh.javassist;
	 * 
	 * public class App{ 
	 * 		public static void main(String[] args) throws java.lang.NoSuchFieldException{
	 * 			System.out.println(System.class.getDeclaredField("sys").getName()); 
	 * 		} 
	 * }
	 * run:
	 * java  -Xbootclasspath/p:. com.flyingh.javassist.App
	 * output:
	 * sys
	 * </pre>
	 * @throws NotFoundException
	 * @throws CannotCompileException
	 * @throws IOException
	 */
	@Test
	public void test() throws NotFoundException, CannotCompileException,
			IOException {
		CtClass ctClass = ClassPool.getDefault().get("java.lang.System");
		CtField ctField = new CtField(CtClass.intType, "sys", ctClass);
		ctField.setModifiers(Modifier.PUBLIC);
		ctClass.addField(ctField);
		ctClass.writeFile();
	}
}
