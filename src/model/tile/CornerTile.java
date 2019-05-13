package model.tile;

public class CornerTile extends Tile {
    private String label;

    public CornerTile(String[] tileInfo) {
        super(tileInfo);
        label = tileInfo[6];
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel(){
        return label;
    }
}