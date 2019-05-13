package model.tests.dicetests;

import model.dice.Dice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiceTest {
    Dice testDie;
    List rollList;

    @BeforeEach
    public void setup(){
        testDie = new Dice(4,10);
        testDie.rollDie();
        rollList = testDie.getRolledDice();
    }

    @Test
    public void rollDieReturnsExpectedSizeList(){
        int expected = 4;
        int actual = rollList.size();
        assertEquals(expected,actual);
    }

    @Test
    public void rollDieReturnsNumbersInExpectedInterval(){
        int expected = 4;
        for(int i = 0; i < rollList.size(); i++){
            int rollInt = (int) rollList.get(i);
            if(rollInt == 0 || (rollInt > testDie.getNumberOfFaces())){
                rollList.remove(i);
            }
        }
        int actual = rollList.size();
        assertEquals(expected,actual);
    }
}
