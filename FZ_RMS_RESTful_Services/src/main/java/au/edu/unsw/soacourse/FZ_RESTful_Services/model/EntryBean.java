package au.edu.unsw.soacourse.FZ_RESTful_Services.model;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "_rid", "registrationNumber", "driver",
		"registrationValidTill" })
@XmlRootElement(name = "Entry")
public class EntryBean {

	private String _rid;
	private String registrationNumber;
	private DriverBean driver;
	private String registrationValidTill;
	private Map<String, String> map = new LinkedHashMap<String, String>();

	public EntryBean() {
		super();
	}

	public EntryBean(String _rid, String registrationNumber, DriverBean driver,
			String registrationValidTill) {
		super();
		this._rid = _rid;
		this.registrationNumber = registrationNumber;
		this.driver = driver;
		this.registrationValidTill = registrationValidTill;
		mapBuild();
	}

	private void mapBuild() {
		map.put("_rid", _rid);
		map.put("registrationNumber", registrationNumber);
		map.put("Driver", "$");
		map.put("registrationValidTill", registrationValidTill);
	}

	public String get_rid() {
		return _rid;
	}

	@XmlElement(name = "_rid")
	public void set_rid(String _rid) {
		this._rid = _rid;
		mapBuild();
	}

	public String getregistrationNumber() {
		return registrationNumber;
	}

	@XmlElement(name = "RegistrationNumber")
	public void setregistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
		mapBuild();
	}

	public DriverBean getDriver() {
		return driver;
	}

	@XmlElement(name = "Driver")
	public void setDriver(DriverBean driver) {
		this.driver = driver;
	}

	public String getregistrationValidTill() {
		return registrationValidTill;
	}

	@XmlElement(name = "RegistrationValidTill")
	public void setregistrationValidTill(String registrationValidTill) {
		this.registrationValidTill = registrationValidTill;
		mapBuild();
	}

	public Map<String, String> getMap() {
		return map;
	}

}
