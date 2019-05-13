package model.player;

import model.card.perkcard.PerkCard;
import model.card.propertycard.PropertyCard;
import model.dice.Dice;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {

    private String myName;
    private int myPosition;
    private int myBalance;
    private Dice dice;
    private boolean inJail;
    private int turnsInJail;
    private Map<Integer, List<PropertyCard>> myPropertiesMap;
    private List<PerkCard> myPerkCards;
    private List<Player> otherPlayers;
    private boolean isCPU;
    private int lastPaidRent;

    public Player(String myName, int myPosition, int myBalance, boolean isCPU) {
        this.myName = myName;
        this.myPosition = myPosition;
        this.myBalance = myBalance;
        this.myPropertiesMap = new HashMap<>();
        this.myPerkCards = new ArrayList<>();
        this.inJail = false;
        this.otherPlayers = new ArrayList<>();
        this.isCPU = isCPU;
    }

    public String getName() {
        if (isCPU) {
            return "CPU " + myName;
        }
        return myName;
    }

    public void setOtherPlayers (List<Player> players) {
        for(Player player : players){
            if(player != this){
                this.otherPlayers.add(player);
            }
        }
    }

    public boolean haveProperty(PropertyCard property) {
        for(Map.Entry<Integer, List<PropertyCard>> entry : myPropertiesMap.entrySet()) {
            for (PropertyCard propertyCard : entry.getValue()) {
                if (propertyCard == property) {
                    return true;
                }
            }
        }
        return false;
    }

    public void addToProperties(PropertyCard property) {
        int propertyGroup = property.getPropertyGroup();
        myPropertiesMap.putIfAbsent(propertyGroup, new ArrayList<PropertyCard>());
        myPropertiesMap.get(propertyGroup).add(property);
    }

    public void removeFromProperties(PropertyCard property) {
        int propertyGroup = property.getPropertyGroup();
        myPropertiesMap.get(propertyGroup).remove(property);
    }

    public void payOtherPlayer(PropertyCard card, Player otherPlayer){
        int diceRolled = dice == null ? 0 : dice.getRolledDice().stream().mapToInt(Integer::intValue).sum();
        lastPaidRent = card.calculateRent(diceRolled,otherPlayer.getNumOwned(card));
        this.changeBalance(-1*lastPaidRent);
        otherPlayer.changeBalance(lastPaidRent);
        System.out.println(this.myName + " landed on " + otherPlayer.getName() + "'s property and paid them " +  lastPaidRent);
    }

    public int getMortageWorth() {
        int mortgagePotential = 0;
        for (List<PropertyCard> propertyCardList: myPropertiesMap.values()) {
            for (PropertyCard propertyCard : propertyCardList) {
                mortgagePotential += propertyCard.getMortgageValue();
            }
        }
        return mortgagePotential;
    }

    public boolean containsProperty(PropertyCard pc) {
        for(Map.Entry<Integer, List<PropertyCard>> entry : myPropertiesMap.entrySet()) {
            for(PropertyCard prop : entry.getValue()) {
                if(pc.equals(prop)) {
                    return true;
                }
            }
        }
        return false;
    }

    public String propertyPrint() {
        StringBuilder output = new StringBuilder();
        for(Map.Entry<Integer, List<PropertyCard>> entry : myPropertiesMap.entrySet()) {
            if(!entry.getValue().isEmpty()) {
                output.append("[" + entry.getKey() + ":");
                List<String> pm = new ArrayList<>();
                for(PropertyCard pc : entry.getValue()) {
                    pm.add(pc.toString());
                }
                output.append(String.join(", ", pm));
                output.append("]");
            }
        }
        return output.toString();
    }

    public String perkPrint() {
        StringBuilder output = new StringBuilder();
        List<String> pm = new ArrayList<>();
        for(int i = 0; i < myPerkCards.size(); i++) {
            pm.add(myPerkCards.get(i).toString());
        }
        output.append(String.join(", ", pm));
        return output.toString();
    }

    public String saveProperties() {
        StringBuilder output = new StringBuilder();
        List<String> pm = new ArrayList<>();
        for(Map.Entry<Integer, List<PropertyCard>> entry : myPropertiesMap.entrySet()) {
            if(!entry.getValue().isEmpty()) {
                for(PropertyCard pc : entry.getValue()) {
                    pm.add(pc.toString());
                }
            }
        }
        output.append(String.join(";", pm));
        return output.toString();
    }

    public String savePerks(){
        StringBuilder output = new StringBuilder();
        List<String> pm = new ArrayList<>();
        for(PerkCard pc : myPerkCards) {
            pm.add(pc.toString());
        }
        output.append(String.join(";", pm));
        return output.toString();
    }

    public void addToPerks(PerkCard perk) {
        if(perk.getAction().equalsIgnoreCase("JailFreePerkCard")){
            myPerkCards.add(perk);
        }
    }

    public int getNumProps() {
        int num = 0;
        for(Map.Entry<Integer, List<PropertyCard>> entry : myPropertiesMap.entrySet()) {
            if(!entry.getValue().isEmpty()) {
                for(PropertyCard pc : entry.getValue()) {
                    num++;
                }
            }
        }
        return num;
    }

    public int getPosition() { return myPosition; }

    public void changeBalance(int amount) { myBalance += amount; }

    public void changePosition(int newPosition) { myPosition = newPosition; }

    public void changeInJail(boolean jailStatus) { inJail = jailStatus; }

    public void changeTurnsInJail(int jailTurns) {turnsInJail = jailTurns; }

    public int getBalance() { return myBalance; }

    public boolean getInJail() { return inJail; }

    public int getTurnsInJail() { return turnsInJail; }

    public void setTurnsInJail(int turns) { turnsInJail = turns; }

    public List<PerkCard> getPerkCards() { return myPerkCards; }

    public Map<Integer, List<PropertyCard>> getPropertiesMap() { return myPropertiesMap; }

    public List<Player> getOtherPlayers() { return otherPlayers; }

    public int getTotalWealth() {
        return getMortageWorth() + getBalance();
    }

    public void removeFromPerks(PerkCard perk) { myPerkCards.remove(perk); }

    public boolean getIsCPU() { return isCPU; }

    public int getNumOwned(PropertyCard card){ return this.getPropertiesMap().get(card.getPropertyGroup()).size(); }

    public Dice getDice() { return dice; }

    public void setDice(Dice dice) { this.dice = dice; }

}
