package unii.mtg.cardprice.mtgcardpriceapp.database;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apachucy on 2015-10-05.
 */
public interface IDatabaseConnector {
    ArrayList<Card> getGroupCardList(int listGroupId);
    List<CardGroup> getGroupListName();
    void removeCardFromList(int cardId, int listGroupId);
    void removeCardGroup(int listGroupId);
    void addList(CardGroup newCardGroup);
    void addCard(Card newCard, int listId);
}
