package com.balt.database_writer.entity;

public enum HandlingUnitStatus {
    
    ADDED(10),
    PROCESSED(20),
    ERROR(30);

    private int enumId; 

    HandlingUnitStatus(int enumId){
        this.enumId = enumId;
    }

    public int  getEnumId(){
        return enumId;
    }
}
