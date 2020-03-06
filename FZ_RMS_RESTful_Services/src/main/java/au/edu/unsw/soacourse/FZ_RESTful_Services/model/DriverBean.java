package au.edu.unsw.soacourse.FZ_RESTful_Services.model;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "lastName", "firstName", "driversLicenseNo", "address",
		"email" })
@XmlRootElement(name = "driver")
public class DriverBean {

	private String lastName;
	private String firstName;
	private String driversLicenseNo;
	private String address;
	private String email;
	private Map<String, String> map = new LinkedHashMap<String, String>();

	public DriverBean() {
		super();
	}

	public DriverBean(String lastName, String firstName,
			String driversLicenseNo, String address, String email) {
		super();
		this.lastName = lastName;
		this.firstName = firstName;
		this.driversLicenseNo = driversLicenseNo;
		this.address = address;
		this.email = email;
		mapBuild();
	}

	private void mapBuild() {
		map.put("lastName", lastName);
		map.put("firstName", firstName);
		map.put("driversLicenseNo", driversLicenseNo);
		map.put("address", address);
		map.put("email", email);
	}

	public String getlastName() {
		return lastName;
	}

	@XmlElement(name = "LastName")
	public void setlastName(String lastName) {
		this.lastName = lastName;
		mapBuild();
	}

	public String getfirstName() {
		return firstName;
	}

	@XmlElement(name = "FirstName")
	public void setfirstName(String firstName) {
		this.firstName = firstName;
		mapBuild();
	}

	public String getdriversLicenseNo() {
		return driversLicenseNo;
	}

	@XmlElement(name = "DriversLicenseNo")
	public void setdriversLicenseNo(String driversLicenseNo) {
		this.driversLicenseNo = driversLicenseNo;
		mapBuild();
	}

	public String getaddress() {
		return address;
	}

	@XmlElement(name = "Address")
	public void setaddress(String address) {
		this.address = address;
		mapBuild();
	}

	public String getemail() {
		return email;
	}

	@XmlElement(name = "Email")
	public void setemail(String email) {
		this.email = email;
		mapBuild();
	}

	public Map<String, String> getMap() {
		return map;
	}

}
