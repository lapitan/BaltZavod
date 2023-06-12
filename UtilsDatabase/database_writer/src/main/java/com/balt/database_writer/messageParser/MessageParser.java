package com.balt.database_writer.messageParser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.balt.database_writer.entity.HandlingUnit;
import com.balt.database_writer.entity.HandlingUnitId;
import com.balt.database_writer.service.HandlingUnitService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
@Component
public class MessageParser {

    @Autowired
    HandlingUnitService handlingUnitService;

    public void parse(String message){

        JsonObject jsonObject = JsonParser.parseString(message).getAsJsonObject();

        String queueId=jsonObject.get("queueId").toString();
        boolean end=jsonObject.get("end").getAsBoolean();
        String huid=jsonObject.get("huid").toString();
        String cwar=jsonObject.get("cwar").toString();
        String locf=jsonObject.get("locf").toString();
        String loct=jsonObject.get("loct").toString();
        long mdat=jsonObject.get("mdat").getAsLong();
        double quan = jsonObject.get("quan").getAsDouble();

        HandlingUnitId handlingUnitId = new HandlingUnitId(huid, cwar, locf, loct, mdat);

        if(handlingUnitService.save(handlingUnitId, quan)){
            System.out.println("ok!");
        }else{
            System.out.println("notok??");

        };


    }
    
}
