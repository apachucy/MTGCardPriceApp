package unii.mtg.cardprice.mtgcardpriceapp.adapters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import unii.mtg.cardprice.mtgcardpriceapp.R;
import unii.mtg.cardprice.mtgcardpriceapp.config.StringHelper;
import unii.mtg.cardprice.mtgcardpriceapp.database.Card;

/**
 * Card price adapter - displaying card in list
 */
public class CardPriceAdapter extends RecyclerView.Adapter<CardPriceAdapter.ViewHolder> {

    private ArrayList<Card> mCardList;
    private Context mContext;
    private CardPriceAdapterMode mCardPriceAdapterMode;

    public CardPriceAdapter(Context context, ArrayList<Card> cardList, CardPriceAdapterMode cardPriceAdapterMode) {
        mCardList = cardList;
        mContext = context;
        mCardPriceAdapterMode = cardPriceAdapterMode;
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
        String cardText = "";
        if (mCardList.get(i).isFoil()) {
            cardText = mContext.getString(R.string.adapter_card_foil, mCardList.get(i).getCurrency(), StringHelper.floatFormatter(mCardList.get(i).getFoilPrice()));
        } else {
            cardText = mContext.getString(R.string.adapter_card_price, mCardList.get(i).getCurrency(), StringHelper.floatFormatter(mCardList.get(i).getMediumPrice()));
        }
        viewHolder.cardMediumPriceTextView.setText(cardText);
        viewHolder.cardIsFoilTextView.setText(mContext.getString(R.string.adapter_card_isFoil, mCardList.get(i).isFoil() + ""));
        if (CardPriceAdapterMode.DRAFT_LIST == mCardPriceAdapterMode) {
            viewHolder.mDeleteIconImageView.setVisibility(View.GONE);
        }
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

        @OnClick(R.id.cardAdapter_cardNameTextView)
        void onCardItemClicked(View view) {
            if (mCardPriceAdapterMode == CardPriceAdapterMode.CARD_LIST && mDeleteIconImageView.getVisibility() == View.VISIBLE) {
                animateStateGone(mDeleteIconImageView);
            } else if (mCardPriceAdapterMode == CardPriceAdapterMode.CARD_LIST && mDeleteIconImageView.getVisibility() == View.GONE) {
                animateStateVisible(mDeleteIconImageView);
            }
        }

        @Bind(R.id.cardAdapter_deleteIconImageView)
        ImageView mDeleteIconImageView;

        @OnClick(R.id.cardAdapter_deleteIconImageView)
        void onDeleteItemClicked(View view) {
            if (mCardPriceAdapterMode == CardPriceAdapterMode.CARD_LIST && mDeleteIconImageView.getVisibility() == View.VISIBLE) {
                //TODO: add remove option
            }
        }

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void animateStateGone(final View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // get the center for the clipping circle
            int cx = view.getWidth() / 2;
            int cy = view.getHeight() / 2;

            // get the initial radius for the clipping circle
            int initialRadius = view.getWidth();

            // create the animation (the final radius is zero)
            Animator anim =
                    ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius, 0);

            // make the view invisible when the animation is done
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    view.setVisibility(View.GONE);
                }
            });
            // start the animation
            anim.start();
        } else {
            view.setVisibility(View.GONE);
        }

    }

    private void animateStateVisible(View view) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // get the center for the clipping circle
            int cx = view.getWidth() / 2;
            int cy = view.getHeight() / 2;

            // get the final radius for the clipping circle
            int finalRadius = Math.max(view.getWidth(), view.getHeight());
            Animator anim =
                    ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);
            view.setVisibility(View.VISIBLE);
            anim.start();
        } else {
            view.setVisibility(View.VISIBLE);
        }

    }

}
