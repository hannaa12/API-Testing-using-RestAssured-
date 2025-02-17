package com.fdmgroup.pojos;

import java.util.List;

public class Manager {
	private int id;
	private double salary;
	private int age;
	private String name;
	private List<Staff> staffs;
	public int getId() {
		return id;
	}
	public double getSalary() {
		return salary;
	}
	public int getAge() {
		return age;
	}
	public String getName() {
		return name;
	}
	public List<Staff> getStaffs() {
		return staffs;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setStaffs(List<Staff> staffs) {
		this.staffs = staffs;
	}
	@Override
	public String toString() {
		return "Manager [id=" + id + ", salary=" + salary + ", age=" + age + ", name=" + name + ", staffs=" + staffs
				+ "]";
	}
	
}
