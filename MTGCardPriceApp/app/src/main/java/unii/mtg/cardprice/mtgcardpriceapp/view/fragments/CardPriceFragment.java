package unii.mtg.cardprice.mtgcardpriceapp.view.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import unii.mtg.cardprice.mtgcardpriceapp.R;
import unii.mtg.cardprice.mtgcardpriceapp.view.adapters.GroupListAdapter;
import unii.mtg.cardprice.mtgcardpriceapp.config.StringHelper;
import unii.mtg.cardprice.mtgcardpriceapp.database.Card;
import unii.mtg.cardprice.mtgcardpriceapp.database.CardGroup;
import unii.mtg.cardprice.mtgcardpriceapp.database.IDatabaseConnector;


public class CardPriceFragment extends BaseFragment {

    private Context mContext;
    private IDatabaseConnector mDatabaseConnector;
    private ICardPriceDraftList mCardPriceDraftList;
    private Card mCardPrice;

    private GroupListAdapter mGroupListAdapter;

    private ArrayList<CardGroup> mCardGroupList;

    @Bind(R.id.singleCard_priceHighTextView)
    TextView mPriceHighTextView;
    @Bind(R.id.singleCard_priceMediumTextView)
    TextView mPriceMediumTextView;
    @Bind(R.id.singleCard_priceLowTextView)
    TextView mPriceLowTextView;
    @Bind(R.id.singleCard_priceFoilTextView)
    TextView mPriceFoilTextView;
    @Bind(R.id.singleCard_cardNameTextView)
    TextView mCartNameTextView;

    @Bind(R.id.singleCard_isFoilCheckBox)
    CheckBox mIsFoilCheckBox;

    @OnCheckedChanged(R.id.singleCard_isFoilCheckBox)
    void onFoilChecked(boolean checked) {
        mCardPrice.setIsFoil(checked);
    }

    @OnClick(R.id.singleCard_addCardToListButton)
    void onAddButtonClicked(View view) {
        mDatabaseConnector.addCard(mCardPrice, ((CardGroup) (mCustomListSpinner.getSelectedItem())).getCardListId());
        Toast.makeText(mContext, getString(R.string.single_card_toast_info), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.singleCard_addDraftList)
    void onAddItemDraftList(View view) {
        mCardPriceDraftList.getDraftCardList().add(new Card(mCardPrice));
        Toast.makeText(mContext, getString(R.string.single_card_toast_info), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.singleCard_addCardList)
    void onAddItemCardList(View view) {
        mDatabaseConnector.addCard(mCardPrice, mDatabaseConnector.getGroupCardId(getString(R.string.database_predefined_list)));
        Toast.makeText(mContext, getString(R.string.single_card_toast_info), Toast.LENGTH_SHORT).show();
    }

    @Bind(R.id.singleCard_customListSpinner)
    Spinner mCustomListSpinner;

    @Bind(R.id.singleCard_customListHolder)
    LinearLayout mCustomListHolder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mCardPrice = (Card) bundle.getSerializable(unii.mtg.cardprice.mtgcardpriceapp.config.Bundle.CARD_BUNDLE);


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof ICardPriceDraftList) {
            mCardPriceDraftList = (ICardPriceDraftList) activity;
        } else {
            throw new ClassCastException("Activity must implement ICardPriceDraftList");
        }

        if (activity instanceof IDatabaseConnector) {
            mDatabaseConnector = (IDatabaseConnector) activity;
        } else {
            throw new ClassCastException("Activity must implement IDatabaseConnector");
        }
        mContext = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_price, container, false);
        ButterKnife.bind(this, view);
        if (mCardPrice != null && mCardPrice.getCardName() != null && !mCardPrice.getCardName().isEmpty()) {
            mPriceHighTextView.setText(getString(R.string.single_card_price_high, StringHelper.floatFormatter(mCardPrice.getHighPrice()), mCardPrice.getCurrency()));
            mPriceMediumTextView.setText(getString(R.string.single_card_price_medium, StringHelper.floatFormatter(mCardPrice.getMediumPrice()), mCardPrice.getCurrency()));
            mPriceLowTextView.setText(getString(R.string.single_card_price_low, StringHelper.floatFormatter(mCardPrice.getLowPrice()), mCardPrice.getCurrency()));
            mPriceFoilTextView.setText(getString(R.string.single_card_price_foil, StringHelper.floatFormatter(mCardPrice.getFoilPrice()), mCardPrice.getCurrency()));
            mIsFoilCheckBox.setChecked(mCardPrice.isFoil());
            mCartNameTextView.setText(getString(R.string.single_card_name, mCardPrice.getCardName()));
        }
        mCardGroupList = new ArrayList<>(mDatabaseConnector.getGroupListNameWithoutCardList());
        if (mCardGroupList.isEmpty()) {
            mCustomListHolder.setVisibility(View.GONE);
        } else {
            mCustomListHolder.setVisibility(View.VISIBLE);
            mGroupListAdapter = new GroupListAdapter(mCardGroupList);
            mCustomListSpinner.setAdapter(mGroupListAdapter);
        }

        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
