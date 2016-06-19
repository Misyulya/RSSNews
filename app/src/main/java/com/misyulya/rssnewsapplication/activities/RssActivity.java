package com.misyulya.rssnewsapplication.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.Toast;

import com.misyulya.rssnewsapplication.R;
import com.misyulya.rssnewsapplication.adapters.LongClickWithPosition;
import com.misyulya.rssnewsapplication.adapters.RssItemRecyclerViewAdapter;
import com.misyulya.rssnewsapplication.business.RssBusiness;
import com.misyulya.rssnewsapplication.models.RssItem;

import java.util.ArrayList;

/**
 * Created by 1 on 30.05.2016.
 */
public class RssActivity extends AppCompatActivity implements View.OnClickListener, LongClickWithPosition {

    private RecyclerView mRecyclerView;
    private RssItemRecyclerViewAdapter mAdapter;
    private ArrayList<RssItem> mRssItems;
    private Button mRefreshButton;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rss_activity);
        initView();
        initToolbar(mToolbar);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RssItemRecyclerViewAdapter(new RssBusiness().getRss(), this);
        mRecyclerView.setAdapter(mAdapter);
    }


    private void initToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mRefreshButton = (Button) toolbar.findViewById(R.id.refresh_button);
        mRefreshButton.setOnClickListener(this);

    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "refresh button pressed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLongClickWithPosition(RssItem item, int itemPosition) {
        mAdapter.toggleSelection(itemPosition);
        Toast.makeText(this, item.getmTitle(), Toast.LENGTH_SHORT).show();
    }
}
