package unii.mtg.cardprice.mtgcardpriceapp.database;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by apachucy on 2015-10-05.
 */
public class CardRelationTable implements Serializable {

    public static final String CARD_ID = "card_id";
    public static final String LIST_ID = "list_id";

    @DatabaseField(generatedId = true)
    private int mIdCardRelationTable;
    @DatabaseField(columnName = CARD_ID)
    private int mCardId;
    @DatabaseField(columnName = LIST_ID)
    private int mCardListId;

    public CardRelationTable() {
    }

    public int getCardId() {
        return mCardId;
    }

    public void setCardId(int mCardId) {
        this.mCardId = mCardId;
    }

    public int getCardListId() {
        return mCardListId;
    }

    public void setCardListId(int mCardListId) {
        this.mCardListId = mCardListId;
    }
}
