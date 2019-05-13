package model.tile;

import model.card.perkcard.PerkCard;
import model.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PerkTile extends Tile {

    private PerkCard lastDrawnCard;

    List<PerkCard> deck = new ArrayList<>();
    public PerkTile(String[] tileInfo) {
        super(tileInfo);
    }

    public List<PerkCard> sortPerkCards(String type, List<PerkCard> perkCardList){
        List<PerkCard> tempPerkCardList = new ArrayList<>();
        for(int i = 0; i < perkCardList.size(); i++){
            PerkCard perkCard = perkCardList.get(i);
            if(perkCard.getType().equals(type)){
                tempPerkCardList.add(perkCard);
            }
        }
        return tempPerkCardList;
    }

    public void drawCard(Player player, List<PerkCard> perkCardList){
        if(!perkCardList.isEmpty()){
            Collections.shuffle(perkCardList);
            PerkCard perkCardDrawn = perkCardList.get(0);
            System.out.println(player.getName() + " has drawn " + perkCardDrawn.getType() + " card " + perkCardDrawn.getText());
            perkCardDrawn.perkCardAction(player);
            perkCardList.add(perkCardDrawn);
            lastDrawnCard = perkCardDrawn;
        }
    }

    public void setDeck(List<PerkCard> list){
        deck = list;
    }

    public List<PerkCard> getDeck(){
        return deck;
    }

    public String getDrawnCardContent() {
        return lastDrawnCard.getText();
    }
}