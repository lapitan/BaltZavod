package com.balt.get_hadning_utils_xml;

import java.util.Arrays;
import java.util.Properties;

// import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

// import java.io.IOException;
// import java.nio.file.FileStore;
// import java.nio.file.FileSystem;
// import java.nio.file.FileSystems;
// import java.nio.file.Files;
// import java.nio.file.Path;
// import java.nio.file.Paths;
// import java.util.Date;
// import java.util.Date;
import java.io.FileInputStream;
import java.io.FileOutputStream;

// import javax.xml.transform.Source;

import com.balt.get_hadning_utils_xml.handlingUnitParser.HandlingUnitParserXml;

@SpringBootApplication
public class GetHadningUtilsXmlApplication {

	    /*{"queueId"="something",
        "end"=true/false,
        "huid"="some huid",
        "cwar"="some cwar",
        "locf"="some locf",
        "loct"="some loct",
        "mdat"="some mdat",
        "quan"="some quan"}
    */

//    static ConfigurableApplicationContext applicationContext;

	public static void main(String[] args) {

        if(args == null || args.length<1){
            System.out.println("Some arguments reaqired!");
            return;
        }

        String arg1 = args[0];

        if(arg1.equals("-gap")){

            Properties properties = new Properties();
            
            try {

                FileInputStream fStream = new FileInputStream("get_hadning_utils_xml\\src\\main\\resources\\application.properties");

                properties.load(fStream);

                fStream.close();

                System.out.println("xml.name: "+properties.getProperty("xml.name"));
                System.out.println("xml.remote.dir: "+properties.getProperty("xml.remote.dir"));
                System.out.println("xml.ser.num: "+properties.getProperty("xml.ser.num"));
                System.out.println("xml.mode: "+properties.getProperty("xml.mode"));
                System.out.println("xml.path.to.adb: "+properties.getProperty("xml.path.to.adb"));
                
            } catch (Exception e) {
                e.printStackTrace();
            }    
        }
        if(arg1.equals("-sp")){

            Properties properties = new Properties();

            try {

                FileInputStream fStream = new FileInputStream("get_hadning_utils_xml\\src\\main\\resources\\application.properties");

                properties.load(fStream);
    
                fStream.close();
                
            } catch (Exception e) {
                e.printStackTrace();
            }

            String[] argsCopy;

            argsCopy = Arrays.copyOfRange(args, 1, args.length);

            Arrays.stream(argsCopy)
            .map(arg -> arg.split("="))
            .forEach(arg-> properties.setProperty(arg[0], arg[1]));

            try {
                FileOutputStream fileOutputStream = new FileOutputStream("get_hadning_utils_xml\\src\\main\\resources\\application.properties");

                properties.store(fileOutputStream, "Properties");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        if(arg1.equals("-r")){
            ConfigurableApplicationContext applicationContext = SpringApplication.run(GetHadningUtilsXmlApplication.class, args);
            HandlingUnitParserXml handlingUnitParserXml = applicationContext.getBean(HandlingUnitParserXml.class);
            handlingUnitParserXml.start();
            applicationContext.close();
        }
	}

}