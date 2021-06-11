/*package com.cgi.charm.sc.tlc.persistence.test;

import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;

import org.easymock.EasyMock;
import org.junit.Test;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import com.cgi.charm.cdm.tlc.IveraObject;
import com.cgi.charm.cdm.tlc.Parameter;
import com.cgi.charm.cdm.tlc.Parameters;
import com.cgi.charm.sc.tlc.handler.PrepareCommand;
import com.cgi.charm.sc.tlc.helper.EstablishConnection;
import com.cgi.charm.sc.tlc.helper.ListOfIveraObjects;
import com.cgi.charm.sc.tlc.helper.ParLbIveraObjectMapping;
import com.cgi.charm.sc.tlc.helper.TlcIveraObjectElementsAndValues;
import com.cgi.charm.sc.tlc.helper.TlcUpdateDeviceInfoWithIveraValues;
import com.cgi.charm.sc.tlc.persistence.dao.TlcPersistenceDAO;
import com.cgi.charm.sc.tlc.persistence.model.TlcDeviceInfo;
import com.cgi.charm.sc.tlc.persistence.service.TlcQueryService;

public class TlcIveraObjectElementsAndValuesTest extends BaseTlcPersistenceTest {

	//@Test
	public void detectorObjectCdmMapTest() {
		TlcIveraObjectElementsAndValues tlcIveraObjectElementsAndValues = new TlcIveraObjectElementsAndValues();
		tlcIveraObjectElementsAndValues
				.setIndexElementNames("@23#=\"SG11_1\",\"SG11_2\",\"SG11_3\",\"SG11_4\"");
		tlcIveraObjectElementsAndValues.setIndexElementValues("@23=0,1,0,3");
		assertTrue(null!=tlcIveraObjectElementsAndValues.mapParameterWithDetectorObj("D",
				"SG11_1"));
	}

	//@Test
	public void preparecommandTest() {
		PrepareCommand prepareCommand = new PrepareCommand();
		TlcDeviceInfo devicceInfo = listOfMockTlcDeviceInfos().get(0);
		devicceInfo.setIveraObject(prepareIveraObject());
		assertTrue(null!=prepareCommand.prepareIveraCommands(devicceInfo));
	}

	//@Test
	public void vriLbTest() {
		PrepareCommand prepareCommand = new PrepareCommand();
		String str = "\"19970117:150023,0,1020,SG02,R,1,1,2,0\",\"19970117:150023,0,1020,SG02,R,1,1,2,0\"";
		assertTrue(null!=prepareCommand.test(str, "1010"));
	}

	//@Test
	public void parLbTest() {
		PrepareCommand prepareCommand = new PrepareCommand();
		assertTrue(null!=prepareCommand
				.partest("\"20160609:163243,0,EGGP/EG_b_I_TR=15,4\",\"20160609:163243,0,EGGP/EG_b_I_TR=15,4\""));
	}
	
	//@Test
	public void getReadcommandTest(){
		PrepareCommand prepareCommand = new PrepareCommand();
		assertTrue(null!=prepareCommand.getReadCommand(listOfMockTlcDeviceInfos().get(0), "TDBP1"));
	}

	//@Test
	public void getDeviceInfoTest() {
		PrepareCommand prepareCommand = new PrepareCommand();
		ReflectionTestUtils.setField(prepareCommand, "tlcQueryServiceImpl",
				tlcQueryServiceImpl, TlcQueryService.class);
		ReflectionTestUtils.setField(tlcQueryServiceImpl,
				"tlcPersistenceDAOImpl", tlcPersistenceDAOImpl,
				TlcPersistenceDAO.class);
		ReflectionTestUtils
				.setField(tlcPersistenceDAOImpl, "hibernateTemplate",
						hibernateTemplate, HibernateTemplate.class);

		hibernateTemplate.find("from TlcDeviceInfo");
		EasyMock.expectLastCall().andReturn(listOfMockTlcDeviceInfos());
		hibernateTemplate
				.find("from TlcSubscriptionInfo si ,TlcDeviceSubscriptions ts where ts.tlcId = ? AND ts.subscriptionId=si.subscriptionId",
						"id1");
		EasyMock.expectLastCall().andReturn(new ArrayList<>());
		EasyMock.replay(hibernateTemplate);
		assertTrue(null!=prepareCommand.getdeviceInfo("5000_@2#:T=1010"));
	}

	//@Test
	public void parLbIveraObjectMapTest() {
		ParLbIveraObjectMapping parLbIveraObjectMapping = new ParLbIveraObjectMapping();
		assertTrue(null!=parLbIveraObjectMapping.map("20160609:163243,0,EGGP/EG_b_I_TR=15,4"));
	}

	//@Test
	public void updateDeviceInfoWithIveraObject() {
		TlcUpdateDeviceInfoWithIveraValues tlcUpdateDeviceInfoWithIveraValues = new TlcUpdateDeviceInfoWithIveraValues();
		TlcDeviceInfo tlcDeviceInfo = listOfMockTlcDeviceInfos().get(0);
		List<String> iveraList = new ArrayList<>();
		iveraList.add("VRISTAT/#0");
		iveraList.add("VRISTAT/#1");
		iveraList.add("VRISTAT/#2");
		iveraList.add("VRISTAT/#3");
		iveraList.add("VRISTAT/#4");
		iveraList.add("VRISTAT/#5");
		iveraList.add("VRISTAT/#6");
		iveraList.add("VRIFSUB/#0");
		iveraList.add("VRIFSUB/#1");
		iveraList.add("VRIFSUB/#2");
		iveraList.add("VRIFSUB/#3");
		iveraList.add("BB0");
		iveraList.add("BB1");
		for (String str : iveraList) {
			tlcUpdateDeviceInfoWithIveraValues.updateDeviceInfoWithStatusAttr(
					tlcDeviceInfo, str, "@1#=123");
		}
	}

	//@Test
	public void updateInventoryDeviceInfoWithIveraObject()
			throws DatatypeConfigurationException, ParseException {
		TlcUpdateDeviceInfoWithIveraValues tlcUpdateDeviceInfoWithIveraValues = new TlcUpdateDeviceInfoWithIveraValues();
		TlcDeviceInfo tlcDeviceInfo = listOfMockTlcDeviceInfos().get(0);
		List<String> iveraList = new ArrayList<>();
		iveraList.add("VRIID/#1");
		iveraList.add("VRIID/#2");
		iveraList.add("KTIJD");
		iveraList.add("TID");
		iveraList.add("YID");
		iveraList.add("DATACOM/#0");
		iveraList.add("DATACOM/#1");
		iveraList.add("DATACOM/#2");
		iveraList.add("DATACOM/#3");
		String payload = "@1#=123";
		for (String str : iveraList) {
			if (str.equals("KTIJD")) {
				payload = "@2#=31122016013015";
			}
			tlcUpdateDeviceInfoWithIveraValues.updateDeviceInfo(tlcDeviceInfo,
					str, payload);
		}
	}

	private List<IveraObject> prepareIveraObject() {
		List<IveraObject> iveraObjectList = new ArrayList<>();
		IveraObject iveraObject = new IveraObject();
		iveraObject.setName("d");
		Parameters parmaters = new Parameters();
		List<Parameter> parameterList = new ArrayList<>();
		Parameter paramter = new Parameter();
		paramter.setKey("3");
		parameterList.add(paramter);
		parmaters.setParameter(parameterList);
		iveraObject.setParameters(parmaters);
		iveraObjectList.add(iveraObject);
		return iveraObjectList;
	}

	//@Test
	public void establishConnectionTest() {
		EstablishConnection establishConnection = new EstablishConnection();
		ReflectionTestUtils.setField(establishConnection,
				"tlcQueryServiceImpl", tlcQueryServiceImpl,
				TlcQueryService.class);
		ReflectionTestUtils.setField(tlcQueryServiceImpl,
				"tlcPersistenceDAOImpl", tlcPersistenceDAOImpl,
				TlcPersistenceDAO.class);
		ReflectionTestUtils
				.setField(tlcPersistenceDAOImpl, "hibernateTemplate",
						hibernateTemplate, HibernateTemplate.class);
		hibernateTemplate.find("from TlcDeviceInfo");
		EasyMock.expectLastCall().andReturn(new ArrayList<>());
		EasyMock.replay(hibernateTemplate);
		assertTrue(null!=establishConnection.handle());
	}

	//@Test
	public void listOfIveraObjectTest() {
		ListOfIveraObjects listOfIveraObjects = new ListOfIveraObjects();
		listOfIveraObjects.statusAttrObjectList();
		assertTrue(null!=listOfIveraObjects.objectList());
	}
}*/