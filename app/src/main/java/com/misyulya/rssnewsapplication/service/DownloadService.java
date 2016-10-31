package com.misyulya.rssnewsapplication.service;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;

import com.misyulya.rssnewsapplication.business.RssBusiness;
import com.misyulya.rssnewsapplication.exeption.DbException;
import com.misyulya.rssnewsapplication.model.RssResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 1 on 29.06.2016.
 */
public class DownloadService extends IntentService {

    private int result = Activity.RESULT_OK;
    public static final String URL = "urlpath";
    public static final String FILENAME = "filename";
    public static final String FILEPATH = "filepath";
    public static final String RESULT = "result";
    public static final String NOTIFICATION_START = "com.example.DownloadService.notificationStart";
    public static final String NOTIFICATION_END = "com.example.DownloadService.notificationEnd";
    public static final String MESSAGE = "com.example.DownloadService.message";
    public static final String ERROR = "com.example.DownloadService.error";
    public static final String SERVICE_NAME = "DownloadService";

    public DownloadService() {
        super(SERVICE_NAME);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        final Intent sendingIntent = new Intent();
        serviceIsRunning(sendingIntent);

        new RssBusiness().requestRss(new Callback<RssResponse>() {
            @Override
            public void onResponse(Call<RssResponse> call, Response<RssResponse> response) {
                successfulWriting(sendingIntent);
            }

            @Override
            public void onFailure(Call<RssResponse> call, Throwable t) {
                if (t instanceof DbException) {
                    unsuccessfulWriting(sendingIntent, t.getMessage());
                }
            }
        });
    }

    private void unsuccessfulWriting(Intent intent, String error) {
        intent.setAction(NOTIFICATION_END);
        intent.putExtra(RESULT, Activity.RESULT_CANCELED);
        intent.putExtra(ERROR, "Сервис обновления завершён c ошибкой: " + error);
        sendBroadcast(intent);
    }

    private void successfulWriting(Intent intent) {
        intent.setAction(NOTIFICATION_END);
        intent.putExtra(RESULT, Activity.RESULT_OK);
        intent.putExtra(MESSAGE, "База данных обновлена");
        sendBroadcast(intent);
    }

    private void serviceIsRunning(Intent intent) {
        intent.setAction(NOTIFICATION_START);
        intent.putExtra(MESSAGE, "Сервис обновления в процессе");
        sendBroadcast(intent);

    }
}
