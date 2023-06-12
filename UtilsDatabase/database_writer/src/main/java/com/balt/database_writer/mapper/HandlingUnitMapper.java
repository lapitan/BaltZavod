package com.balt.database_writer.mapper;

import com.balt.database_writer.entity.HandlingUnit;
import com.balt.database_writer.entity.HandlingUnitId;

public class HandlingUnitMapper {
    
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

    public static HandlingUnit getHandlingUnitfromHandlingUnitId(HandlingUnitId handlingUnitId){
        HandlingUnit handlingUnit = new HandlingUnit();

        handlingUnit.setHandlingUnitId(handlingUnitId);

        return handlingUnit;
    }

}
