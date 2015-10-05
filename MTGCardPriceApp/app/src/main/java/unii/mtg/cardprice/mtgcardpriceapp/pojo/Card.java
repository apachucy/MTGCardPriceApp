package unii.mtg.cardprice.mtgcardpriceapp.pojo;

import java.io.Serializable;

/**
 * MTG Card price POJO
 */
public class Card implements Serializable{
    private String mCardName;
    private String mCurrency;
    private float mMediumPrice;
    private float mHighPrice;
    private float mLowPrice;
    private float mFoilPrice;
    private boolean isFoil;
    public Card() {
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
