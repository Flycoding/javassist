package com.flyingh.javassist;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.NotFoundException;

public class MyClassLoader extends ClassLoader {
	private ClassPool cp;

	/**
	 * <pre>
	 *  C:\com
	 * 		└─flyingh
	 *    		└─javassist
	 *           	Bar.class
	 *  package com.flyingh.javassist;
	 * 
	 * 	public class Bar{
	 * 		public static void main(String[] args){
	 * 			if(args.length<2){
	 * 				return;
	 * 			}
	 * 			System.out.println(args[0]+"-->"+args[1]);
	 * 		}
	 * }
	 * </pre>
	 * 
	 * @throws NotFoundException
	 */
	public MyClassLoader() throws NotFoundException {
		super();
		cp = new ClassPool();
		cp.insertClassPath("C:\\");
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		try {
			byte[] bytecode = cp.get(name).toBytecode();
			return defineClass(name, bytecode, 0, bytecode.length);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CannotCompileException e) {
			e.printStackTrace();
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		return super.findClass(name);
	}

	public static void main(String[] args) throws ClassNotFoundException,
			NotFoundException, IllegalArgumentException, SecurityException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		Class<?> loadClass = new MyClassLoader()
				.loadClass("com.flyingh.javassist.Bar");
		loadClass.getDeclaredMethod("main", String[].class).invoke(loadClass,
				(Object) new String[] { "Hello", "World!!!" });
	}
}
