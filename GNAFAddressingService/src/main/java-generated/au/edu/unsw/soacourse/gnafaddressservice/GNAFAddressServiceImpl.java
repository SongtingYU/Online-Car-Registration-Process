package au.edu.unsw.soacourse.gnafaddressservice;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jws.WebService;

import au.edu.unsw.soacourse.gnafservice.AddressResult;
import au.edu.unsw.soacourse.gnafservice.AddressUtils;
import au.edu.unsw.soacourse.gnafservice.GNAFDatabase;

@WebService(endpointInterface = "au.edu.unsw.soacourse.gnafaddressservice.GNAFAddressService")
public class GNAFAddressServiceImpl implements GNAFAddressService {
	// [a-zA-Z ]*?([0-9]+)*?\/?[, ]*?([0-9]+)-?([0-9]+)?([ABCDLRWH])? +?([a-zA-Z
	// ]+?) ([a-zA-Z]+)[ ,]+([a-zA-Z ]+)[ ,]+([a-zA-Z ]+)[ ,]+([0-9]{4})
	final static private String EXACT_ADDRESS_FORMAT = "[a-zA-Z ]*?([0-9]+)*?/?[, ]*?([0-9]+)-?([0-9]+)?([ABCDLRWH])? +?([a-zA-Z ]+?) ([a-zA-Z]+)[ ,]+([a-zA-Z ]+)[ ,]+([a-zA-Z ]+)[ ,]+([0-9]{4}) *";

	// [a-zA-Z ]*?([0-9]+)*?\/?[, ]*?([0-9]+)-?([0-9]+)?([ABCDLRWH])? +?([a-zA-Z
	// ]+?) ([a-zA-Z]+)[ ,]*([a-zA-Z ]+)?[ ,]*([a-zA-Z ]+)?[ ,]*([0-9]{4})?
	final static private String SIMILAR_ADDRESS_FORMAT = "[a-zA-Z ]*?([0-9]+)*?/?[, ]*?([0-9]+)-?([0-9]+)?([ABCDLRWH])? +?([a-zA-Z ]+?) ([a-zA-Z]+)[ ,]*([a-zA-Z ]+)?[ ,]*([a-zA-Z ]+)?[ ,]*([0-9]{4})? *";

	@SuppressWarnings("unchecked")
	@Override
	public SimilarAddressesInFileResponse similarAddressesInFile(
			SimilarAddressesInFileRequest parameters)
			throws SimilarAddressesInFileFaultMsg {
		String input = parameters.getAddress();
		Pattern pattern = Pattern.compile(SIMILAR_ADDRESS_FORMAT);
		Matcher matcher = pattern.matcher(input);

		if (!matcher.matches()) {
			throw new SimilarAddressesInFileFaultMsg("Invalid address format");
		}

		String streetNum = matcher.group(2);
		String streetName = matcher.group(5);
		String streetType = AddressUtils.getStreetType(matcher.group(6));
		String suburb = (matcher.group(7) == null) ? "" : matcher.group(7);
		String state = (matcher.group(8) == null) ? "" : matcher.group(8);
		String postcode = (matcher.group(9) == null) ? "" : matcher.group(9);
		String buildingNum = (matcher.group(1) == null) ? "" : matcher.group(1);
		String streetNumRange = (matcher.group(3) == null) ? "" : matcher
				.group(3);
		String streetNumSuffix = (matcher.group(4) == null) ? "" : matcher
				.group(4);

		List<AddressResult> similarAddresses = GNAFDatabase
				.getSimilarAddresses(streetNum, buildingNum, streetNumSuffix,
						streetNumRange, streetName, streetType, suburb, state,
						postcode);

		Collections.sort(similarAddresses);
		int numToReturn = (3 > similarAddresses.size()) ? similarAddresses
				.size() : 3;
		List<AddressResult> topSimilarities = similarAddresses.subList(0,
				numToReturn);

		StringBuilder sb = new StringBuilder();
		sb.append(System.lineSeparator());
		for (AddressResult address : topSimilarities) {
			sb.append(address.getReadableAddress());
			sb.append(System.lineSeparator());
		}

		SimilarAddressesInFileResponse response = new SimilarAddressesInFileResponse();
		response.setReturnData(sb.toString());

		return response;
	}

	@Override
	public AddressExistsInFileResponse addressExistsInFile(
			AddressExistsInFileRequest parameters)
			throws AddressExistsInFileFaultMsg {
		String input = parameters.getAddress();
		Pattern pattern = Pattern.compile(EXACT_ADDRESS_FORMAT);
		Matcher matcher = pattern.matcher(input);

		ServiceFaultType fault = new ServiceFaultType();

		if (!matcher.matches()) {
			String msg = "Invalid address format";
			String code = "addressExistsFault";
			fault.setErrcode(code);
			fault.setErrtext(msg);
			throw new AddressExistsInFileFaultMsg(msg, fault);
		}

		String streetNum = matcher.group(2);
		String streetName = matcher.group(5);
		String streetType = AddressUtils.getStreetType(matcher.group(6));
		String suburb = matcher.group(7);
		String state = matcher.group(8);
		String postcode = matcher.group(9);

		String buildingNum = (matcher.group(1) == null) ? "" : matcher.group(1);
		String streetNumRange = (matcher.group(3) == null) ? "" : matcher
				.group(3);
		String streetNumSuffix = (matcher.group(4) == null) ? "" : matcher
				.group(4);

		String exactAddress = GNAFDatabase.getExactAddress(streetNum,
				buildingNum, streetNumSuffix, streetNumRange, streetName,
				streetType, suburb, state, postcode);

		AddressExistsInFileResponse response = new AddressExistsInFileResponse();

		if (exactAddress.equals(GNAFDatabase.NO_RESULT)) {
			response.setReturnData("NOT FOUND");
		} else {
			response.setReturnData(exactAddress);
		}

		return response;
	}

	@Override
	public ReturnPostcodeInfoResponse returnPostcodeInfo(
			ReturnPostcodeInfoRequest parameters)
			throws ReturnPostcodeInfoFaultMsg {
		String state = parameters.getState();
		String suburb = parameters.getSuburb();

		if (state.isEmpty()) {
			throw new ReturnPostcodeInfoFaultMsg("No state provided");
		}

		Pattern pattern = Pattern.compile("[0-9]+");
		Matcher matcher = pattern.matcher(state);
		if (matcher.find()) {
			throw new ReturnPostcodeInfoFaultMsg(
					"State contains non characters");
		}

		if (suburb.isEmpty()) {
			throw new ReturnPostcodeInfoFaultMsg("No suburb provided");
		}

		matcher = pattern.matcher(suburb);
		if (matcher.find()) {
			throw new ReturnPostcodeInfoFaultMsg(
					"Suburb contains non characters");
		}

		ReturnPostcodeInfoResponse response = new ReturnPostcodeInfoResponse();

		String postcode = GNAFDatabase.getPostcode(suburb, state);

		if (postcode.isEmpty()) {
			response.setReturnData("No postcode found");
		} else {
			response.setReturnData(postcode);
		}

		return response;
	}
}