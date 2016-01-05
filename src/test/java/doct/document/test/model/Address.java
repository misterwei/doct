package doct.document.test.model;

public class Address {
	private String code;
	private String address;
	
	public Address(String code, String address) {
		this.code = code;
		this.address = address;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
