package com.balt.database_writer.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.balt.database_writer.entity.HandlingUnit;
import com.balt.database_writer.entity.HandlingUnitId;
import com.balt.database_writer.entity.HandlingUnitStatus;
import com.balt.database_writer.mapper.HandlingUnitMapper;
import com.balt.database_writer.repository.HandlingUnitRepository;

@Service
public class HandlingUnitService {

    @Autowired
    HandlingUnitRepository unitRepository;

    //request: Json file
    /*{"queueId"="something",
        "end"=true/false,
        "huid"="some huid",
        "cwar"="some cwar",
        "locf"="some locf",
        "loct"="some loct",
        "mdat"="some mdat",
        "quan"="some quan"}
    */


    public boolean save(HandlingUnitId handlingUnitId, double quantity){

        if(unitRepository.findByHandlingUnitId(handlingUnitId).isPresent()) return false;

        HandlingUnit handlingUnit = HandlingUnitMapper.getHandlingUnitfromHandlingUnitId(handlingUnitId);

        handlingUnit.setQuan(quantity);
        handlingUnit.setStatus(HandlingUnitStatus.ADDED);
        handlingUnit.setQdat(new Date().getTime()/1000);

        unitRepository.save(handlingUnit);

        return true;

    }

    public HandlingUnit getHandlingUnit(HandlingUnitId handlingUnitId){
        return unitRepository.findByHandlingUnitId(handlingUnitId).orElse(null);
    }

    public boolean addInforTime(HandlingUnitId handlingUnitId, long inforDate){
        
        HandlingUnit handlingUnit = getHandlingUnit(handlingUnitId);

        if(handlingUnit==null) return false;

        handlingUnit.setIdat(inforDate);
        handlingUnit.setStatus(HandlingUnitStatus.PROCESSED);
        unitRepository.save(handlingUnit);

        return true;
    }

    public boolean setInforError(HandlingUnitId handlingUnitId){
        HandlingUnit handlingUnit = getHandlingUnit(handlingUnitId);

        if(handlingUnit == null) return false;

        handlingUnit.setStatus(HandlingUnitStatus.ERROR);

        unitRepository.save(handlingUnit);
        return true;
    }
    
}
