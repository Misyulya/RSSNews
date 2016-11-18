package com.misyulya.rssnews.activity;

import java.io.IOException;
import java.util.Collection;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.annimon.stream.Stream;
import com.misyulya.rssnews.R;
import com.misyulya.rssnews.business.RssBusiness;
import com.misyulya.rssnews.dialog.OkDialog;
import com.misyulya.rssnews.exeption.DbException;
import com.misyulya.rssnews.model.RssItem;

public class DashboardActivity extends AppCompatActivity {

    private RssBusiness mRssBusiness;

    private View.OnClickListener mOnClickListener = v -> {
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
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initView();
        mRssBusiness = new RssBusiness();
    }

    private void initView() {
        Stream.of(findViewById(R.id.rssButton),
                  findViewById(R.id.backupButton),
                  findViewById(R.id.restoreButton),
                  findViewById(R.id.exitButton))
                .forEach(v -> v.setOnClickListener(mOnClickListener));
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

    //AL_DB Naming, this class do not read db from file, he read data from file and put this data in DB.
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
                    return "Ошибка: " + e.getMessage(); //AL_DM Move all code in one try catch block make exception handle more explicit and clearly. Speak with mee.
                }
                return null; //AL_DM One return instead of multiple, wats make code more readable.

            }
            return "SD карта не доступна";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s == null) {
                showDialog("Чтение данных завершено успешно");//AL_DM Move all hardcode strings to String.xml
            } else showDialog(s);
        }
    }

    private boolean isNetworkAvailable() {//AL_DM This looks like a utility function maybe you should create some Utility class for this function. DeviceUtils.isNetworkAvailable();
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
