package unii.mtg.cardprice.mtgcardpriceapp;

import android.app.Application;

import java.util.ArrayList;

import unii.mtg.cardprice.mtgcardpriceapp.fragments.ICardPriceDraftList;
import unii.mtg.cardprice.mtgcardpriceapp.pojo.Card;


/**
 * Created by Arkadiusz Pachucy on 2015-05-04.
 */
public class AppConfig extends Application {
    public final static String APP_SHARED_PREFERENCES = "APP_SHARED_PREFERENCES" + AppConfig.class.getName();

    @Override
    public void onCreate() {
        super.onCreate();
        // SettingsPreferencesFactory.configure(new SettingsSharedPreferences(
        //        new SharedPreferencesManager().getSharedPreferences(this,
        //                APP_SHARED_PREFERENCES)));
    }

}
