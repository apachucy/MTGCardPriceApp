package unii.mtg.cardprice.mtgcardpriceapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import unii.mtg.cardprice.mtgcardpriceapp.R;
import unii.mtg.cardprice.mtgcardpriceapp.database.CardGroup;

/**
 * Created by apachucy on 2015-10-05.
 */
public class GroupListAdapter extends BaseAdapter {
    private ArrayList<CardGroup> mCardGroupList;

    public GroupListAdapter(ArrayList<CardGroup> cardGroupList) {
        mCardGroupList = cardGroupList;
    }


    @Override
    public int getCount() {
        return mCardGroupList.size();
    }

    @Override
    public Object getItem(int i) {
        return mCardGroupList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_list_name, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.nameTextView.setText(mCardGroupList.get(i).getCardListName());
        return view;
    }

    public static class ViewHolder {
        @Bind(R.id.adapterList_nameTextView)
        TextView nameTextView;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }
}
