package unii.mtg.cardprice.mtgcardpriceapp.sharedpreferences;


import java.util.ArrayList;

import unii.mtg.cardprice.mtgcardpriceapp.pojo.Card;

public interface ISettings {

    public void setFirstRun(boolean isFirst);

    public boolean getFirstRun();

    public void setCardList(ArrayList<Card> cardList);

    public ArrayList<Card> getCardList();

}
