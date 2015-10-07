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

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import unii.mtg.cardprice.mtgcardpriceapp.R;
import unii.mtg.cardprice.mtgcardpriceapp.adapters.CardPriceAdapter;
import unii.mtg.cardprice.mtgcardpriceapp.adapters.DividerItemDecorator;
import unii.mtg.cardprice.mtgcardpriceapp.database.Card;
import unii.mtg.cardprice.mtgcardpriceapp.database.IDatabaseConnector;

/**
 * Created by apachucy on 2015-10-01.
 */
public class CardPriceListFragment extends BaseFragment {

    private Context mContext;
    private ICardList mCardPriceSearch;

    @Bind(R.id.cardPriceFragment_cardListRecyclerView)
    RecyclerView mCardListRecyclerView;
    @Bind(R.id.cardPriceFragment_pullToRefresh)
    MaterialRefreshLayout mMaterialRefreshLayout;

    private RecyclerView.Adapter mCardListRecyclerAdapter;
    private RecyclerView.LayoutManager mCardListLayoutManager;
    private String mCurrentOpenedListName;
    private IDatabaseConnector mDatabaseConnector;

    private ArrayList<Card> mCardList;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof ICardList) {
            mCardPriceSearch = (ICardList) activity;
            mContext = activity;
        } else {
            throw new ClassCastException("Activity must implement ICardPriceDraftList");
        }
        if (activity instanceof IDatabaseConnector) {
            mDatabaseConnector = (IDatabaseConnector) activity;
        } else {
            throw new ClassCastException("Activity must implement IDatabaseConnector");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mCurrentOpenedListName = (String) bundle.getSerializable(unii.mtg.cardprice.mtgcardpriceapp.config.Bundle.LIST_NAME_BUNDLE);
        int groupId = mDatabaseConnector.getGroupCardId(mCurrentOpenedListName);
        if (groupId > -1) {
            mCardList = mDatabaseConnector.getCardListForGroup(groupId);
        } else {
            mCardList = new ArrayList<>();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_price_list, container, false);
        ButterKnife.bind(this, view);
        mCardListRecyclerView.setHasFixedSize(true);
        mCardListLayoutManager = new LinearLayoutManager(mContext);
        mCardListRecyclerView.setLayoutManager(mCardListLayoutManager);
        mCardListRecyclerView.addItemDecoration(new DividerItemDecorator(mContext, DividerItemDecorator.VERTICAL_LIST));
        mCardListRecyclerAdapter = new CardPriceAdapter(mContext, mCardList);
        mCardListRecyclerView.setAdapter(mCardListRecyclerAdapter);

        mMaterialRefreshLayout.setMaterialRefreshListener(mMaterialRefreshListener);
        return view;
    }

    private MaterialRefreshListener mMaterialRefreshListener = new MaterialRefreshListener() {
        @Override
        public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
            //refreshing...
            //TODO: after refresh ends call: materialRefreshLayout.finishRefresh();
        }

        @Override
        public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
            //load more refreshing...
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMaterialRefreshLayout.setMaterialRefreshListener(null);
        ButterKnife.unbind(this);
    }
}
