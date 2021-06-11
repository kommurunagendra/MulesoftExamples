package com.cgi.charm.sc.tlc.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.mule.api.MuleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cgi.charm.cdm.tlc.IveraObject;
import com.cgi.charm.cdm.tlc.Parameter;
import com.cgi.charm.sc.tlc.persistence.model.TlcDeviceInfo;
import com.cgi.charm.sc.tlc.persistence.service.TlcQueryService;

/**
 * @author CHARM CGI
 *
 */
@Component
public class PrepareCommand {
	@Autowired
	private TlcQueryService tlcQueryServiceImpl;
	private MuleMessage message;

	private String literal = "\",\"";
	private static final Logger logger = Logger
			.getLogger(PrepareCommand.class);
	/**
	 * the List of commands
	 * 
	 * @param tlcDeviceInfo
	 *            to prepare ivera command
	 * @return List returns list of commands
	 */
	public List<String> prepareIveraCommands(TlcDeviceInfo tlcDeviceInfo) {
		List<String> commands = new ArrayList<String>();
		for (IveraObject iveraObject : tlcDeviceInfo.getIveraObject()) {
			if (iveraObject.getParameters() != null) {
				prepareCommandParameter(commands, iveraObject);
			}
		}
		return commands;
	}

	/**
	 * the String
	 * 
	 * @param deviceInfo
	 *            is to get the response from the device
	 * @param iveraObject
	 *            iveraobject to prepare command
	 * @return String
	 */
	public String getReadCommand(TlcDeviceInfo deviceInfo, String iveraObject) {
		int sequenceNumber = deviceInfo.getSequenceNumber();
		StringBuilder readCommand = new StringBuilder();
		readCommand.append("@").append(++sequenceNumber).append("#").append(iveraObject).append("\r\n");
	
		return readCommand.toString();
	}

	/**
	 * the List of commands
	 * 
	 * @param payload
	 *            prepare vri command
	 * @param triggerCode
	 *            prepare command based on the triggercode
	 * @return List
	 */
	public List<String> test(String payload, String triggerCode) {
		String s = payload.substring(payload.indexOf("=") + 1).trim();
		List<String> iveraList = new ArrayList<String>();
		if (payload.contains(literal)) {
			String[] ss = s.substring(1, s.length() - 1).split(literal);
			iveraList.addAll(Arrays.asList(ss).stream().filter(li -> li.contains(triggerCode)).map(li -> (String) li)
					.collect(Collectors.toList()));
		} else {
			
			try {
				String str = s.substring(1, s.length() - 1);
				iveraList.add(str);

			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return iveraList;
	}

	/**
	 * the List
	 * 
	 * @param payload
	 *            prepare par command
	 * @return List return list of commands
	 */
	public List<String> partest(String payload) {
		String s = payload.substring(payload.indexOf("=") + 1).trim();
		List<String> iveraList = new ArrayList<String>();
		if (payload.contains(literal)) {
			String[] ss = s.substring(1, s.length() - 1).split(literal);
			Arrays.asList(ss).stream().forEach(li -> iveraList.add((String) li));
		} else {
			String str;
			try {
				str = s.substring(1, s.length() - 1);
				iveraList.add(str);

			} catch (Exception e) {
				System.out.println(e);
			}
			
		}
		return iveraList;
	}

	/**
	 * @param hostname
	 *            getHostName from TlcDeviceInfo
	 * @return TlcDeviceInfo
	 * 
	 */
	
	
	public TlcDeviceInfo getdeviceInfo(String hostname) {
		TlcDeviceInfo deviceInfo = null;
		String[] Ip=hostname.split(":");
		String host=Ip[0];
		String ipAddress=host.substring(1);
		List<TlcDeviceInfo> tlcDeviceInfoList = tlcQueryServiceImpl.getAllTlcs();
		for (TlcDeviceInfo tlcDeviceInfo : tlcDeviceInfoList) {
			if (tlcDeviceInfo.getIpnrVri().equals(ipAddress)) {
				deviceInfo = tlcDeviceInfo;				
				break;		
		
			}
			else if(tlcDeviceInfo.getIpnrVri().equals("localhost")){
				deviceInfo = tlcDeviceInfo;				
				break;		
			}
		}
		return deviceInfo;
	}

	/**
	 * the prepareCommandParameter
	 * 
	 * @param commands
	 *            the commands
	 * @param iveraObject
	 *            the iveraObject
	 */
	private void prepareCommandParameter(List<String> commands, IveraObject iveraObject) {
		for (Parameter parameter : iveraObject.getParameters().getParameter()) {
			String objectName = iveraObject.getName().toUpperCase();
			StringBuilder command = new StringBuilder();
			command.append(objectName).append("/");
			if (NumberUtils.isNumber(parameter.getKey())) {
				command.append('#');
			}
			command.append(parameter.getKey()).append("=").append(parameter.getValue()).append("\r");

			commands.add(command.toString());
		}
	}
}
