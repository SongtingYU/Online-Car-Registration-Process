package au.edu.unsw.soacourse.gnafservice;

import java.util.HashMap;
import java.util.Map;

public class AddressUtils {
	private static final Map<String, String> STREET_TYPE_MAPPINGS = new HashMap<String, String>() {
		{
			put("st", "STREET");
			put("ave", "AVENUE");
			put("rd", "ROAD");
			put("cct", "CIRCUIT");
			put("cl", "CLOSE");
			put("gr", "GROVE");
			put("pl", "PLACE");
			put("dr", "DRIVE");
			put("lane", "LANE");
			put("pl", "PLACE");
			put("gln", "GLEN");
			put("cres", "CRESCENT");
			put("ct", "COURT");
			put("trl", "TRAIL");
			put("firetrail", "FIRETRAIL");
			put("ter", "TERRACE");
			put("blvd", "BOULEVARD");
			put("gdns", "GARDENS");
		}
	};
	

	public static String getStreetType(String street) {

		if (STREET_TYPE_MAPPINGS.get(street.toLowerCase()) != null) {
			return STREET_TYPE_MAPPINGS.get(street.toLowerCase());
		}

		for (String streetType : STREET_TYPE_MAPPINGS.values()) {
			if (streetType.equalsIgnoreCase(street)) {
				return streetType;
			}
		}

		for (String streetTypeAbbrev : STREET_TYPE_MAPPINGS.keySet()) {
			if (streetTypeAbbrev.contains(street.toLowerCase())
					|| street.toLowerCase().contains(streetTypeAbbrev)) {
				return STREET_TYPE_MAPPINGS.get(streetTypeAbbrev);
			}
		}

		for (String streetType : STREET_TYPE_MAPPINGS.values()) {
			if (streetType.contains(street.toUpperCase())
					|| street.toUpperCase().contains(streetType)) {
				return streetType;
			}
		}

		return "";
	}
}
