package unii.mtg.cardprice.mtgcardpriceapp.adapters;

import java.util.Comparator;

import unii.mtg.cardprice.mtgcardpriceapp.database.Card;

/**
 * Created by apachucy on 2015-10-06.
 */
public class CardPriceSortAscendingComparator implements Comparator<Card> {
    @Override
    public int compare(Card left, Card right) {
        if (getCardPrice(left) > getCardPrice(right)) {
            return 1;
        } else if (getCardPrice(left) < getCardPrice(right)) {
            return -1;
        } else {
            return 0;
        }

    }

    private float getCardPrice(Card card) {
        if (card.isFoil()) {
            return card.getFoilPrice();
        } else {
            return card.getMediumPrice();
        }
    }
}
