package unii.mtg.cardprice.mtgcardpriceapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import unii.mtg.cardprice.mtgcardpriceapp.R;
import unii.mtg.cardprice.mtgcardpriceapp.pojo.Card;


public class CardPriceFragment extends BaseFragment {

    private Context mContext;

    private ICardPriceDraftList mCardPriceDraftList;
    private ICardList mCardList;

    private Card mCardPrice;
    @Bind(R.id.singleCard_priceHighTextView)
    TextView mPriceHighTextView;
    @Bind(R.id.singleCard_priceMediumTextView)
    TextView mPriceMediumTextView;
    @Bind(R.id.singleCard_priceLowTextView)
    TextView mPriceLowTextView;
    @Bind(R.id.singleCard_priceFoilTextView)
    TextView mPriceFoilTextView;

    @OnClick(R.id.singleCard_addDraftList)
    void onAddItemDraftList(View view) {
        mCardPriceDraftList.getDraftCardList().add(mCardPrice);
    }

    @OnClick(R.id.singleCard_addCardList)
    void onAddItemCardList(View view) {
        mCardList.getCardList().add(mCardPrice);
    }

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
        if (activity instanceof ICardList) {
            mCardList = (ICardList) activity;
        } else {
            throw new ClassCastException("Activity must implement ICardList");
        }
        mContext = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_price, container, false);
        ButterKnife.bind(this, view);
        if (!(mCardPrice == null || mCardPrice.getCardName().isEmpty())) {
            mPriceHighTextView.setText(getString(R.string.single_card_price_high, mCardPrice.getHighPrice(), mCardPrice.getCurrency()));
            mPriceMediumTextView.setText(getString(R.string.single_card_price_medium, mCardPrice.getMediumPrice(), mCardPrice.getCurrency()));
            mPriceLowTextView.setText(getString(R.string.single_card_price_low, mCardPrice.getLowPrice(), mCardPrice.getCurrency()));
            mPriceFoilTextView.setText(getString(R.string.single_card_price_foil, mCardPrice.getFoilPrice(), mCardPrice.getCurrency()));
        }
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
