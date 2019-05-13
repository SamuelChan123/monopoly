package model.card.perkcard;

import java.util.List;

public class PayToPlayersPerkCard extends NumPlayersPerkCard {
    public PayToPlayersPerkCard(List<String> attributeList) {
        super(attributeList);
    }

    @Override
    public int setAmount(int amount) {
        return super.setAmount(-1*amount);
    }
}