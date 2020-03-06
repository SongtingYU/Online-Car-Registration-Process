package au.edu.unsw.soacourse.gnafservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class GNAFDatabase {
	final public static String NO_RESULT = "";

	public static String getPostcode(String suburb, String state) {
		Connection connection = null;
		String postcode = "";

		try {

			Class.forName("org.sqlite.JDBC");
			connection = DriverManager
					.getConnection("jdbc:sqlite::resource:gnaf.db");

			Statement statement = connection.createStatement();

			StringBuilder sb = new StringBuilder();
			sb.append("select postcode from states left join localities on states.id = localities.in_state ");
			sb.append("where localities.name='" + suburb
					+ "' collate nocase and ");

			if (state.length() == 3) {
				sb.append("states.abbrev='" + state + "' collate nocase;");
			} else {
				sb.append("states.name='" + state + "' collate nocase;");
			}

			ResultSet rs = statement.executeQuery(sb.toString());

			if (!rs.next()) {
				if (connection != null) {
					connection.close();
				}
				return NO_RESULT;
			}

			postcode = rs.getString("postcode");

		} catch (SQLException e1) {
			try {
				e1.printStackTrace();
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e2) {
			}

			return NO_RESULT;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return postcode;
	}

	public static List<AddressResult> getSimilarAddresses(String streetNum,
			String buildingNum, String streetNumSuffix, String streetNumRange,
			String streetName, String streetType, String suburb, String state,
			String postcode) {
		Connection connection = null;
		List<AddressResult> similarAddresses = new ArrayList<AddressResult>();

		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager
					.getConnection("jdbc:sqlite::resource:gnaf.db");

			StringBuilder sb = new StringBuilder();

			sb.append("select number_first, lot_number, flat_number, level_number, number_last, num1_suffix, streets.name as street_name, streets.type_code as street_type, localities.name as suburb, states.abbrev as state, localities.postcode as postcode"
					+ " from addresses left join localities on addresses.locality = localities.id left join streets on addresses.street = streets.id left join states on localities.in_state = states.id where ");
			sb.append(" addresses.number_first like ? or ");
			sb.append("lower(streets.name) like ? or ");
			sb.append("lower(streets.type_code) or ?");

			List<String> queryArgs = new ArrayList<String>();

			queryArgs.add(streetNum);
			queryArgs.add(streetName);
			queryArgs.add(streetType);

			if (!buildingNum.isEmpty()) {
				sb.append(" or ");
				sb.append("(addresses.lot_number like ? or ");
				sb.append("addresses.flat_number like ? or ");
				sb.append("addresses.level_number like ?)");
				queryArgs.add(buildingNum);
				queryArgs.add(buildingNum);
				queryArgs.add(buildingNum);
			}

			if (!streetNumRange.isEmpty()) {
				sb.append(" or ");
				sb.append("addresses.number_last like ?");
				queryArgs.add(streetNumRange);
			}

			if (!streetNumSuffix.isEmpty()) {
				sb.append(" or ");
				sb.append("lower(addresses.num1_suffix) like ?");
				queryArgs.add(streetNumSuffix);
			}

			PreparedStatement statement = connection.prepareStatement(sb
					.toString());
			for (int i = 1; i <= queryArgs.size(); i++) {
				statement.setString(i, "%"
						+ queryArgs.get(i - 1).toLowerCase().trim() + "%");
			}

			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				String resultStreetNum = rs.getString("number_first");

				if (resultStreetNum == null) {
					resultStreetNum = "";
				}
				
				String resultBuildingNum = "";
				if (rs.getString("lot_number") != null) {
					resultBuildingNum = rs.getString("lot_number");
				} else if (rs.getString("flat_number") != null) {
					resultBuildingNum = rs.getString("flat_number");
				} else if (rs.getString("level_number") != null) {
					resultBuildingNum = rs.getString("level_number");
				}

				String resultStreetNumSuffix = "";
				if (rs.getString("num1_suffix") != null) {
					resultBuildingNum = rs.getString("num1_suffix");
				}

				String resultStreetNumRange = "";
				if (rs.getString("number_last") != null) {
					resultStreetNumRange = rs.getString("number_last");
				}

				String resultStreetName = rs.getString("street_name");
				String resultStreetType = rs.getString("street_type");
				String resultSuburb = rs.getString("suburb");
				String resultState = rs.getString("state");
				String resultPostcode = rs.getString("postcode");

				int score = 0;

				
				score += StringUtils.getLevenshteinDistance(resultStreetNum,
						streetNum);

				score += StringUtils.getLevenshteinDistance(resultStreetName,
						streetName);

				score += StringUtils.getLevenshteinDistance(resultStreetType,
						streetType);

				if (!buildingNum.isEmpty()) {
					score += StringUtils.getLevenshteinDistance(
							resultBuildingNum, buildingNum);
				}

				if (!streetNumSuffix.isEmpty()) {
					score += StringUtils.getLevenshteinDistance(
							resultStreetNumSuffix, streetNumSuffix);
				}

				if (!streetNumRange.isEmpty()) {
					score += StringUtils.getLevenshteinDistance(
							resultStreetNumRange, streetNumRange);
				}

				if (!suburb.isEmpty()) {

					score += StringUtils.getLevenshteinDistance(resultSuburb,
							suburb);
				}

				if (!state.isEmpty()) {
					score += StringUtils.getLevenshteinDistance(resultState,
							state);
				}

				if (!postcode.isEmpty()) {
					score += StringUtils.getLevenshteinDistance(resultPostcode,
							postcode);
				}

				similarAddresses.add(new AddressResult(resultStreetNum,
						resultBuildingNum, resultStreetNumSuffix,
						resultStreetNumRange, resultStreetName,
						resultStreetType, resultSuburb, resultState,
						resultPostcode, score));
			}

		} catch (SQLException e1) {
			try {
				e1.printStackTrace();
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e2) {
			}

			return similarAddresses;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return similarAddresses;
	}

	public static String getExactAddress(String streetNum, String buildingNum,
			String streetNumSuffix, String streetNumRange, String streetName,
			String streetType, String suburb, String state, String postcode) {
		Connection connection = null;
		String readableAddress = "";

		try {

			Class.forName("org.sqlite.JDBC");
			connection = DriverManager
					.getConnection("jdbc:sqlite::resource:gnaf.db");

			StringBuilder sb = new StringBuilder();

			sb.append("select streets.name as street_name, streets.type_code as street_type, localities.name as suburb, states.abbrev as state, localities.postcode as postcode"
					+ " from addresses left join localities on addresses.locality = localities.id left join streets on addresses.street = streets.id left join states on localities.in_state = states.id where ");
			sb.append("addresses.number_first = ? and ");
			sb.append("addresses.postcode = ? and ");
			sb.append("streets.name = ? collate nocase and ");
			sb.append("streets.type_code = ? collate nocase and ");
			sb.append("localities.name = ? collate nocase and ");

			if (state.length() == 3) {
				sb.append("states.abbrev = ? collate nocase");
			} else {
				sb.append("states.name = ? collate nocase");
			}

			List<String> queryArgs = new ArrayList<String>();

			queryArgs.add(streetNum);
			queryArgs.add(postcode);
			queryArgs.add(streetName);
			queryArgs.add(streetType);
			queryArgs.add(suburb);
			queryArgs.add(state);

			if (!buildingNum.isEmpty()) {
				sb.append(" and ");
				sb.append("(addresses.lot_number = ? or ");
				sb.append("addresses.flat_number = ? or ");
				sb.append("addresses.level_number = ?)");
				queryArgs.add(buildingNum);
				queryArgs.add(buildingNum);
				queryArgs.add(buildingNum);
			}

			if (!streetNumRange.isEmpty()) {
				sb.append(" and ");
				sb.append("addresses.number_last = ?");
				queryArgs.add(streetNumRange);
			}

			if (!streetNumSuffix.isEmpty()) {
				sb.append(" and ");
				sb.append("addresses.num1_suffix = ? collate nocase");
				queryArgs.add(streetNumSuffix);
			}

			PreparedStatement statement = connection.prepareStatement(sb
					.toString());

			for (int i = 1; i <= queryArgs.size(); i++) {
				statement.setString(i, queryArgs.get(i - 1));
			}

			ResultSet rs = statement.executeQuery();

			if (!rs.next()) {
				if (connection != null) {
					connection.close();
				}
				return NO_RESULT;
			}

			readableAddress = createReadableAddress(streetNum, buildingNum,
					streetNumSuffix, streetNumRange,
					rs.getString("street_name"), rs.getString("street_type"),
					rs.getString("suburb"), rs.getString("state"),
					rs.getString("postcode"));
		} catch (SQLException e1) {
			try {
				e1.printStackTrace();
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e2) {
			}

			return NO_RESULT;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return readableAddress;
	}

	public static String createReadableAddress(String streetNum,
			String buildingNum, String streetNumSuffix, String streetNumRange,
			String streetName, String streetType, String suburb, String state,
			String postcode) throws SQLException {

		StringBuilder sb = new StringBuilder();
		if (!buildingNum.equals("") && !streetNum.isEmpty()) {
			sb.append(buildingNum + "/");
		}

		sb.append(streetNum);

		if (!streetNumSuffix.equals("")) {
			sb.append(streetNumSuffix);
		}

		if (!streetNumRange.equals("")) {
			sb.append("-" + streetNumRange);
		}

		sb.append(" ");
		sb.append(streetName);
		sb.append(" ");
		sb.append(streetType);
		sb.append(", ");
		sb.append(suburb);
		sb.append(", ");
		sb.append(state);
		sb.append(", ");
		sb.append(postcode);

		return sb.toString();
	}
}
