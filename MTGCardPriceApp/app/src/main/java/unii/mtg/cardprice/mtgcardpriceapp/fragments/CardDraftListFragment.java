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

import java.util.ArrayList;
import java.util.Collections;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import unii.mtg.cardprice.mtgcardpriceapp.R;
import unii.mtg.cardprice.mtgcardpriceapp.adapters.CardPriceAdapter;
import unii.mtg.cardprice.mtgcardpriceapp.adapters.CardPriceAdapterMode;
import unii.mtg.cardprice.mtgcardpriceapp.adapters.CardPriceSortDescendingComparator;
import unii.mtg.cardprice.mtgcardpriceapp.adapters.CardPriceSortAscendingComparator;
import unii.mtg.cardprice.mtgcardpriceapp.adapters.DividerItemDecorator;
import unii.mtg.cardprice.mtgcardpriceapp.database.Card;

/**
 * Created by apachucy on 2015-10-06.
 */
public class CardDraftListFragment extends BaseFragment {
    private Context mContext;
    private ICardPriceDraftList mCardPriceDraftList;
    private ArrayList<Card> mDraftCardList;
    @Bind(R.id.cardPriceFragment_cardListRecyclerView)
    RecyclerView mCardListRecyclerView;

    @OnClick(R.id.cardPriceFragment_sortAscendingTextView)
    void onSortAscendingClicked(View view) {
        Collections.sort(mDraftCardList, new CardPriceSortAscendingComparator());
        mCardListRecyclerAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.cardPriceFragment_sortDescendingTextView)
    void onSortDescendingClicked(View view) {
        Collections.sort(mDraftCardList, new CardPriceSortDescendingComparator());
        mCardListRecyclerAdapter.notifyDataSetChanged();
    }

    private RecyclerView.Adapter mCardListRecyclerAdapter;
    private RecyclerView.LayoutManager mCardListLayoutManager;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof ICardPriceDraftList) {
            mCardPriceDraftList = (ICardPriceDraftList) activity;
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

        mDraftCardList = mCardPriceDraftList.getDraftCardList();
        mCardListRecyclerAdapter = new CardPriceAdapter(mContext, mDraftCardList, CardPriceAdapterMode.DRAFT_LIST);
        mCardListRecyclerView.setAdapter(mCardListRecyclerAdapter);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
