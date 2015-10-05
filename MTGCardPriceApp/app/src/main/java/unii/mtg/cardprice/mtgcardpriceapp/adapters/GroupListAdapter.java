package unii.mtg.cardprice.mtgcardpriceapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import unii.mtg.cardprice.mtgcardpriceapp.R;
import unii.mtg.cardprice.mtgcardpriceapp.database.CardGroup;

/**
 * Created by apachucy on 2015-10-05.
 */
public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.ViewHolder> {
    private ArrayList<CardGroup> mCardGroupList;

    public GroupListAdapter(ArrayList<CardGroup> cardGroupList) {
        mCardGroupList = cardGroupList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_list_name, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nameTextView.setText(mCardGroupList.get(position).getCardListName());
    }

    @Override
    public int getItemCount() {
        return mCardGroupList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.adapterList_nameTextView)
        TextView nameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
