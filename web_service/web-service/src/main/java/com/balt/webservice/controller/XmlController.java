package com.balt.webservice.controller;

import com.balt.webservice.request.HandlingUnitRequest;
import com.balt.webservice.serialNumber.SerialNumber;
import com.balt.webservice.serialNumber.SerialNumberRepository;
import io.swagger.v3.oas.annotations.Operation;

import java.util.Optional;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/units",
        consumes = MediaType.APPLICATION_JSON_VALUE)
public class XmlController {

    SerialNumberRepository serialNumberRepository;
    AmqpTemplate amqpTemplate;

    public XmlController(SerialNumberRepository serialNumberRepository, AmqpTemplate amqpTemplate) {
        this.serialNumberRepository = serialNumberRepository;
        this.amqpTemplate = amqpTemplate;
    }

    @PostMapping
    @Operation(summary = "add Handling Unit")
    public String addHandlingUnit(@RequestBody HandlingUnitRequest handlingUnitRequest) {
        // Optional<SerialNumber> optional = serialNumberRepository.findById(handlingUnitRequest.getSerialNumber());

        // SerialNumber
        if (!serialNumberRepository.existsById(handlingUnitRequest.getSerialNumber()))
            return "Your Terminal isn't in Infor database";

        String message = "{queueId=\"something\", end=\"false\", huid=\"" +
                handlingUnitRequest.getHuid() + "\", cwar=\"" + handlingUnitRequest.getCwar() +
                "\", locf=\"" + handlingUnitRequest.getLocf() + "\", loct=\"" + handlingUnitRequest.getLoct() +
                "\", mdat=\"" + handlingUnitRequest.getMdat() + "\", quan=\"" + handlingUnitRequest.getMdat() + "\"}";

        amqpTemplate.convertAndSend("requestToDb", message);
        return "OK!";
    }

    @PostMapping(value = "/serNum")
    @Operation(summary = "add Serial Number To Infor")
    public String addSerialNumber(@RequestBody String serialNumber){
        serialNumber = serialNumber.replaceAll("\"", "");
        if(serialNumberRepository.existsById(serialNumber)) return "your Termial is already in database";
        SerialNumber serialNumberEntity = new SerialNumber();
        serialNumberEntity.setSerialNumber(serialNumber);
        serialNumberRepository.save(serialNumberEntity);
        return "OK!";
    }

}
