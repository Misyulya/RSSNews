package com.misyulya.rssnewsapplication.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.misyulya.rssnewsapplication.R;
import com.misyulya.rssnewsapplication.business.RssBusiness;
import com.misyulya.rssnewsapplication.model.RssItem;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1 on 30.05.2016.
 */
public class RssItemRecyclerViewAdapter extends RecyclerView.Adapter<RssItemRecyclerViewAdapter.ViewHolder> {

    private final ClickWithPosition mClickWithPosition;
    private List<RssItem> mRssItems;
    private SparseBooleanArray mSelectedItems;
    private View mRssItem;
    private int mItemBackgroundColor = R.color.colorWhite;
    private RssBusiness mRssBusiness;

    public RssItemRecyclerViewAdapter(ClickWithPosition longClick) {
        mRssItems = new ArrayList<>();
        mClickWithPosition = longClick;
        mSelectedItems = new SparseBooleanArray();
        mRssBusiness = new RssBusiness();
    }

    public void toggleSelection(int pos) {
        if (mSelectedItems.get(pos, false)) {
            mSelectedItems.delete(pos);
        } else {
            mSelectedItems.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    public void changeBackgroundItemColor(int itemBackgroundColor) {
        this.mItemBackgroundColor = itemBackgroundColor;
    }

    public void setRssItemList(List<RssItem> rssItemList) {
        mRssItems.clear();
        mRssItems.addAll(rssItemList);
        notifyDataSetChanged();
    }

    public void clearSelections() {
        mSelectedItems.clear();
        notifyDataSetChanged();
    }

    public void deleteSelectedItems() {
        List<Integer> selectedItems = getSelectedItems();
        List<RssItem> removeItems = new ArrayList<>();
        for (int i = 0; i < selectedItems.size(); i++) {
            int position = selectedItems.get(i);
            RssItem item = mRssItems.get(position);
            removeItems.add(item);
            mRssBusiness.delete(item);
        }
        mRssItems.removeAll(removeItems);
        for (int i = 0; i < selectedItems.size(); i++) {
            int position = selectedItems.get(i);
            notifyItemRemoved(position);
        }
        mSelectedItems.clear();
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
        mRssItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.rss_item, parent, false);
        ViewHolder holder = new ViewHolder(mRssItem);
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mGenreTextView;
        private ImageView mImage;
        private String mPosterUrl;
        private View mItemView;

        public ViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            mTitleTextView = (TextView) itemView.findViewById(R.id.title_textView);
            mGenreTextView = (TextView) itemView.findViewById(R.id.genre_textView);
            mImage = (ImageView) itemView.findViewById(R.id.image_view);
            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);
        }

        public void setContent(RssItem rssItem, boolean isSelected) {
            mTitleTextView.setText(rssItem.getTitle());
            mGenreTextView.setText(rssItem.getGenre());
            mPosterUrl = rssItem.getPosterURL();
            ImageLoader.getInstance().displayImage(mPosterUrl, mImage);
            mItemView.setBackgroundColor(ContextCompat.getColor(mItemView.getContext(),
                    isSelected ? R.color.colorPrimary : mItemBackgroundColor));
        }

        @Override
        public boolean onLongClick(View v) {
            mClickWithPosition.onLongClickWithPosition(mRssItems.get(getLayoutPosition()), getLayoutPosition());
            return true;
        }

        @Override
        public void onClick(View v) {
            mClickWithPosition.onClickWithPosition(mRssItems.get(getLayoutPosition()), getLayoutPosition());
        }
    }
}
