package model.configreader;

import model.board.Board;
import model.tile.*;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.lang.reflect.InvocationTargetException;

public class BoardConfig {
    private Board board = new Board();

    public Board makeBoardFromXML(File inputFile){
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList tiles = doc.getElementsByTagName("tile");

            for(int i = 0; i < tiles.getLength(); i++){
                String line = tiles.item(i).getTextContent();
                String[] lineSplit = line.split(",");
                for (int j = 0; j < lineSplit.length; j++) {
                    lineSplit[j] = lineSplit[j].trim();
                }
                Tile tile = createTile(lineSplit);
                board.addTile(tile);
            }
            return board;
        }catch (Exception e) {
            e.printStackTrace ();
        }
        return board;
    }

    public Tile createTile(String[] lineSplit) {
        String type = lineSplit[1];
        Object[] args = {lineSplit};
        try {
            return (Tile) Class.forName("model.tile." + type).getDeclaredConstructors()[0].newInstance(args);
        } catch (ClassNotFoundException e) {
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
