package vn.com.nst.model;

import java.io.Serializable;

public class Employee implements Serializable{
	int id;
	String name;
	String phoneNumber;
	byte [] avarta;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public byte[] getAvarta() {
		return avarta;
	}
	public void setAvarta(byte[] avarta) {
		this.avarta = avarta;
	}
	public Employee(int id, String name, String phoneNumber, byte[] avarta) {
		super();
		this.id = id;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.avarta = avarta;
	}
	public Employee() {
		super();
	}
	
}
