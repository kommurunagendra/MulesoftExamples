package com.cgi.charm.sc.tlc.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * @author pavan.chenna
 *
 */
@Component
public class ListOfIveraObjects {
	
	

	/**
	 * @return List
	 */
	public List<String> objectList() {
		//REMARK: please remember to use the diamond operator instead of explicit type arguments
		List<String> list = new ArrayList<String>();
		list.add("VRIID/#1");
		list.add("VRIID/#2");
		list.add("KTIJD");
		list.add("TID");
		list.add("YID");
		list.add("DATACOM/#0");
		list.add("DATACOM/#1");
		list.add("DATACOM/#3");
		return list;
	}

	/**
	 * @return List
	 */
	public List<String> statusAttrObjectList() {
		//REMARK: please remember to use the diamond operator instead of explicit type arguments
		List<String> list = new ArrayList<String>();
		list.add("VRISTAT/#0");
		list.add("VRISTAT/#1");
		list.add("VRISTAT/#2");
		list.add("VRISTAT/#3");
		list.add("VRISTAT/#4");
		list.add("VRISTAT/#5");
		list.add("VRISTAT/#6");
		list.add("VRIFSUB/#0");
		list.add("VRIFSUB/#1");
		list.add("VRIFSUB/#2");
		list.add("VRIFSUB/#3");
		list.add("BB0");
		list.add("BB1");
		return list;
	}
}
