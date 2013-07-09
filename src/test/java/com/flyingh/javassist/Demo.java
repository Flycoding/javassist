package com.flyingh.javassist;

import java.io.FileOutputStream;
import java.io.IOException;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Loader;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.Translator;

import org.junit.Test;

class Hello {
	public void say() {
		System.out.println("Hello world!!!");
	}
}

class A {

}

class B {

}

class Foo {
	public static void main(String[] args) {
		if (args.length < 2) {
			return;
		}
		System.out.println(args[0] + "-->" + args[1]);
	}
}

public class Demo {
	@Test
	public void test3() throws Throwable {
		Loader loader = new Loader(ClassPool.getDefault());
		loader.addTranslator(ClassPool.getDefault(), new Translator() {

			@Override
			public void start(ClassPool pool) throws NotFoundException,
					CannotCompileException {
				System.out
						.println("Demo.test3().new Translator() {...}.start()");
			}

			@Override
			public void onLoad(ClassPool pool, String classname)
					throws NotFoundException, CannotCompileException {
				System.out
						.println("Demo.test3().new Translator() {...}.onLoad()");
				pool.get(classname).setModifiers(Modifier.PUBLIC);
			}
		});
		loader.run("com.flyingh.javassist.Foo", new String[] { "Hello", "Foo" });
	}

	@Test
	public void test2() throws NotFoundException, CannotCompileException,
			ClassNotFoundException {
		ClassPool cp = ClassPool.getDefault();
		CtClass a = cp.get("com.flyingh.javassist.A");
		a.setSuperclass(cp.get("com.flyingh.javassist.B"));
		Class<?> aClass = new Loader(cp).loadClass("com.flyingh.javassist.A");
		System.out.println(aClass.getSuperclass());
	}

	@Test
	public void test() throws NotFoundException, CannotCompileException,
			InstantiationException, IllegalAccessException, IOException {
		ClassPool pool = ClassPool.getDefault();
		CtClass ctClass = pool.get("com.flyingh.javassist.Hello");
		CtMethod method = ctClass.getDeclaredMethod("say");
		method.insertBefore("System.out.println(\"Hello Javassist!!!\");");
		ctClass.writeFile();
		save(ctClass.toBytecode());
		Class<?> cls = ctClass.toClass();
		System.out.println(cls.getName());
		Hello newInstance = (Hello) cls.newInstance();
		newInstance.say();
	}

	private void save(byte[] bytecode) throws IOException {
		FileOutputStream os = new FileOutputStream("C:\\Hello.class");
		os.write(bytecode);
		os.close();
	}
}
