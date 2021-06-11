package com.cgi.charm.sc.tlc.persistence.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.cgi.charm.cdm.tlc.Property;
import com.cgi.charm.sc.tlc.persistence.model.TlcPropertyInfo;
import com.cgi.charm.util.persistence.mappers.PersistenceMapper;

/**
 * @author shyam.avula
 *
 */
@Component
public class TlcPropertyInfoMapper implements PersistenceMapper<List<TlcPropertyInfo>, List<Property>> {

	/*
	public List<Property> mapTlcPropertyInfoList(
			List<TlcPropertyInfo> tlcPropInfoList) {
		List<Property> propertyList = new ArrayList<>();
		for (TlcPropertyInfo tlcPropInfo : tlcPropInfoList) {
			Property prop = new Property();
			prop.setKey(tlcPropInfo.getKey());
			prop.setValue(tlcPropInfo.getValue());
			propertyList.add(prop);
		}
		return propertyList;
	}*/

	@Override
	public List<TlcPropertyInfo> toCdm(List<Property> propertyList) {
		List<TlcPropertyInfo> tlcPropInfoList = new ArrayList<>();
		for (Property property : propertyList) {
			TlcPropertyInfo tlcPropInfo = new TlcPropertyInfo();
			tlcPropInfo.setKey(property.getKey());
			tlcPropInfo.setValue(property.getValue());
			tlcPropInfoList.add(tlcPropInfo);
		}
		return tlcPropInfoList;
	}
	
}
