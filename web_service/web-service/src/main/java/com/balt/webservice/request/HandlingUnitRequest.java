package com.balt.webservice.request;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
public class HandlingUnitRequest {

    String huid;
    String cwar;
    String locf;
    String loct;
    long mdat;
    double quan;
    String serialNumber;

}
