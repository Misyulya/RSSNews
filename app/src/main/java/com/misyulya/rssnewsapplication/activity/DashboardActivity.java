package com.misyulya.rssnewsapplication.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.misyulya.rssnewsapplication.R;
import com.misyulya.rssnewsapplication.business.RssBusiness;
import com.misyulya.rssnewsapplication.model.RssItem;

import java.io.File;
import java.util.List;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mRSSButton;
    private Button mBackupButton;
    private Button mRestoreButton;
    private Button mExitButton;
    private RssBusiness mRssBusiness;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initView();
        mRssBusiness = new RssBusiness();
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
                Intent intent = new Intent(this, RssActivity.class);
                startActivity(intent);
                break;
            case R.id.backupButton:
                if (RssBusiness.isExternalStorageWritable()) {
                    List rssItem = mRssBusiness.getRss();
                    String json = mRssBusiness.serializeToJson(rssItem);
                    RssBusiness.writeToFile(json);
                    Toast.makeText(this, "Data from DB successfully serialized to JSON", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.restoreButton:
                if (RssBusiness.isExternalStorageReadable()) {
                    String json = RssBusiness.readFromFile();
                    Log.d("myLog", json);
                    List<RssItem> list = RssBusiness.getRssListFromJsonFile(json);
                    Toast.makeText(this, "Data from rss.json successfully deserialized" , Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.exitButton:
                onBackPressed();
                break;
        }
    }

}
