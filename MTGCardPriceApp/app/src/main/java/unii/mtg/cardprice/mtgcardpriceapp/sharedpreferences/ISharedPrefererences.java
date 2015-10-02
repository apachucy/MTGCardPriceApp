package unii.mtg.cardprice.mtgcardpriceapp.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

public interface ISharedPrefererences {

	
public SharedPreferences getSharedPreferences(Context context, String name);
}
