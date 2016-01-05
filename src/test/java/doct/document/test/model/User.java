package doct.document.test.model;

import java.util.Date;

public class User {
	private String name;
	private int age;
	private Address address;
	private String mobile;
	private Date birthday;
	
	public User(String name, int age, Date birthday, String mobile) {
		this.name = name;
		this.age = age;
		this.birthday = birthday;
		this.mobile = mobile;
	}
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
}
