package com.balt.get_hadning_utils_xml.xmlLogger;

import java.io.File;
import java.io.FileFilter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class XmlLogger {

    Logger logger;

    File file;

    public void terminalConnection(){
        
    }

    public void startReadXml(String xmlName, String serNum){
        logger.log(Level.INFO, "started reading xml with name: "+ xmlName + " scanner serialNumber: "+ serNum);
    }

    public void finishReadXml(String xmlName, String serNum){
        logger.log(Level.INFO, "finished reading xml with name: "+ xmlName + " scanner serialNumber: "+ serNum);
    }


    public void databaseRecord(String message){
        logger.log(Level.INFO, "send message to rabbit: " + message);
    }

    // private boolean checkFileSize(){

    //     if(filename.equals("null")||file.length()>(1024*1024*1024*2)){

    //         SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    //         String newFilename="get_handling_units_xml_"+simpleDateFormat.format(new Date())+".log";

    //         File newFile = new File(filename);

    //         try {
    //             newFile.createNewFile();   
    //         } catch (Exception e) {
    //             e.printStackTrace();
    //             // TODO: handle exception
    //         }

    //         Properties properties = new Properties();


    //     }

    //     if(true){}

    //     return false;
    // }

    @PostConstruct
    private void setLogger(){

        File root = new File(".");

        if(!root.isDirectory()) System.out.println("kavo??");

        System.out.println(root.getAbsolutePath());

        String regexp = "get_handling_units_xml_.*.log";

        Pattern pattern = Pattern.compile(regexp);

        File[] files = root.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file){
                return pattern.matcher(file.getName()).matches();
            }
        });

        if(files.length == 0){

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String newFilename="get_handling_units_xml_"+simpleDateFormat.format(new Date())+".log";

            File newFile = new File(newFilename);
            try {
                newFile.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
                // TODO: handle exception
            }

            files = new File[]{newFile};
        }

        if(files[0].length()>(1024L*1024L*1024L*2L)){

            files[0].delete();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String newFilename="get_handling_units_xml_"+simpleDateFormat.format(new Date())+".log";

            File newFile = new File(newFilename);
            try {
                newFile.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
                // TODO: handle exception
            }

            files = new File[]{newFile};
        }

        File currLogFile = files[0];

        logger = Logger.getLogger("xmlLogs");
        FileHandler fileHandler;

        try {

            fileHandler = new FileHandler(currLogFile.getName(), true); 
            logger.addHandler(fileHandler);
            
            SimpleFormatter simpleFormatter = new SimpleFormatter();
            fileHandler.setFormatter(simpleFormatter);
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }

    }

    
}
