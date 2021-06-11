package com.cgi.charm.sc.tlc.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cgi.charm.cdm.tlc.TlcInfoRequest;
import com.cgi.charm.sc.tlc.persistence.model.TlcDeviceInfo;
import com.cgi.charm.sc.tlc.persistence.service.TlcQueryService;

/**
 * @author pavan.chenna
 *
 */
@Component
public class TlcDeviceInfoList {

	@Autowired
	private TlcQueryService tlcQueryServiceImpl;

	/** handle the TlcDeviceInfo	
	 * @param tlcInfoRequest fetch tlcDeviceInfo's from the datastore
	 * @return List returns deviceInfo's 
	 */
	public List<TlcDeviceInfo> handle(TlcInfoRequest tlcInfoRequest) {
		List<TlcDeviceInfo> tlcDeviceInfoList = new ArrayList<>();
		boolean isAllTlcs = Boolean.TRUE.equals(tlcInfoRequest.getAllTlc());
		if (isAllTlcs) {
			tlcDeviceInfoList.addAll(tlcQueryServiceImpl.getAllTlcs());
		} else if (tlcInfoRequest.getTlcSpec() != null) {
			tlcInfoRequest
					.getTlcSpec()
					.forEach(
							eachTlcSpec -> {
								TlcDeviceInfo deviceInfo=null;
								deviceInfo=tlcQueryServiceImpl.getTlcById(eachTlcSpec.getTlcId().getId());
								if(deviceInfo!=null)
									tlcDeviceInfoList.add(deviceInfo);	
							});
													
		}
		return tlcDeviceInfoList;
	}
}
