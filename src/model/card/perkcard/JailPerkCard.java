package model.card.perkcard;

import model.tile.JailTile;

import java.util.List;

public class JailPerkCard extends PerkCard {
    private JailTile jailTile = new JailTile(new String[]{"1","1","1","1","1","1","1"});

    public JailPerkCard(List<String> attributeList) {
        super(attributeList);
    }

    public void setJailTile(JailTile tile){
        jailTile = tile;
    }

    public JailTile getJailTile(){
        return jailTile;
    }
}