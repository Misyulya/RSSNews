package com.misyulya.rssnewsapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.misyulya.rssnewsapplication.R;
import com.misyulya.rssnewsapplication.business.RssBusiness;
import com.misyulya.rssnewsapplication.dialog.OkDialog;
import com.misyulya.rssnewsapplication.exeption.DbException;
import com.misyulya.rssnewsapplication.model.RssItem;

import java.io.IOException;
import java.util.Collection;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

    private RssBusiness mRssBusiness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initView();
        mRssBusiness = new RssBusiness();
    }

    private void initView() {
        Button mRSSButton = (Button) findViewById(R.id.rssButton);
        Button mBackupButton = (Button) findViewById(R.id.backupButton);
        Button mRestoreButton = (Button) findViewById(R.id.restoreButton);
        Button mExitButton = (Button) findViewById(R.id.exitButton);
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
                new WriteDbToFile().execute();
                break;
            case R.id.restoreButton:
                new ReadDbFromFile().execute();
                break;
            case R.id.exitButton:
                onBackPressed();
                break;
        }
    }

    private void showDialog(String message) {
        Bundle bundle = new Bundle();
        bundle.putString(OkDialog.MESSAGE_FOR_DIALOG, message);
        OkDialog dialog = new OkDialog();
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), "OkDialog");
    }

    private class WriteDbToFile extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            if (RssBusiness.isExternalStorageWritable()) {
                Collection rssItem = mRssBusiness.getRss();
                if (rssItem.isEmpty()) {
                    if (isNetworkAvailable()) {
                        try {
                            rssItem = mRssBusiness.requestRss();
                        } catch (IOException e) {
                            e.printStackTrace();
                            return e.getMessage();
                        } catch (DbException e) {
                            e.printStackTrace();
                            return e.getMessage();
                        }
                    } else return "Необходим интернет";
                }
                String json = mRssBusiness.serializeToJson(rssItem);
                RssBusiness.writeToFile(json);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s == null) {
                showDialog("Запись данных завершена успешно");
            } else showDialog(s);
        }
    }

    private class ReadDbFromFile extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            if (RssBusiness.isExternalStorageReadable()) {
                String json;
                try {
                    json = RssBusiness.readFromFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    return "Ошибка: " + e.getMessage();
                }
                Collection<RssItem> list = RssBusiness.getRssListFromJsonFile(json);
                try {
                    mRssBusiness.saveRssToDB(list);
                } catch (DbException e) {
                    e.printStackTrace();
                    return "Ошибка: " + e.getMessage();
                }
                return null;

            }
            return "SD карта не доступна";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s == null) {
                showDialog("Чтение данных завершено успешно");
            } else showDialog(s);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
