package com.misyulya.rssnewsapplication.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.misyulya.rssnewsapplication.R;
import com.misyulya.rssnewsapplication.models.RssItem;
import com.misyulya.rssnewsapplication.services.DownloadService;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mRSSButton;
    private Button mBackupButton;
    private Button mRestoreButton;
    private Button mExitButton;
    private Button mRefreshButton;
    private Toolbar mToolbar;
    private ArrayList<RssItem> mRssItems;
    public static final String INTENT_INFORMATION = "intent information";
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String string = bundle.getString(DownloadService.FILEPATH);
                int resultCode = bundle.getInt(DownloadService.RESULT);
                if (resultCode == RESULT_OK) {
                    Toast.makeText(DashboardActivity.this,
                            "Download complete. Download URI: " + string,
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(DashboardActivity.this, "Download failed",
                            Toast.LENGTH_LONG).show();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initView();
        prepareData();
    }


    private void initView() {
        mRSSButton = (Button) findViewById(R.id.rssButton);
        mBackupButton = (Button) findViewById(R.id.backupButton);
        mRestoreButton = (Button) findViewById(R.id.restoreButton);
        mExitButton = (Button) findViewById(R.id.exitButton);
        mRSSButton.setOnClickListener(this);
        mBackupButton.setOnClickListener(this);
        mRestoreButton.setOnClickListener(this);
        mExitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rssButton:
                Toast.makeText(this, "RSS button pressed", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, RssActivity.class);
                intent.putExtra(INTENT_INFORMATION, mRssItems);
                startActivity(intent);
                break;
            case R.id.backupButton:
                Toast.makeText(this, "Backup button pressed", Toast.LENGTH_SHORT).show();
                break;
            case R.id.restoreButton:
//                Toast.makeText(this, "Restore button pressed", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(this, DownloadService.class);
                // add infos for the service which file to download and where to store
                intent1.putExtra(DownloadService.FILENAME, "index.html");
                intent1.putExtra(DownloadService.URL,
                        "http://www.vogella.com/index.html");
                startService(intent1);
                break;
            case R.id.exitButton:
                Toast.makeText(this, "Exit button pressed", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver, new IntentFilter(
                DownloadService.NOTIFICATION));
    }

    private void prepareData() {
        mRssItems = new ArrayList<>();
        mRssItems.add(new RssItem("First chanel", "the best TV chanel"));
        mRssItems.add(new RssItem("Second chanel", "Russian TV chanel"));
        mRssItems.add(new RssItem("Cultural chanel", "Interesting TV chanel"));
        mRssItems.add(new RssItem("Second chanel", "Russian TV chanel"));
        mRssItems.add(new RssItem("Cultural chanel", "Interesting TV chanel"));
        mRssItems.add(new RssItem("First chanel", "the best TV chanel"));
        mRssItems.add(new RssItem("Second chanel", "Russian TV chanel"));
        mRssItems.add(new RssItem("Cultural chanel", "Interesting TV chanel"));
        mRssItems.add(new RssItem("Second chanel", "Russian TV chanel"));
        mRssItems.add(new RssItem("Cultural chanel", "Interesting TV chanel"));
    }


}
