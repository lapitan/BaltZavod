package com.balt.get_hadning_utils_xml.handlingUnitParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.balt.get_hadning_utils_xml.serialNumber.SerialNumber;
import com.balt.get_hadning_utils_xml.serialNumber.SerialNumberRepository;
import com.balt.get_hadning_utils_xml.xmlLogger.XmlLogger;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Component
public class HandlingUnitParserXml {

    @Value("${xml.ser.num}")
    String xmlSerNum;

    @Value("${xml.remote.dir}")
    String xmlRemoteDir;

    @Value("${xml.name}")
    String xmlName;

    @Value("${xml.mode}")
    String mode;

    @Value("${xml.path.to.adb}")
    String pathToAdb;

    AmqpTemplate amqpTemplate;

    SerialNumberRepository serialNumberRepository;
    
    XmlLogger xmlLogger;

    public HandlingUnitParserXml(AmqpTemplate amqpTemplate, SerialNumberRepository serialNumberRepository, XmlLogger xmlLogger) {
        this.amqpTemplate = amqpTemplate;
        this.serialNumberRepository = serialNumberRepository;
        this.xmlLogger = xmlLogger;
    }

    int retryNumber = 0;

    public void start() {

        String filePath = xmlRemoteDir + xmlName;

        try {
            ProcessBuilder builder = new ProcessBuilder(
                    "cmd.exe", "/c", "cd " + pathToAdb + " & adb devices" /*" & adb pull "+ filePath + " " + Paths.get("").toAbsolutePath()*/
            );
            builder.redirectErrorStream(true);
            Process process = builder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            boolean deviceWasFound = false;

            while (!deviceWasFound) {
                line = reader.readLine();

                if (line == null) {
                    break;
                }

                String[] lines = line.split("\t");

                if (!lines[lines.length-1].equals("device")){
                    continue;
                }

                if (!lines[0].equals(xmlSerNum)){
                    continue;
                }
                
                SerialNumber serialNumber = serialNumberRepository.findById(xmlSerNum).orElse(null);

                if (!serialNumberRepository.existsById(xmlSerNum)){
                    System.out.println("Your device isn't in Infor database");
                    return;
                }

                deviceWasFound = true;
            }

            if (!deviceWasFound) return;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(mode.equals("pull")){
            try {
                ProcessBuilder builder = new ProcessBuilder(
                    "cmd.exe", "/c", "cd "+pathToAdb+ /*" & dir"*/ " & adb -s "+ xmlSerNum +" pull "+ filePath + " " + Paths.get("").toAbsolutePath()
                );
                builder.redirectErrorStream(true);
                Process process = builder.start();

                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;

                while(true){
                    line=reader.readLine();
                    if(line==null) break;
                    System.out.println(line);
                }


                Thread.sleep(1000);
            } catch (Exception e) {
            }
        }

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {

            xmlLogger.startReadXml(xmlName, xmlSerNum);

            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(new File(xmlName));
            document.getDocumentElement().normalize();

            NodeList list = document.getElementsByTagName("handlingUnit");

            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);

                if (node.getNodeType() != Node.ELEMENT_NODE)
                    continue;

                Element element = (Element) node;

                String huid = element.getElementsByTagName("huid").item(0).getTextContent();
                String cwar = element.getElementsByTagName("cwar").item(0).getTextContent();
                String locf = element.getElementsByTagName("locf").item(0).getTextContent();
                String loct = element.getElementsByTagName("loct").item(0).getTextContent();
                long mdat = Long.parseLong(element.getElementsByTagName("mdat").item(0).getTextContent());
                double quan = Double.parseDouble(element.getElementsByTagName("quan").item(0).getTextContent());

                String message = "{queueId=\"something\", end=\"false\", huid=\""+huid+"\", cwar=\""+cwar+"\", locf=\""+locf+"\", loct=\""+loct+"\", mdat=\""+mdat+"\", quan=\""+quan+"\"}";

                amqpTemplate.convertAndSend("requestToDb", message);

                xmlLogger.databaseRecord(message);

            }

        } catch (Exception e) {
            System.out.println("Can't read file. Number of retry: "+ retryNumber);
            retryNumber++;
            try {
                Thread.sleep(5000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            start();
        }

        xmlLogger.finishReadXml(xmlName, xmlSerNum);
        System.out.println("OK!");

    }

}
