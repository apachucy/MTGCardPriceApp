package unii.mtg.cardprice.mtgcardpriceapp.view.fragments;

import java.util.ArrayList;

/**
 * Created by apachucy on 2015-10-06.
 */
public interface IMenu {
    void addMenuItem(String itemName);
    String getMenuItem(int position);
    void removeMenuItem(String itemName);
}
