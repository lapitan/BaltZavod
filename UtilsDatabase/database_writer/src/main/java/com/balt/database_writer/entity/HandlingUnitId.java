package com.balt.database_writer.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class HandlingUnitId implements Serializable {
    
    String huid;
    String cwar;
    String locf;
    String loct;
    long mdat;

}
