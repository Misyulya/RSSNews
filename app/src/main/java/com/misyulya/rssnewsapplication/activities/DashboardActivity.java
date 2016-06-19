package com.misyulya.rssnewsapplication.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.misyulya.rssnewsapplication.R;
import com.misyulya.rssnewsapplication.adapters.LongClickWithPosition;
import com.misyulya.rssnewsapplication.models.RssItem;

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
                Toast.makeText(this, "Restore button pressed", Toast.LENGTH_SHORT).show();
                break;
            case R.id.exitButton:
                Toast.makeText(this, "Exit button pressed", Toast.LENGTH_SHORT).show();
                break;
        }
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
