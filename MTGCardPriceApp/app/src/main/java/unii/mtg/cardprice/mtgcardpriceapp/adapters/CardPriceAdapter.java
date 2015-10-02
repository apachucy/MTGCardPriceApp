package unii.mtg.cardprice.mtgcardpriceapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import unii.mtg.cardprice.mtgcardpriceapp.R;
import unii.mtg.cardprice.mtgcardpriceapp.pojo.Card;

/**
 * Card price adapter - displaying card in list
 */
public class CardPriceAdapter extends RecyclerView.Adapter<CardPriceAdapter.ViewHolder> {

    private ArrayList<Card> mCardList;
    private Context mContext;

    public CardPriceAdapter(Context context, ArrayList<Card> cardList) {
        mCardList = cardList;
        mContext = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_card_price, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.cardNameTextView.setText(mContext.getString(R.string.adapter_card_name, mCardList.get(i).getCardName()));
        viewHolder.cardMediumPriceTextView.setText(mContext.getString(R.string.adapter_card_price, mCardList.get(i).getCurrency(), mCardList.get(i).getMediumPrice()));
        viewHolder.cardIsFoilTextView.setText(mContext.getString(R.string.adapter_card_foil, mCardList.get(i).getCurrency(), mCardList.get(i).getFoilPrice()));
    }

    @Override
    public int getItemCount() {
        return mCardList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.cardAdapter_cardNameTextView)
        TextView cardNameTextView;
        @Bind(R.id.cardAdapter_cardMediumPriceTextView)
        TextView cardMediumPriceTextView;
        @Bind(R.id.cardAdapter_cardIsFoilTextView)
        TextView cardIsFoilTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
