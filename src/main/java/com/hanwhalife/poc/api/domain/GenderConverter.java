package com.hanwhalife.poc.api.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class GenderConverter implements AttributeConverter<Gender, String> {


    @Override
    public String convertToDatabaseColumn(Gender gender) {
        return gender != null ?  gender.getShortCode() : null;
    }

    @Override
    public Gender convertToEntityAttribute(String s) {
        return Gender.getGenderByString(s).orElseThrow();
    }
}
