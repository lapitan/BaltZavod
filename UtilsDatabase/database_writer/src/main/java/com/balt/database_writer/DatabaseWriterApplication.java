package com.balt.database_writer;

import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.balt.database_writer.entity.HandlingUnit;
import com.balt.database_writer.entity.HandlingUnitId;
import com.balt.database_writer.messageParser.MessageParser;
import com.balt.database_writer.service.HandlingUnitService;

@SpringBootApplication
public class DatabaseWriterApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext applicationContext = SpringApplication.run(DatabaseWriterApplication.class, args);
		// MessageParser messageParser = applicationContext.getBean(MessageParser.class);

		// HandlingUnitId handlingUnitId = new HandlingUnitId();
		

		// String message = "{queueId=\"something\", end=\"true\", huid=\"some huid\", cwar=\"some cwar\", locf=\"some locf\", loct=\"some loct\", mdat=\"100000000\", quan=\"2.5\"}";

		// messageParser.parse(message);
		// messageParser.parse(message);
		// messageParser.parse(message);

		// System.out.println("abobis");

		// handlingUnitId.setHuid("123");
		// handlingUnitId.setCwar("1");
		// handlingUnitId.setLocf("1");
		// handlingUnitId.setLoct("2");
		// handlingUnitId.setMdat(new Date().getTime()/1000);

		// handlingUnitService.save(handlingUnitId, 10);
		// handlingUnitService.save(handlingUnitId, 10);

		// handlingUnitService.addInforTime(handlingUnitId, new Date().getTime()/1000);

		// HandlingUnit handlingUnit = handlingUnitService.getHandlingUnit(handlingUnitId);

		// if(handlingUnit == null) return;

		// System.out.println(handlingUnit.getStatus());
	}

}
