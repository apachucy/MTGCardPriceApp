package unii.mtg.cardprice.mtgcardpriceapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import unii.mtg.cardprice.mtgcardpriceapp.R;
import unii.mtg.cardprice.mtgcardpriceapp.adapters.CardPriceAdapter;
import unii.mtg.cardprice.mtgcardpriceapp.adapters.DividerItemDecorator;

/**
 * Created by apachucy on 2015-10-01.
 */
public class CardPriceListFragment extends BaseFragment {

    private Context mContext;
    private ICardPriceDraftList mCardPriceSearch;

    @Bind(R.id.cardPriceFragment_cardListRecyclerView)
    RecyclerView mCardListRecyclerView;

    private RecyclerView.Adapter mCardListRecyclerAdapter;
    private RecyclerView.LayoutManager mCardListLayoutManager;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof ICardPriceDraftList) {
            mCardPriceSearch = (ICardPriceDraftList) activity;
            mContext = activity;
        } else {
            throw new ClassCastException("Activity must implement ICardPriceDraftList");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_price_draft_list, container, false);
        ButterKnife.bind(this, view);
        mCardListRecyclerView.setHasFixedSize(true);
        mCardListLayoutManager = new LinearLayoutManager(mContext);
        mCardListRecyclerView.setLayoutManager(mCardListLayoutManager);
        mCardListRecyclerView.addItemDecoration(new DividerItemDecorator(mContext, DividerItemDecorator.VERTICAL_LIST));
        mCardListRecyclerAdapter = new CardPriceAdapter(mContext, mCardPriceSearch.getDraftCardList());
        mCardListRecyclerView.setAdapter(mCardListRecyclerAdapter);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
