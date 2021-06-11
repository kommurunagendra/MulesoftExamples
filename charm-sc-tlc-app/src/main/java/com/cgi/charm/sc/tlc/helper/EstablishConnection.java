package com.cgi.charm.sc.tlc.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cgi.charm.sc.tlc.persistence.model.TlcDeviceInfo;
import com.cgi.charm.sc.tlc.persistence.service.TlcQueryService;

/**
 * @author CHARM CGI
 *
 */
@Component
public class EstablishConnection {

	
	
	@Autowired
	private TlcQueryService tlcQueryServiceImpl;

	/**
	 * to fetch the deviceInfo's
	 * @return List
	 */
	public List<TlcDeviceInfo> handle() {
		List<TlcDeviceInfo> tlcDeviceInfoList = new ArrayList<>();
		tlcDeviceInfoList.addAll(tlcQueryServiceImpl.getAllTlcs());
		return tlcDeviceInfoList;
	}
}
