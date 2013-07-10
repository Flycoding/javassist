package com.flyingh.javassist;

import java.io.IOException;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

import org.junit.Test;

class Point {
	private int x;
	private int y;

	public void move(int dx, int dy) {
		x += dx;
		y += dy;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}

public class Demo3 {

	@Test
	public void test() throws NotFoundException, CannotCompileException,
			IOException {
		CtClass ctClass = ClassPool.getDefault().get(
				"com.flyingh.javassist.Point");
		CtMethod ctMethod = ctClass.getDeclaredMethod("move");
		ctMethod.insertBefore("{System.out.println($1);System.out.println($2);}");
		ctClass.writeFile();
	}
}
