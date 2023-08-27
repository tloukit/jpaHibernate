package com.hibernatejpa.converter;


import java.util.stream.Stream;

import com.hibernatejpa.domain.Certification;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CertificationAttributeConverter implements AttributeConverter<Certification, Integer>{

	@Override
	public Integer convertToDatabaseColumn(Certification attribute) {
		return attribute != null ? attribute.getKey() : null;
	}

	@Override
	public Certification convertToEntityAttribute(Integer dbData) {
		
		return Stream.of(Certification.values())
				.filter(certif -> certif.getKey().equals(dbData))
				.findFirst().orElse(null);
	}
	
	

}
