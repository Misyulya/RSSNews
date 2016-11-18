package com.misyulya.rssnews.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
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

import com.misyulya.rssnews.R;
import com.misyulya.rssnews.adapter.ClickWithPosition;
import com.misyulya.rssnews.adapter.RssItemRecyclerViewAdapter;
import com.misyulya.rssnews.business.RssBusiness;
import com.misyulya.rssnews.dialog.OkDialog;
import com.misyulya.rssnews.exeption.DbException;
import com.misyulya.rssnews.model.RssItem;
import com.misyulya.rssnews.service.DownloadService;

import java.io.IOException;
import java.util.Collection;

/**
 * Created by 1 on 30.05.2016.
 */
public class RssActivity extends AppCompatActivity implements View.OnClickListener, ClickWithPosition {

    private ProgressBar mProgressBar;
    private RssItemRecyclerViewAdapter mAdapter;
    private Toolbar mToolbar;
    private ActionMode mActionMode;
    private RssBusiness mRssBusiness;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String message = bundle.getString(DownloadService.MESSAGE);
                if (intent.getAction() == DownloadService.NOTIFICATION_START) {
                    mProgressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(RssActivity.this, message, Toast.LENGTH_SHORT).show();
                } else if (intent.getAction() == DownloadService.NOTIFICATION_END) {
                    switch (intent.getIntExtra(DownloadService.RESULT, 0)) {
                        case Activity.RESULT_OK:
                            new ReadDataFromDb().execute();
                            Toast.makeText(RssActivity.this, message, Toast.LENGTH_SHORT).show();
                            break;
                        case Activity.RESULT_CANCELED:
                            showDialog(message);
                    }
                    mProgressBar.setVisibility(View.GONE);
                }
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rss_activity);
        initView();
        initToolbar(mToolbar);
        mRssBusiness = new RssBusiness();
        mProgressBar.setVisibility(View.VISIBLE);
        new ReadDataFromDb().execute();
    }

    private void showDialog(String message) {
        Bundle bundle = new Bundle();
        bundle.putString(OkDialog.MESSAGE_FOR_DIALOG, message);
        OkDialog dialog = new OkDialog();
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), "OkDialog");
    }

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


    private void initView() {
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RssItemRecyclerViewAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Button mRefreshButton = (Button) toolbar.findViewById(R.id.refresh_button);
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

    @Override
    public void finishActionModeAfterDeleteOperation() {
        mActionMode.finish();
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

    private class ReadDataFromDb extends AsyncTask<Void, Void, Collection<RssItem>> {
        @Override
        protected Collection<RssItem> doInBackground(Void... params) {
            Collection<RssItem> rssItems = mRssBusiness.getRss();
            if (rssItems.isEmpty()) {
                if (isNetworkAvailable()) {
                    try {
                        rssItems = mRssBusiness.requestRss();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                } else return null;
            }
            return rssItems;
        }

        @Override
        protected void onPostExecute(Collection<RssItem> rssItems) {
            super.onPostExecute(rssItems);
            if (!rssItems.isEmpty()) {
                mAdapter.setRssItemList(rssItems);
            } else {
                showDialog("Нужен интернет");
            }
            mProgressBar.setVisibility(View.GONE);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
