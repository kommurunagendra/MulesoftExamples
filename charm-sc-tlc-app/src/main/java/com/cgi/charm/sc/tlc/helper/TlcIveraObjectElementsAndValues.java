package com.cgi.charm.sc.tlc.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Component;

import com.cgi.charm.cdm.tlc.Parameter;
import com.cgi.charm.sc.tlc.constants.TlcConstants;

/**
 * @author pavan.chenna
 *
 */
@Component
public class TlcIveraObjectElementsAndValues {

	private List<String> indexElementNames;

	private List<String> indexElementValues;

	public List<String> getIndexElementNames() {
		return indexElementNames;
	}

	public void setIndexElementNames(String indexElementNames) {
		String[] names = indexElementNames.substring(1,
				indexElementNames.length() - 1).split("\",\"");
		names[0]=names[0].split("\"")[1];
		this.indexElementNames = Arrays.asList(names);
	}

	public List<String> getIndexElementValues() {
		return indexElementValues;
	}

	public void setIndexElementValues(String indexElementValues) {
		String[] values = indexElementValues.split(",");
		values[0]=values[0].split("=")[1];
		this.indexElementValues = Arrays.asList(values);
	}

	/** Map the List Of parameters
	 * @param iveraObject prepare the ParameterObject
	 * @param key Prepare the parameterObject
	 * @return List returns parameterList 
	 */
	public List<Parameter> mapParameterWithDetectorObj(String iveraObject,
			String key) {
		List<String> detectorNames = new ArrayList<String>();
		detectorNames.add(TlcConstants.Detector_Category_Status);
		detectorNames.add(TlcConstants.Detector_Category_Error);
		detectorNames.add(TlcConstants.Detector_Category_Performance);
		detectorNames.add(TlcConstants.Detector_Category_Swico);
		detectorNames.add(TlcConstants.Detector_Category_Flutter);
		List<Parameter> parameterList = new ArrayList<>();
		for (int objCreate = 0; objCreate < detectorNames.size(); objCreate++) {
			Parameter parameter = new Parameter();
			StringBuffer sb = new StringBuffer();
			sb.append(iveraObject).append(".");
			if (NumberUtils.isNumber(key)) {
				sb.append(key)
						.append(".")
						.append(getIndexElementNames().get(
								Integer.parseInt(key))).append(".");
			} else {
				sb.append(getIndexElementNames().indexOf(key)).append(".")
						.append(key).append(".");
			}
			sb.append(detectorNames.get(objCreate));
			parameter.setKey(sb.toString());
			parameterList.add(parameter);
		}
		List<String> valuesList = mapParameterValueWithDetectorObj(
				parameterList, key);
		for (int index = 0; index < parameterList.size(); index++) {
			parameterList.get(index).setValue(valuesList.get(index));
		}
		return parameterList;
	}

	private List<String> mapParameterValueWithDetectorObj(
			List<Parameter> parameterList, String key) {
		String value = NumberUtils.isNumber(key) ? getIndexElementValues().get(
				Integer.parseInt(key)) : getIndexElementValues().get(
				getIndexElementNames().indexOf(key));
		String binaryValue = decimalToBinary(value);
		return binaryToParameterValue(binaryValue);
	}

	private String decimalToBinary(String value) {
		String binaryString = String.format("%6s",
				Integer.toBinaryString(Integer.parseInt(value))).replace(" ",
				"0");
		return binaryString;
	}

	private List<String> binaryToParameterValue(String binaryValue) {
		String[] binaryValSplit = binaryValue.split("");
		List<String> valuesList = new ArrayList<>();
		valuesList.add(binaryValSplit[0].equals("1") ? "occupied"
				: "unoccupied");
		valuesList.add(binaryValSplit[1].equals("1") ? "true" : "false");
		if (binaryValSplit[2].equals("1") && binaryValSplit[0].equals("0")) {
			valuesList.add("under");
		} else if (binaryValSplit[2].equals("1")
				&& binaryValSplit[0].equals("1")) {
			valuesList.add("overbit");
		} else {
			valuesList.add("none");
		}
		valuesList.add(mapSwico(binaryValSplit[3] + binaryValSplit[4]));
		valuesList.add(binaryValSplit[5].equals("1") ? "true" : "false");
		return valuesList;
	}

	private String mapSwico(String binary) {
		switch (binary) {
		case "00":
			return "none";
		case "10":
			return "swico_op";
		case "01":
			return "swico_af";
		case "11":
			return "illegal";
		default:
			return null;
		}
	}

}
