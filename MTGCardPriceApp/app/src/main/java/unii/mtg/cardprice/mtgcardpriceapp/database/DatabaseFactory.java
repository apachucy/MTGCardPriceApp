package unii.mtg.cardprice.mtgcardpriceapp.database;

import unii.mtg.cardprice.mtgcardpriceapp.sharedpreferences.ISettings;

/**
 * Created by apachucy on 2015-10-07.
 */
public class DatabaseFactory {
    private static IDatabaseConnector sInstance;

    public static void configure(IDatabaseConnector instance) {
        sInstance = instance;
    }

    public static IDatabaseConnector getInstance() {
        return sInstance;
    }
}
