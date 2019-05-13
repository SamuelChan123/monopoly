package model.configreader;

import model.card.perkcard.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class PerkConfig {
    private List<PerkCard> perkCardList = new ArrayList<>();

    public List<PerkCard> makePerkCardsFromXML(File inputFile){
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList cardNodeList= doc.getElementsByTagName("card");

            for(int i = 0; i < cardNodeList.getLength(); i++){
                Node cardNode = cardNodeList.item(i);
                List<String> attributeList = new ArrayList<>();

                if(cardNode.getNodeType() == Node.ELEMENT_NODE){
                    Element cardNodeElement = (Element) cardNode;
                    attributeList.add(cardNodeElement.getElementsByTagName("type").item(0).getTextContent().trim());
                    attributeList.add(cardNodeElement.getElementsByTagName("text").item(0).getTextContent().trim());
                    attributeList.add(cardNodeElement.getElementsByTagName("action").item(0).getTextContent().trim());
                    attributeList.add(cardNodeElement.getElementsByTagName("changeFund").item(0).getTextContent().trim());
                    attributeList.add(cardNodeElement.getElementsByTagName("move").item(0).getTextContent().trim());
                }

                PerkCard card = createCard(attributeList);
                if (card == null) {
                    System.exit(-1);
                }
                perkCardList.add(card);
            }
            return perkCardList;
        }catch (Exception e) {
            e.printStackTrace ();
        }
        return perkCardList;
    }

    public PerkCard createCard(List<String> attributeList) {
        String action = attributeList.get(2);
        try {
            return (PerkCard) Class.forName("model.card.perkcard." + action).getConstructors()[0].newInstance(attributeList);
        } catch (ClassNotFoundException e){
            System.out.println("Class matching name found in configuration file nonexisted!");
        } catch (IllegalAccessException e){
            System.out.println("Class matching name found in configuration file is not accessible!");
        } catch (InvocationTargetException e){
            System.out.println("Your underlying method is non-functional!");
        } catch (InstantiationException e) {
            System.out.println("Issue with object being made, check to see if parameters are appropriate!");
        }
        return null;
    }
}
