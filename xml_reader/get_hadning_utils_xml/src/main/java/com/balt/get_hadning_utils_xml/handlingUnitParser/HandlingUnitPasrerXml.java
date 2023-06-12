package com.balt.get_hadning_utils_xml.handlingUnitParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.Date;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Component
public class HandlingUnitPasrerXml {
    
    @Value("${xml.pid}")
    String xmlPid;
    
    @Value("${xml.ser.num}")
    String xmlSerNum;

    @Value("${xml.remote.dir}")
    String xmlRemoteDir;

    @Value("${xml.name}")
    String xmlName;

    @Value("${xml.vid}")
    String xmlVid;

    @Value("${xml.mode}")
    String mode;

    @Value("${xml.path.to.adb}")
    String pathToAdb;

    @Autowired
    AmqpTemplate amqpTemplate;

    int retryNumber = 0;

    public void start(){

        String filePath = xmlRemoteDir + xmlName;

        if(mode.equals("pull")){
            try {
                ProcessBuilder builder = new ProcessBuilder(
                    "cmd.exe", "/c", "cd "+pathToAdb+ /*" & dir"*/ " & adb pull "+ filePath + " " + Paths.get("").toAbsolutePath()
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
                // TODO: handle exception
            }
        }
        
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        // System.out.println("=============");

        try {

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

                // Date date = new Date(mdat*1000);
                // System.out.println(date.toString());

                // System.out.println("curr element huid: " + huid);
                // System.out.println("curr element cwar: " + cwar);
                // System.out.println("curr element locf: " + locf);
                // System.out.println("curr element loct: " + loct);
                // System.out.println("curr element mdat: " + mdat);
                // System.out.println("curr element quan: " + quan);

                // System.out.println("=============");

            }

        } catch (Exception e) {
            System.out.println("Can't read file. Number of retry: "+ retryNumber);
            retryNumber++;
            try {
                Thread.sleep(5000);
            } catch (Exception ex) {
                ex.printStackTrace();
                // TODO: handle exception
            }
            start();
        }

    }

}
