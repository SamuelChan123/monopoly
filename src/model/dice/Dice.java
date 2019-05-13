package model.dice;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Dice {

    private int numberOfFaces;
    private int numberOfDie;

    private List<Integer> rolledDice;

    public Dice(int numberOfDie, int numberOfFaces) {
        this.numberOfFaces = numberOfFaces;
        this.numberOfDie = numberOfDie;
        rollDie();
    }

    public void rollDie() {

        Random rand = new Random();

        List<Integer> dieresult = new ArrayList<>();

        for (int i = 0; i < numberOfDie; i ++) {
            int randint = rand.nextInt(numberOfFaces) + 1;
            dieresult.add(randint);
        }

        rolledDice = dieresult;
    }

    public int getNumberOfDie() {
        return numberOfDie;
    }

    public int getNumberOfFaces() {
        return numberOfFaces;
    }

    public List<Integer> getRolledDice() {
        return rolledDice;
    }
}
