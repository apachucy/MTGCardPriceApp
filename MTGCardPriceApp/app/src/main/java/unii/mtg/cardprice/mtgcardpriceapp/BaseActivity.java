package unii.mtg.cardprice.mtgcardpriceapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import unii.mtg.cardprice.mtgcardpriceapp.database.Card;
import unii.mtg.cardprice.mtgcardpriceapp.database.CardGroup;
import unii.mtg.cardprice.mtgcardpriceapp.database.Database;
import unii.mtg.cardprice.mtgcardpriceapp.database.DatabaseFactory;
import unii.mtg.cardprice.mtgcardpriceapp.database.IDatabaseConnector;

/**
 * Created by apachucy on 2015-10-05.
 */
public class BaseActivity extends AppCompatActivity implements IDatabaseConnector {

    // Reference of DatabaseHelper class to access its DAOs and other components
    protected IDatabaseConnector mDatabaseConnector = null;

    /**
     * Initialize helper
     *
     * @param context
     * @return
     */
    protected void openHelper(Context context) {
        if (DatabaseFactory.getInstance() == null) {
            DatabaseFactory.configure(new Database(context));
        }
        mDatabaseConnector = DatabaseFactory.getInstance();
        init();
    }

    private void init() {
        List<CardGroup> cardGroupList = mDatabaseConnector.getGroupList();
        if (cardGroupList == null || cardGroupList.isEmpty() || !cardGroupList.get(0).getCardListName().equals(getString(R.string.database_predefined_list))) {
            CardGroup cardGroup = new CardGroup();
            cardGroup.setCardListName(getString(R.string.database_predefined_list));
            mDatabaseConnector.addList(cardGroup);
        }
    }

    public ArrayList<Card> getCardListForGroup(int listGroupId) {
        return mDatabaseConnector.getCardListForGroup(listGroupId);

    }

    public List<CardGroup> getGroupList() {
        return mDatabaseConnector.getGroupList();
    }

    public void removeCardFromCardGroup(int cardId, int listGroupId) {
        mDatabaseConnector.removeCardFromCardGroup(cardId, listGroupId);
    }

    public void removeCardGroup(int listGroupId) {
        mDatabaseConnector.removeCardGroup(listGroupId);
    }

    @Override
    public void addList(CardGroup newCardGroup) {
        mDatabaseConnector.addList(newCardGroup);
    }

    @Override
    public void addCard(Card newCard, int listId) {
        mDatabaseConnector.addCard(newCard, listId);
    }

    @Override
    public int getGroupCardId(String groupName) {
        return mDatabaseConnector.getGroupCardId(groupName);
    }

    @Override
    public void closeDatabase() {

    }

    @Override
    public List<CardGroup> getGroupListNameWithoutCardList() {
        List<CardGroup> cardGroupList = getGroupList();
        //Remove predefined card list
        cardGroupList.remove(0);
        return cardGroupList;
    }

    @Override
    public void removeCardFromCardGroup(Card card, int listId) {
        mDatabaseConnector.removeCardFromCardGroup(card, listId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
         /*
         * You'll need this in your class to release the helper when done.
		 */
        mDatabaseConnector.closeDatabase();


    }

    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }
}
