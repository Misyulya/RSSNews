package com.misyulya.rssnewsapplication.services;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import android.widget.Toast;

import com.misyulya.rssnewsapplication.business.RssBusiness;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by 1 on 29.06.2016.
 */
public class DownloadService extends IntentService {

    private int result = Activity.RESULT_OK;
    public static final String URL = "urlpath";
    public static final String FILENAME = "filename";
    public static final String FILEPATH = "filepath";
    public static final String RESULT = "result";
    public static final String NOTIFICATION_START = "com.xample.DownloadService.notificationStart";
    public static final String NOTIFICATION_END = "com.xample.DownloadService.notificationEnd";
    public static final String MESSAGE = "com.example.DownloadService.message";

    public DownloadService() {
        super("DownloadService");
    }

    // will be called asynchronously by Android
    @Override
    protected void onHandleIntent(Intent intent) {

        serviceIsRunning();
        String response = "exception occurs";
        try {
            response = new RssBusiness().getRssResponseString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        publishResults(response, result);
    }

    private void serviceIsRunning() {
        Intent intent = new Intent(NOTIFICATION_START);
        intent.putExtra(MESSAGE, "Сервис обновления в процессе");
        sendBroadcast(intent);

    }

    private void publishResults(String outputPath, int result) {
        Intent intent = new Intent(NOTIFICATION_END);
        intent.putExtra(FILEPATH, outputPath);
        intent.putExtra(RESULT, result);
        sendBroadcast(intent);
    }
}
