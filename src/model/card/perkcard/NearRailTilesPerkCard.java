package model.card.perkcard;

import java.util.List;

public class NearRailTilesPerkCard extends NearTilesPerkCard{
    public NearRailTilesPerkCard(List<String> attributeList) {
        super(attributeList);
        type = "RailroadTile";
    }
}