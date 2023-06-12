// package com.balt.get_hadning_utils_xml.config;

// import java.util.logging.FileHandler;
// import java.util.logging.Logger;
// import java.util.logging.SimpleFormatter;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// @Configuration
// public class XmlAppConfig {


//     String filename;

//     @Bean
//     public Logger logger(){
//         Logger logger = Logger.getLogger("xmlLogs");
//         FileHandler fileHandler;

//         try {
//             fileHandler = new FileHandler(filename); 
//             logger.addHandler(fileHandler);
            
//             SimpleFormatter simpleFormatter = new SimpleFormatter();
//             fileHandler.setFormatter(simpleFormatter);
//         } catch (Exception e) {
//             e.printStackTrace();
//             // TODO: handle exception
//         }

//         return logger;
//     }
    
// }
