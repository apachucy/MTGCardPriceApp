package unii.mtg.cardprice.mtgcardpriceapp.sharedpreferences;


import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import unii.mtg.cardprice.mtgcardpriceapp.pojo.Card;

public class SettingsSharedPreferences implements ISettings {
    private SharedPreferences mSharedPreferences;

    private static final String FLAG_FIRST_RUN = "FLAG_FIRST_RUN" + SettingsSharedPreferences.class.getName();
    private static final String FLAG_SAVE_CARD_LIST = "FLAG_SAVE_CARD_LIST_SIZE" + SettingsSharedPreferences.class.getName();

    public SettingsSharedPreferences(SharedPreferences shPref) {
        mSharedPreferences = shPref;
    }


    @Override
    public void setFirstRun(boolean isFirst) {
        mSharedPreferences.edit().putBoolean(FLAG_FIRST_RUN, isFirst).commit();
    }

    @Override
    public boolean getFirstRun() {
        return mSharedPreferences.getBoolean(FLAG_FIRST_RUN, true);

    }

    @Override
    public void setCardList(ArrayList<Card> cardList) {
     //TODO

    }

    @Override
    public ArrayList<Card> getCardList() {
        //TODO
        return null;
    }

}
