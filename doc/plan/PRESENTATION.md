### Implementation Plan

## Delegation of work:

**Model**
Eugene (Perk, Tiles, Dice)
Sam (Player (Human, CPU), Board, Bank)
Caleb (ConfigurationReader, Data, Properties, Set Up)

**View**
Amik (Tiles, Player)
Kiori (Buttons, Dice)
Jen (Splash Screen, Board)

**Controller** - everyone
Caleb/Amik/Eugene 

** Challenge Features:** Auctions/Trading, Player Profiles, Saving games

## Sprint :

**Sprint 1**
* Being able to start and continue games (multiple players, taking turns, rolling die, choosing options)
* Make working visuals and GUI
* Setting up configurations/csv/properties files
* Working backbone with basic working UI buttons 
* Setting up Splash Screens and setting up game parameters
* Working win/loss conditions

**Sprint 2**
* Full working original 40-tile game with modifiable parameters
* Working loadable implementations of 1-2 other game variations (house rules, triopoly... etc)
* Interactive game GUI (board, properties, changing settings)
* Finishing up special features of the game (rolling doubles, free parking variations, user options (tokens, jail options... etc))
* Setting up CPU players and conditional decisions

**Final Sprint**
* Debugging, extra credit, cheat codes, easter eggs, beautification 
* Auctions/Trading, Player Profiles, Saving games
* Working 3+ multiple games
* Wrapping up and adding new features

## APIs

```java
package model.dice;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Dice {

    private int numberOfFaces;
    private int numberOfDie;

    public Dice(int numberOfFaces, int numberOfDie) {
        this.numberOfFaces = numberOfFaces;
        this.numberOfDie = numberOfDie;
    }

    public List<Integer> rollDie() {

        Random rand = new Random();

        List<Integer> dieresult = new ArrayList<>();

        for (int i = 0; i < numberOfDie; i ++) {
            int randint = rand.nextInt(numberOfFaces) + 1;
            dieresult.add(randint);
        }

        return dieresult;

    }

}
```

```java
package model.player;

import model.card.perkcard.PerkCard;
import model.card.propertycard.PropertyCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Player {

    private String myName;
    private int myPosition;
    private int myBalance;
    private Map<Integer, List<PropertyCard>> myPropertiesMap;
    private List<PerkCard> myPerkCards;

    public Player(String myName, int myPosition, int myBalance) {
        this.myName = myName;
        this.myPosition = myPosition;
        this.myBalance = myBalance;
    }

    public void changeFunds(int amount) {
        myBalance += amount;
    }

    public int getMyBalance() {
        return myBalance;
    }

    public List<PerkCard> getMyPerkCards() {
        return myPerkCards;
    }

    public Map<Integer, List<PropertyCard>> getMyPropertiesMap() {
        return myPropertiesMap;
    }

    public void addToProperties(PropertyCard property) {
        int propertyGroup = property.getPropertyGroup();
        myPropertiesMap.putIfAbsent(propertyGroup, new ArrayList<PropertyCard>());
        myPropertiesMap.get(propertyGroup).add(property);
    }

    public void removeFromProperties(PropertyCard property) {
        int propertyGroup = property.getPropertyGroup();
        myPropertiesMap.get(propertyGroup).remove(property);
        //account for exception when there the property isn't owned
    }

    public void addToPerks(PerkCard perk) {
        myPerkCards.add(perk);
    }

    public void removeFromPerks(PerkCard perk) {
        myPerkCards.remove(perk);
    }

    //add/remove methods could possibly return the object (so it can be added/transferred elsewhere

}
```

## Use Cases

**Player lands on Property**
* Call Model to: check if Property card in Tile
- Remove from unsoldPropertyMap, update Player's propertyMap
* Call View to: add PropertyView to TileView

**Roll dice clicked**
* Call Model to: rollDice() and returns ArrayList
* Call View to: update the DiceView
