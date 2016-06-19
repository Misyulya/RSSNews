package com.misyulya.rssnewsapplication.adapters;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.misyulya.rssnewsapplication.R;
import com.misyulya.rssnewsapplication.models.RssItem;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by 1 on 30.05.2016.
 */
public class RssItemRecyclerViewAdapter extends RecyclerView.Adapter<RssItemRecyclerViewAdapter.ViewHolder> {

    private final LongClickWithPosition mLongClick;
    private ArrayList<RssItem> mRssItems;
    private SparseBooleanArray mSelectedItems;


    public RssItemRecyclerViewAdapter(Collection<RssItem> rssItems, LongClickWithPosition longClick) {
        mRssItems = (ArrayList<RssItem>) rssItems;
        mLongClick = longClick;
        mSelectedItems = new SparseBooleanArray();
    }

    public void toggleSelection(int pos) {
        if (mSelectedItems.get(pos, false)) {
            mSelectedItems.delete(pos);
        } else {
            mSelectedItems.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    public void clearSelections() {
        mSelectedItems.clear();
        notifyDataSetChanged();
    }

    public int getSelectedItemCount() {
        return mSelectedItems.size();
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items =
                new ArrayList<Integer>(mSelectedItems.size());
        for (int i = 0; i < mSelectedItems.size(); i++) {
            items.add(mSelectedItems.keyAt(i));
        }
        return items;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rssItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.rss_item, parent, false);
        ViewHolder holder = new ViewHolder(rssItem);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RssItem rssItem = mRssItems.get(position);
        holder.setContent(rssItem, mSelectedItems.get(position));
    }


    @Override
    public int getItemCount() {
        return mRssItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        private final TextView mTitleTextView;
        private final TextView mDescriptionTextView;
        private final ImageView mImage;
        private final String IMAGE_URI = "http://pics04.loveplanet.ru/7/foto/63/27/63275bed/eCHNXDEIoHlMHaBZSCA==_.jpg?p=s_";
        private final View mItemView;


        public ViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            mTitleTextView = (TextView) itemView.findViewById(R.id.title_textView);
            mDescriptionTextView = (TextView) itemView.findViewById(R.id.description_textView);
            mImage = (ImageView) itemView.findViewById(R.id.image_view);
            itemView.setOnLongClickListener(this);
        }

        public void setContent(RssItem rssItem, boolean isSelected) {
            mTitleTextView.setText(rssItem.getmTitle());
            mDescriptionTextView.setText(rssItem.getmDescription());
            ImageLoader.getInstance().displayImage(IMAGE_URI, mImage);
            mItemView.setBackgroundColor(ContextCompat.getColor(mItemView.getContext(),
                    isSelected ? R.color.colorPrimaryDark : R.color.colorWhite));
        }


        @Override
        public boolean onLongClick(View v) {
            mLongClick.onLongClickWithPosition(mRssItems.get(getLayoutPosition()), getLayoutPosition());
            return true;
        }
    }

}
