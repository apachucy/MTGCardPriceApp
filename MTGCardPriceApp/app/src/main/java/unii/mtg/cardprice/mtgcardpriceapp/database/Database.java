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


    public void removeCardGroup(int listGroupId) {
        if (mDatabaseHelper != null) {
            try {
                CardGroup cardGroup = mDatabaseHelper.getCardListDao().queryBuilder().where().eq(CardGroup.LIST_ID, listGroupId).queryForFirst();
                mDatabaseHelper.getCardListDao().delete(cardGroup);
                List<CardRelationTable> cardRelationTableList = mDatabaseHelper.getCardRelationTableDao().queryBuilder().where().eq(CardRelationTable.LIST_ID, cardGroup.getCardListId()).query();
                mDatabaseHelper.getCardRelationTableDao().delete(cardRelationTableList);
                for (CardRelationTable cardRelationTable : cardRelationTableList)//int i = 0; i < cardRelationTableList.size(); i++) {
                {
                    List<CardRelationTable> cardExistOtherInList = mDatabaseHelper.getCardRelationTableDao().queryBuilder().where().eq(CardRelationTable.CARD_ID, cardRelationTable.getCardId()).query();
                    if (cardExistOtherInList == null || cardExistOtherInList.isEmpty()) {
                        removeCard(cardRelationTable.getCardId());
                    }
                }
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
                //check db if card exist
                Card card = mDatabaseHelper.getCardDao().queryBuilder().where().like(Card.CARD_NAME, newCard.getCardName()).and().eq(Card.CARD_IS_FOIL, newCard.isFoil()).queryForFirst();

                if (card != null) {
                    //if exist do an update
                    mDatabaseHelper.getCardDao().update(card);
                } else {
                    mDatabaseHelper.getCardDao().create(newCard);
                    CardRelationTable cardRelationTable = new CardRelationTable();
                    cardRelationTable.setCardId(newCard.getCardId());
                    cardRelationTable.setCardListId(listId);
                    mDatabaseHelper.getCardRelationTableDao().create(cardRelationTable);
                }
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

    private void removeCard(int cardId) {
        try {
            mDatabaseHelper.getCardDao().deleteById(cardId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void removeCard(Card card) {
        try {
            mDatabaseHelper.getCardDao().delete(card);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeCardFromCardGroup(Card card, int listId) {
        removeCardFromCardGroup(card.getCardId(), listId);
    }

    public void removeCardFromCardGroup(int cardId, int listGroupId) {
        if (mDatabaseHelper != null) {
            try {
                CardRelationTable cardRelationTable = mDatabaseHelper.getCardRelationTableDao().queryBuilder().where().eq(CardRelationTable.CARD_ID, cardId).and().eq(CardRelationTable.LIST_ID, listGroupId).queryForFirst();
                if (cardRelationTable != null) {
                    mDatabaseHelper.getCardRelationTableDao().delete(cardRelationTable);
                    List<CardRelationTable> cardRelationTableList = mDatabaseHelper.getCardRelationTableDao().queryBuilder().where().eq(CardRelationTable.CARD_ID, cardId).query();
                    if (cardRelationTableList == null || cardRelationTableList.isEmpty()) {
                        removeCard(cardId);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
