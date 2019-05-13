package model.tests.configreadertests;

import model.board.Board;
import model.configreader.BoardConfig;
import model.tile.GoTile;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardConfigTest {

        BoardConfig testSpace = new BoardConfig();
        File file = new File("data/GameTestFiles/TilesLayoutTest.xml");

        @Test
        void createTileMakesRightTileType(){
            String[] lineSplit = {"1","GoTile","BOB","0","0","0","0"};
            int expected = new GoTile(lineSplit).getPosition();
            int actual = testSpace.createTile(lineSplit).getPosition();
            String expected2 = "BOB";
            String actual2 = testSpace.createTile(lineSplit).getText();
            assertEquals(expected,actual);
        }

        @Test
        void makeBoardFromFileCorrectTiles(){
            String expected = "1 GoTile 2 RealEstateTile 3 CommunityPerkTile 4 RealEstateTile 5 TaxTile";
            String actual = "";
            Board testBoard = testSpace.makeBoardFromXML(file);
            for (int i = 0; i < testBoard.getBoardMap().size(); i++){
                actual += (i + 1) + " " + testBoard.getBoardMap().get(i).getType() + " ";
            }
            actual = actual.trim();
            assertEquals(expected,actual);
        }
}

