package com.rpc.factory.protocol;

public class ValueTypes {

	private Class<?> clazz;
	private String value;
	
	public ValueTypes() {
		super();
	}
	public ValueTypes(Class<?> clazz, String value) {
		super();
		this.clazz = clazz;
		this.value = value;
	}
	public Class<?> getClazz() {
		return clazz;
	}
	public String getValue() {
		return value;
	}
	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
