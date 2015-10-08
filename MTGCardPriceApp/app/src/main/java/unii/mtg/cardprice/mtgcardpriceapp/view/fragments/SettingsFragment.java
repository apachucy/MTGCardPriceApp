package unii.mtg.cardprice.mtgcardpriceapp.view.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import unii.mtg.cardprice.mtgcardpriceapp.R;
import unii.mtg.cardprice.mtgcardpriceapp.database.Card;
import unii.mtg.cardprice.mtgcardpriceapp.database.CardGroup;
import unii.mtg.cardprice.mtgcardpriceapp.database.IDatabaseConnector;
import unii.mtg.cardprice.mtgcardpriceapp.sharedpreferences.SettingsPreferencesFactory;
import unii.mtg.cardprice.mtgcardpriceapp.view.adapters.GroupListAdapter;

/**
 * Created by apachucy on 2015-10-01.
 */
public class SettingsFragment extends BaseFragment {

    private IDatabaseConnector mDatabaseConnector;
    private Context mContext;
    private IMenu mMenuList;
    private ArrayList<CardGroup> mCardGroupList;
    private GroupListAdapter mGroupListAdapter;

    //TODO: add remove custom list!
    @OnCheckedChanged(R.id.settings_tutorialSwitch)
    void onTutorialSelected(boolean checked) {
        SettingsPreferencesFactory.getInstance().setFirstRun(checked);
    }

    @Bind(R.id.settings_tutorialSwitch)
    Switch mTutorialSwitch;
    @Bind(R.id.settings_addListEditText)
    EditText mAddListEditText;
    @Bind(R.id.settings_removeListSpinner)
    Spinner mRemoveSpinner;

    @OnClick(R.id.settings_addListButton)
    void onAddListButtonClicked(View view) {
        String groupName = mAddListEditText.getText().toString();
        if (!groupName.isEmpty() && checkGroupName(groupName)) {
            CardGroup cardGroup = new CardGroup();
            cardGroup.setCardListName(groupName);
            mDatabaseConnector.addList(cardGroup);
            Toast.makeText(mContext, getString(R.string.settings_list_added_info), Toast.LENGTH_SHORT).show();
            mAddListEditText.setText("");
            mMenuList.addMenuItem(groupName);
            if (mCardGroupList.get(0).getCardListName().equals(getString(R.string.empty_list))) {
                mCardGroupList.remove(0);
                mCardGroupList.add(cardGroup);
                mRemoveSpinner.setSelection(0);
            } else {
                mCardGroupList.add(cardGroup);
            }
            mGroupListAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(mContext, getString(R.string.settings_list_name_empty), Toast.LENGTH_SHORT).show();
        }
    }


    @OnClick(R.id.settings_removeListButton)
    void onChooseRemoveList(View view) {
        if (!mRemoveSpinner.getAdapter().isEmpty() && !mCardGroupList.get(0).getCardListName().equals(getString(R.string.empty_list))) {
            CardGroup selectedCardGroup = (CardGroup) mRemoveSpinner.getSelectedItem();
            mDatabaseConnector.removeCardGroup(selectedCardGroup.getCardListId());
            mCardGroupList.remove(selectedCardGroup);
            if (mCardGroupList.isEmpty()) {
                mCardGroupList.add(predefinedCardGroup());
            }
            mMenuList.removeMenuItem(selectedCardGroup.getCardListName());
            mGroupListAdapter.notifyDataSetChanged();
            Toast.makeText(mContext, getString(R.string.settings_list_remove_info), Toast.LENGTH_SHORT).show();
        } else {

            Toast.makeText(mContext, getString(R.string.settings_list_name_cannot_remove), Toast.LENGTH_SHORT).show();
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCardGroupList = new ArrayList<>(mDatabaseConnector.getGroupListNameWithoutCardList());
        if (mCardGroupList.isEmpty()) {
            mCardGroupList.add(predefinedCardGroup());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);
        //operations on Settings View
        mTutorialSwitch.setChecked(SettingsPreferencesFactory.getInstance().getFirstRun());

        mGroupListAdapter = new GroupListAdapter(mCardGroupList);
        mRemoveSpinner.setAdapter(mGroupListAdapter);
        return view;
    }

    private CardGroup predefinedCardGroup() {
        CardGroup cardGroup = new CardGroup();
        cardGroup.setCardListName(getString(R.string.empty_list));
        return cardGroup;

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
