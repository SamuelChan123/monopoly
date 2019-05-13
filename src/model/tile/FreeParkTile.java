package model.tile;

import model.player.Player;

public class FreeParkTile extends CornerTile {
    private int parkingMoney;

    public FreeParkTile(String[] tileInfo) {
        super(tileInfo);
        parkingMoney = 0;
    }

    @Override
    public void performAction(Player player){
        player.changeBalance(this.getParkingMoney());
        System.out.println(player.getName() + " landed on Free Parking and got " + this.getParkingMoney());
        parkingMoney = 0;
    }

    public void changeParkingMoney(int amount){
        parkingMoney += amount;
    }

    public int getParkingMoney(){
        return parkingMoney;
    }
}