/*package com.cgi.charm.sc.tlc.persistence.mapper;

import org.springframework.stereotype.Component;

import com.cgi.charm.cdm.tlc.Property;
import com.cgi.charm.sc.tlc.persistence.model.TlcPropertyInfo;
import com.cgi.charm.util.persistence.mappers.PersistenceMapper;

*//**
 * @author shyam.avula
 *
 *//*
@Component
public class TlcPropertyMapper implements
            PersistenceMapper<Property, TlcPropertyInfo> {


	@Override
	public Property toCdm(TlcPropertyInfo tlcPropertyInfo) {
		String key = tlcPropertyInfo.getKey();
		String value = tlcPropertyInfo.getValue();
		Property property = new Property();
		property.setKey(key);
		property.setValue(value);
		return property;
	}

}
*/