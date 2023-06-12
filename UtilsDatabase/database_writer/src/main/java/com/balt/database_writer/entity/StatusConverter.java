package com.balt.database_writer.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<HandlingUnitStatus, Integer> {
    
    @Override
    public Integer convertToDatabaseColumn(HandlingUnitStatus handlingUnitStatus){

        if(handlingUnitStatus == null) return null;

        return handlingUnitStatus.getEnumId();
    }

    @Override
    public HandlingUnitStatus convertToEntityAttribute(Integer dbData){

        if(dbData == null) return null;

        switch(dbData){
            case 10:
            return HandlingUnitStatus.ADDED;
            case 20:
            return HandlingUnitStatus.PROCESSED;
            case 30:
            return HandlingUnitStatus.ERROR;
        }

       throw new IllegalArgumentException("There is no such status code as: "+ dbData);
    }
}
