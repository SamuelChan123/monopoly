package model.card.perkcard;

import model.player.Player;

import java.util.List;

public abstract class PerkCard {

    private String type;
    private String text;
    private String action;
    private int changeFund;
    private int move;

    public PerkCard(List<String> attributeList) {
        this.type = attributeList.get(0);
        this.text = attributeList.get(1);
        this.action = attributeList.get(2);
        this.changeFund = Integer.parseInt(attributeList.get(3));
        this.move = Integer.parseInt(attributeList.get(4));
    }

    public String getType(){return type;}

    public String getText(){return text;}

    public String getAction(){return action;}

    public int getChangeFund(){return changeFund;}

    public int getMove() {return move;}

    public void perkCardAction(Player player){}

    @Override
    public String toString() {
        return text;
    }
}