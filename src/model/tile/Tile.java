package model.tile;

import model.player.Player;

public abstract class Tile {

    private int position;
    private String type;
    private String text;
    private int propertyGroup;
    private int groupNum;
    private String image;
    private String color;

    public Tile(String[] tileInfo) {
        this.position = Integer.parseInt(tileInfo[0]);
        this.type = tileInfo[1];
        this.text = tileInfo[2];
        this.propertyGroup = Integer.parseInt(tileInfo[3]);
        this.groupNum = Integer.parseInt(tileInfo[4]);
        this.image = tileInfo[5];
    }

    public int getPosition() {return position;}

    public String getType() {return type;}

    public String getText() {return text;}

    public String getImage() {return image;}

    public int getPropertyGroup() {return propertyGroup;}

    public int getGroupNum() {return groupNum;}

    public void performAction(Player player){}

    public void setColor(String colorString) {
        this.color = colorString;
    }

    public String getColor() {
        return color;
    }
}