package unii.mtg.cardprice.mtgcardpriceapp.database;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by apachucy on 2015-10-07.
 */
public class Database implements IDatabaseConnector {
    // Reference of DatabaseHelper class to access its DAOs and other components
    protected DatabaseHelper mDatabaseHelper = null;

    public Database(Context context) {
        if (mDatabaseHelper == null) {
            mDatabaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
        }
    }

    public ArrayList<Card> getCardListForGroup(int listGroupId) {
        if (mDatabaseHelper == null) {
            return null;
        } else {
            ArrayList<Card> cardGroupList = new ArrayList<>();
            try {
                List<CardRelationTable> cardRelationTablesList = mDatabaseHelper.getCardRelationTableDao().queryBuilder().where().eq(CardRelationTable.LIST_ID, listGroupId).query();
                for (CardRelationTable cardRelationTables : cardRelationTablesList) {
                    Card card = mDatabaseHelper.getCardDao().queryBuilder().where().eq(Card.CARD_ID, cardRelationTables.getCardId()).queryForFirst();
                    cardGroupList.add(card);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return cardGroupList;
        }
    }

    public List<CardGroup> getGroupList() {
        if (mDatabaseHelper == null) {
            return null;
        } else {
            List<CardGroup> cardGroupList = null;
            try {
                cardGroupList = mDatabaseHelper.getCardListDao().queryForAll();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return cardGroupList;
        }
    }

    public void removeCardFromList(int cardId, int listGroupId) {
        if (mDatabaseHelper != null) {
            try {
                List<CardRelationTable> cardToBeRemovedList = mDatabaseHelper.getCardRelationTableDao().queryBuilder().where().eq(Card.CARD_ID, cardId).query();
                for (CardRelationTable cardRelationTable : cardToBeRemovedList) {
                    if (cardRelationTable.getCardListId() == listGroupId) {
                        mDatabaseHelper.getCardRelationTableDao().delete(cardRelationTable);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeCardGroup(int listGroupId) {
        if (mDatabaseHelper != null) {
            try {
                CardGroup cardGroup = mDatabaseHelper.getCardListDao().queryBuilder().where().eq(CardGroup.LIST_ID, listGroupId).queryForFirst();
                mDatabaseHelper.getCardListDao().delete(cardGroup);
                List<CardRelationTable> cardRelationTableList = mDatabaseHelper.getCardRelationTableDao().queryBuilder().where().eq(CardRelationTable.LIST_ID, cardGroup.getCardListId()).query();
                mDatabaseHelper.getCardRelationTableDao().delete(cardRelationTableList);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void addList(CardGroup newCardGroup) {
        if (mDatabaseHelper != null) {
            try {
                mDatabaseHelper.getCardListDao().create(newCardGroup);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void addCard(Card newCard, int listId) {
        if (mDatabaseHelper != null) {
            try {
                mDatabaseHelper.getCardDao().create(newCard);
                CardRelationTable cardRelationTable = new CardRelationTable();
                cardRelationTable.setCardId(newCard.getCardId());
                cardRelationTable.setCardListId(listId);
                mDatabaseHelper.getCardRelationTableDao().create(cardRelationTable);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getGroupCardId(String groupName) {
        if (mDatabaseHelper != null) {
            try {
                CardGroup cardGroup = mDatabaseHelper.getCardListDao().queryBuilder().where().like(CardGroup.LIST_NAME, groupName).queryForFirst();
                if (cardGroup != null) {
                    return cardGroup.getCardListId();
                }
                return -1;
            } catch (SQLException e) {
                e.printStackTrace();
                return -1;
            }

        }
        return -1;
    }

    @Override
    public void closeDatabase() {
        if (mDatabaseHelper != null) {
            OpenHelperManager.releaseHelper();
            mDatabaseHelper = null;
        }
    }

    @Override
    public List<CardGroup> getGroupListNameWithoutCardList() {
        //Non functional
        return null;
    }


}
