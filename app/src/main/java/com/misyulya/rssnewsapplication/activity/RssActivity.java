package com.misyulya.rssnewsapplication.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.misyulya.rssnewsapplication.R;
import com.misyulya.rssnewsapplication.adapter.ClickWithPosition;
import com.misyulya.rssnewsapplication.adapter.RssItemRecyclerViewAdapter;
import com.misyulya.rssnewsapplication.business.RssBusiness;
import com.misyulya.rssnewsapplication.exeption.DbException;
import com.misyulya.rssnewsapplication.model.RssItem;
import com.misyulya.rssnewsapplication.model.RssResponse;
import com.misyulya.rssnewsapplication.service.DownloadService;

import java.net.ConnectException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 1 on 30.05.2016.
 */
public class RssActivity extends AppCompatActivity implements View.OnClickListener, ClickWithPosition {

    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;
    private RssItemRecyclerViewAdapter mAdapter;
    private Button mRefreshButton;
    private Toolbar mToolbar;
    private ActionMode mActionMode;
    private RssBusiness mRssBusiness;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                if (intent.getAction() == DownloadService.NOTIFICATION_START) {
                    mProgressBar.setVisibility(View.VISIBLE);
                    String message = bundle.getString(DownloadService.MESSAGE);
                    Toast.makeText(RssActivity.this, message, Toast.LENGTH_SHORT).show();
                } else if (intent.getAction() == DownloadService.NOTIFICATION_END) {
                    String message = null;
                    switch (intent.getIntExtra(DownloadService.RESULT, 0)) {
                        case Activity.RESULT_OK:
                            message = intent.getStringExtra(DownloadService.MESSAGE);
                            updateInformation();
                            break;
                        case Activity.RESULT_CANCELED:
                            message = intent.getStringExtra(DownloadService.ERROR);
                    }
                            Toast.makeText(RssActivity.this, message, Toast.LENGTH_SHORT).show();
                            mProgressBar.setVisibility(View.GONE);
                }
            }
        }
    };

    private android.view.ActionMode.Callback mCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = RssActivity.this.getMenuInflater();
            inflater.inflate(R.menu.action_mode_menu, menu);
            mAdapter.changeBackgroundItemColor(R.color.colorLightGray);
            mAdapter.notifyDataSetChanged();
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.delete_button:
                    mAdapter.deleteSelectedItems();
                    mActionMode.finish();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mAdapter.changeBackgroundItemColor(R.color.colorWhite);
            mAdapter.clearSelections();
            mActionMode = null;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rss_activity);
        initView();
        initToolbar(mToolbar);
        mRssBusiness = new RssBusiness();
        updateInformation();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.INVISIBLE);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RssItemRecyclerViewAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mRefreshButton = (Button) toolbar.findViewById(R.id.refresh_button);
        mRefreshButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, DownloadService.class);
        startService(intent);
    }

    @Override
    public void onLongClickWithPosition(RssItem item, int itemPosition) {
        if (mActionMode == null) {
            mActionMode = mToolbar.startActionMode(mCallback);
        }
        mAdapter.toggleSelection(itemPosition);
        if (mAdapter.getSelectedItemCount() == 0) {
            mActionMode.finish();
        }
    }

    @Override
    public void onClickWithPosition(RssItem item, int itemPosition) {
        if (mActionMode != null) {
            mAdapter.toggleSelection(itemPosition);
            if (mAdapter.getSelectedItemCount() == 0) {
                mActionMode.finish();
            }
        }
    }

    public void updateInformation() {
        mProgressBar.setVisibility(View.VISIBLE);
        List<RssItem> rssItems = mRssBusiness.getRss();
        if (rssItems.isEmpty()) {
            mRssBusiness.requestRss(new Callback<RssResponse>() {
                @Override
                public void onResponse(Call<RssResponse> call, Response<RssResponse> response) {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    mAdapter.setRssItemList(mRssBusiness.getRss());
                }

                @Override
                public void onFailure(Call<RssResponse> call, Throwable t) {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    if (t instanceof DbException) {
                        Toast.makeText(RssActivity.this, "Ошибка записи в БД", Toast.LENGTH_SHORT).show();
                        RssActivity.this.finish();
                    }
                }
            });
        } else {
//            TODO Fix problems with the threads
            mAdapter.setRssItemList(rssItems);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver, new IntentFilter(DownloadService.NOTIFICATION_START));
        registerReceiver(mReceiver, new IntentFilter(DownloadService.NOTIFICATION_END));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }

}
