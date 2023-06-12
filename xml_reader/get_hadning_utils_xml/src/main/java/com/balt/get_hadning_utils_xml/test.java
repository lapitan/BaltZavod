package com.balt.get_hadning_utils_xml;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
// import java.io.IOException;
// import java.nio.file.FileStore;
// import java.nio.file.FileSystem;
// import java.nio.file.FileSystems;
// import java.nio.file.Files;
// import java.nio.file.Path;
// import java.nio.file.Paths;
// import java.util.Date;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.regex.Pattern;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
// import javax.xml.transform.Source;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import org.w3c.dom.Node;

public class test {

//    private static String mode = "pull";
//    private static String xmlRemoteDir = "pull";
//    private static String xmlName = "pull";

    public static void main(String[] args) {

        File root = new File(".");

        if(!root.isDirectory()) System.out.println("kavo??");

        System.out.println(root.getAbsolutePath());

        File[] files = root.listFiles();

        Arrays.stream(files)
        .forEach(file ->{
            System.out.println(file.getAbsolutePath());
        });

        String regexp = "get_handling_units_xml_.*.log";

        Pattern pattern = Pattern.compile(regexp);

        files = root.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file){
                return pattern.matcher(file.getName()).matches();
            }
        });

        Arrays.stream(files)
            .forEach(file ->{
                System.out.println(file.getAbsolutePath());
            });

//        String filePath = xmlRemoteDir + xmlName;
//
//        if(mode.equals("pull")){
//            try {
//                ProcessBuilder builder = new ProcessBuilder(
//                        "cmd.exe", "/c", "cd "+pathToAdb+ /*" & dir"*/ " & adb pull "+ filePath + " " + Paths.get("").toAbsolutePath()
//                );
//                builder.redirectErrorStream(true);
//                Process process = builder.start();
//
//                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//                String line;
//
//                while(true){
//                    line=reader.readLine();
//                    if(line==null) break;
//                    System.out.println(line);
//                }
//
//
//                Thread.sleep(1000);
//            } catch (Exception e) {
//                // TODO: handle exception
//            }
//        }

//        Path path = Paths.get("");
//
//        System.out.println(path.toAbsolutePath());

        // String path = "\\EDA50K\\IPSM card\\example.xml";

        // File[] f = File.listRoots();

        // Arrays.stream(f)
        //     .forEach( file->{
        //         System.out.println(file.getAbsolutePath());
        //     } );

        // DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        

        // System.out.println("=============");

        // try {

        //     dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        //     DocumentBuilder db = dbf.newDocumentBuilder();
        //     Document document = db.parse(new File(path));
        //     document.getDocumentElement().normalize();

        //     NodeList list = document.getElementsByTagName("handlingUnit");

        //     for (int i = 0; i < list.getLength(); i++) {
        //         Node node = list.item(i);

        //         if (node.getNodeType() != Node.ELEMENT_NODE)
        //             continue;

        //         Element element = (Element) node;

        //         String huid = element.getElementsByTagName("huid").item(0).getTextContent();
        //         String cwar = element.getElementsByTagName("cwar").item(0).getTextContent();
        //         String locf = element.getElementsByTagName("locf").item(0).getTextContent();
        //         String loct = element.getElementsByTagName("loct").item(0).getTextContent();
        //         long mdat = Long.parseLong(element.getElementsByTagName("mdat").item(0).getTextContent());
        //         double quan = Double.parseDouble(element.getElementsByTagName("quan").item(0).getTextContent());

        //         Date date = new Date(mdat*1000);
        //         System.out.println(date.toString());

        //         System.out.println("curr element huid: " + huid);
        //         System.out.println("curr element cwar: " + cwar);
        //         System.out.println("curr element locf: " + locf);
        //         System.out.println("curr element loct: " + loct);
        //         System.out.println("curr element mdat: " + mdat);
        //         System.out.println("curr element quan: " + quan);

        //         System.out.println("=============");

        //     }

        // } catch (Exception e) {
        //     e.printStackTrace();
        // }

    }

}
