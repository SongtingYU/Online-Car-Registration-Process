package au.edu.unsw.soacourse.gnafservice;

public class AddressResult implements Comparable {
	private String streetNum;
	private String buildingNum;
	private String streetNumSuffix;
	private String streetNumRange;
	private String streetName;
	private String streetType;
	private String suburb;
	private String state;
	private String postcode;
	private int score;

	public AddressResult(String streetNum, String buildingNum,
			String streetNumSuffix, String streetNumRange, String streetName,
			String streetType, String suburb, String state, String postcode,
			int score) {
		super();
		this.streetNum = streetNum;
		this.buildingNum = buildingNum;
		this.streetNumSuffix = streetNumSuffix;
		this.streetNumRange = streetNumRange;
		this.streetName = streetName;
		this.streetType = streetType;
		this.suburb = suburb;
		this.state = state;
		this.postcode = postcode;
		this.score = score;
	}

	public int getScore() {
		return score;
	}

	public String getStreetNum() {
		return streetNum;
	}

	public String getBuildingNum() {
		return buildingNum;
	}

	public String getStreetNumSuffix() {
		return streetNumSuffix;
	}

	public String getStreetNumRange() {
		return streetNumRange;
	}

	public String getStreetName() {
		return streetName;
	}

	public String getStreetType() {
		return streetType;
	}

	public String getSuburb() {
		return suburb;
	}

	public String getState() {
		return state;
	}

	public String getPostcode() {
		return postcode;
	}

	@Override
	public int compareTo(Object o) {
		return (score - ((AddressResult) o).getScore());
	}

	public String getReadableAddress() {
		return GNAFDatabase.getExactAddress(streetNum, buildingNum,
				streetNumSuffix, streetNumRange, streetName, streetType,
				suburb, state, postcode);
	}
}
