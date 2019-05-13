package model.configreader;

import model.card.propertycard.PropertyCard;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class serves as the XML parser to read in information data regarding purchasable monopoly properties
 * and their attributes.
 */
public class PropertyConfig {

    public List<PropertyCard> makePropertiesInfoFromXML(File inputFile) {

        List<PropertyCard> propertyCards = new ArrayList<>();

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            List<String> propertyTypes = new ArrayList<>(Arrays.asList("RealEstate", "Railroad", "Utilities"));
            for (String type : propertyTypes) {
                propertyCards.addAll(readPropertyInfo(doc, type));
            }
        } catch (Exception e) {
            System.out.println("Parser has encountered an error with the given file!");
            e.printStackTrace();
        }
        return propertyCards;
    }

    private List<PropertyCard> readPropertyInfo(Document doc, String tagName) {

        NodeList nodeList = doc.getElementsByTagName(tagName);
        List<PropertyCard> realEstateCards = new ArrayList<>();

        for (int group = 0; group < nodeList.getLength(); group++) {
            Node groupNode = nodeList.item(group);
            NodeList groupList = groupNode.getChildNodes();
            Element groupNodeInfo = (Element)groupNode;
            int propertyGroup = Integer.parseInt(groupNodeInfo.getAttribute("group"));
            int totalGroups = Integer.parseInt(groupNodeInfo.getAttribute("totalgroups"));

            for (int num = 0; num < groupList.getLength(); num++) {
                Node groupNumNode = groupList.item(num);
                if (groupNumNode.getNodeType() == Node.ELEMENT_NODE) {
                    PropertyCard propertyCard = readAll(groupNumNode, propertyGroup, totalGroups, tagName);
                    realEstateCards.add(propertyCard);
                }
            }
        }
        return realEstateCards;
    }

    private PropertyCard readAll(Node groupNumNode, int propertyGroup, int totalGroups, String type) {

        Element property = (Element) groupNumNode;

        int groupNum = Integer.parseInt(property.getAttribute("num"));
        String title = property.getElementsByTagName("title").item(0).getTextContent();
        int buyingPrice = Integer.parseInt(property.getElementsByTagName("price").item(0).getTextContent());
        int sellingPrice = buyingPrice / 2;
        int mortgage;
        try {
            mortgage = Integer.parseInt(property.getElementsByTagName("mortgage").item(0).getTextContent());
        }
        catch (NullPointerException e) {
            mortgage = 0;
        }
        int buildingCost;
        try {
            buildingCost = Integer.parseInt(property.getElementsByTagName("buildingcost").item(0).getTextContent());
        }
        catch (NullPointerException e) {
            buildingCost = 0;
        }

        List<Integer> rentInfo = new ArrayList<>();
        NodeList subNodes = groupNumNode.getChildNodes();
        for (int i = 0; i < subNodes.getLength(); i ++) {
            if (subNodes.item(i).getNodeName().contains("rent")) {
                rentInfo.add(Integer.parseInt(subNodes.item(i).getTextContent()));
            }

        }

        PropertyCard propertyCard = null;
        try {
            propertyCard = (PropertyCard) Class.forName("model.card.propertycard." + type + "PropertyCard").getConstructors()[0].newInstance(title, propertyGroup, groupNum, buyingPrice, sellingPrice, totalGroups, rentInfo, mortgage, buildingCost);
        } catch (ClassNotFoundException e){
            System.out.println("Class matching name found in configuration file does not exist!");
            e.printStackTrace();
        } catch (IllegalAccessException e){
            System.out.println("Class matching name found in configuration file is not accessible!");
            e.printStackTrace();
        } catch (InvocationTargetException e){
            System.out.println("Your underlying method is non-functional!");
            e.printStackTrace();
        } catch (InstantiationException e) {
            System.out.println("Issue with object being made, check to see if parameters are appropriate!");
            e.printStackTrace();
        }
        return propertyCard;
    }
}