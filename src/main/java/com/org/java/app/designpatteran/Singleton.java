package com.org.java.app.designpatteran;

import java.io.Serializable;

public class Singleton implements Serializable,Cloneable
{

	private static final long serialVersionUID = 1L;
	private static Singleton singleton;
	
	private Singleton() {
		
	}
	@SuppressWarnings("unused")
	public static Singleton getInstance() {
		if(singleton==null) {
			singleton=new Singleton();
		}
		return singleton;
	}
	@Override
	public Singleton clone() throws CloneNotSupportedException {
		return (Singleton) super.clone();
	}
}
