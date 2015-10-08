package unii.mtg.cardprice.mtgcardpriceapp.view.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import unii.mtg.cardprice.mtgcardpriceapp.R;
import unii.mtg.cardprice.mtgcardpriceapp.database.CardGroup;
import unii.mtg.cardprice.mtgcardpriceapp.database.IDatabaseConnector;
import unii.mtg.cardprice.mtgcardpriceapp.sharedpreferences.SettingsPreferencesFactory;

/**
 * Created by apachucy on 2015-10-01.
 */
public class SettingsFragment extends BaseFragment {

    private IDatabaseConnector mDatabaseConnector;
    private Context mContext;
    private IMenu mMenuList;

    @OnCheckedChanged(R.id.settings_tutorialSwitch)
    void onTutorialSelected(boolean checked) {
        SettingsPreferencesFactory.getInstance().setFirstRun(checked);
    }

    @Bind(R.id.settings_tutorialSwitch)
    Switch mTutorialSwitch;
    @Bind(R.id.settings_addListEditText)
    EditText mAddListEditText;

    @OnClick(R.id.settings_addListButton)
    void onAddListButtonClicked(View view) {
        String groupName = mAddListEditText.getText().toString();
        if (!groupName.isEmpty() && checkGroupName(groupName)) {
            CardGroup cardGroup = new CardGroup();
            cardGroup.setCardListName(groupName);
            mDatabaseConnector.addList(cardGroup);
            Toast.makeText(mContext, getString(R.string.settings_list_added), Toast.LENGTH_SHORT).show();
            mAddListEditText.setText("");
            mMenuList.addMenuItem(groupName);
        } else {
            Toast.makeText(mContext, getString(R.string.settings_list_name_empty), Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.settings_pagePriceButton)
    void onChoosePagePriceClicked(View view) {

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof IDatabaseConnector) {
            mDatabaseConnector = (IDatabaseConnector) activity;
        } else {
            throw new ClassCastException("Activity must implement IDatabaseConnector");
        }
        if (activity instanceof IMenu) {
            mMenuList = (IMenu) activity;
        } else {
            throw new ClassCastException("Activity must implement IMenu");
        }
        mContext = activity;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);
        //operations on Settings View
        mTutorialSwitch.setChecked(SettingsPreferencesFactory.getInstance().getFirstRun());

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private boolean checkGroupName(String newGroupName) {
        boolean nameNotUsed = true;
        for (CardGroup cardGroup : mDatabaseConnector.getGroupList()) {
            if (cardGroup.getCardListName().equals(newGroupName)) {
                nameNotUsed = false;
                break;
            }
        }

        return nameNotUsed;
    }
}
