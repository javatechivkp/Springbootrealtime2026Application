package com.org.java.app.designpatteran;

public enum EnumSingletonTest {
	
	INSTANCE;
	
	public void perform() {
		System.out.println("This is Enum time Singleton::"+this.hashCode());
	}

}