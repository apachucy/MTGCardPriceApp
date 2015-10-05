package unii.mtg.cardprice.mtgcardpriceapp.database;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by apachucy on 2015-10-05.
 */
public class CardGroup implements Serializable {
    public static final String LIST_ID = "list_id";
    public static final String LIST_NAME = "list_name";
    @DatabaseField(generatedId = true, columnName = LIST_ID)
    private int mCardListId;
    @DatabaseField(columnName = LIST_NAME)
    private String mCardListName;

    public CardGroup() {
    }

    public int getCardListId() {
        return mCardListId;
    }

    public void setCardListId(int mCardListId) {
        this.mCardListId = mCardListId;
    }

    public String getCardListName() {
        return mCardListName;
    }

    public void setCardListName(String mCardListName) {
        this.mCardListName = mCardListName;
    }
}
