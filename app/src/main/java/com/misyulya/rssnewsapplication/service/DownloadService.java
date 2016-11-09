package com.misyulya.rssnewsapplication.service;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;

import com.misyulya.rssnewsapplication.business.RssBusiness;
import com.misyulya.rssnewsapplication.exeption.DbException;

import java.io.IOException;

public class DownloadService extends IntentService {

    public static final String RESULT = "result";
    public static final String NOTIFICATION_START = "com.example.DownloadService.notificationStart";
    public static final String NOTIFICATION_END = "com.example.DownloadService.notificationEnd";
    public static final String MESSAGE = "com.example.DownloadService.message";
    private static final String SERVICE_NAME = "DownloadService";

    public DownloadService() {
        super(SERVICE_NAME);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        final Intent sendingIntent = new Intent();
        serviceIsRunning(sendingIntent);
        String message = null;

        try {
            new RssBusiness().requestRss();
            successfulWriting(sendingIntent);
        } catch (IOException e) {
            message = e.getMessage();
        } catch (DbException e) {
            message = e.getMessage();
        } finally {
            serviceCompleted(sendingIntent, message);
        }
    }

    private void serviceCompleted(Intent intent, String message) {
        intent.setAction(NOTIFICATION_END);
        if (message == null) {
            intent.putExtra(RESULT, Activity.RESULT_OK);
            intent.putExtra(MESSAGE, "Сервис обновления завершён");
        } else {
            intent.putExtra(RESULT, Activity.RESULT_CANCELED);
            intent.putExtra(MESSAGE, "Сервис обновления завершён c ошибкой: " + message);
        }
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
