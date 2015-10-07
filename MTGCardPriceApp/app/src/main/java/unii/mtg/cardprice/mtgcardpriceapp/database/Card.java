package unii.mtg.cardprice.mtgcardpriceapp.database;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apachucy on 2015-10-05.
 */
public class Card implements Serializable {
    public static final String CARD_ID = "card_id";

    @DatabaseField(generatedId = true, columnName = CARD_ID)
    private int mCardId;
    @DatabaseField(columnName = "card_name")
    private String mCardName;
    @DatabaseField(columnName = "card_currency")
    private String mCurrency;
    @DatabaseField(columnName = "card_price_medium")
    private float mMediumPrice;
    @DatabaseField(columnName = "card_price_high")
    private float mHighPrice;
    @DatabaseField(columnName = "card_price_low")
    private float mLowPrice;
    @DatabaseField(columnName = "card_price_foil")
    private float mFoilPrice;
    @DatabaseField(columnName = "card_is_foil")
    private boolean isFoil;

    //  @DatabaseField(columnName = "card_list", canBeNull = false, foreign = true, foreignAutoRefresh = true)
    //List<CardGroup> mCardList;
    public Card() {
    }

    public Card(Card card) {
        mCardId = card.getCardId();
        mCardName = card.getCardName();
        mCurrency = card.getCurrency();
        mMediumPrice = card.getMediumPrice();
        mHighPrice = card.getHighPrice();
        mLowPrice = card.getLowPrice();
        mFoilPrice = card.getFoilPrice();
        isFoil = card.isFoil();
    }

    public int getCardId() {
        return mCardId;
    }

    public void setCardId(int mCardId) {
        this.mCardId = mCardId;
    }

    public String getCardName() {
        return mCardName;
    }

    public void setCardName(String mCardName) {
        this.mCardName = mCardName;
    }

    public String getCurrency() {
        return mCurrency;
    }

    public void setCurrency(String mCurrency) {
        this.mCurrency = mCurrency;
    }

    public float getMediumPrice() {
        return mMediumPrice;
    }

    public void setMediumPrice(float mMediumPrice) {
        this.mMediumPrice = mMediumPrice;
    }

    public float getHighPrice() {
        return mHighPrice;
    }

    public void setHighPrice(float mHighPrice) {
        this.mHighPrice = mHighPrice;
    }

    public float getLowPrice() {
        return mLowPrice;
    }

    public void setLowPrice(float mLowPrice) {
        this.mLowPrice = mLowPrice;
    }

    public float getFoilPrice() {
        return mFoilPrice;
    }

    public void setFoilPrice(float mFoilPrice) {
        this.mFoilPrice = mFoilPrice;
    }

    public boolean isFoil() {
        return isFoil;
    }

    public void setIsFoil(boolean isFoil) {
        this.isFoil = isFoil;
    }
}
