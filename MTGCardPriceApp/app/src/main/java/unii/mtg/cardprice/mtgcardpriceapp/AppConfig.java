package unii.mtg.cardprice.mtgcardpriceapp;

import android.app.Application;

import java.util.ArrayList;

import unii.mtg.cardprice.mtgcardpriceapp.database.Card;
import unii.mtg.cardprice.mtgcardpriceapp.view.fragments.ICardPriceDraftList;
import unii.mtg.cardprice.mtgcardpriceapp.sharedpreferences.SettingsPreferencesFactory;
import unii.mtg.cardprice.mtgcardpriceapp.sharedpreferences.SettingsSharedPreferences;
import unii.mtg.cardprice.mtgcardpriceapp.sharedpreferences.SharedPreferencesManager;


/**
 * Created by Arkadiusz Pachucy on 2015-05-04.
 */
public class AppConfig extends Application implements ICardPriceDraftList{
    public final static String APP_SHARED_PREFERENCES = "APP_SHARED_PREFERENCES" + AppConfig.class.getName();
    private ArrayList<Card> mDraftCardList = new ArrayList<>();
    @Override
    public void onCreate() {
        super.onCreate();
        SettingsPreferencesFactory.configure(new SettingsSharedPreferences(
                new SharedPreferencesManager().getSharedPreferences(this,
                        APP_SHARED_PREFERENCES)));
    }

    @Override
    public ArrayList<Card> getDraftCardList() {
        return mDraftCardList;
    }
}
