package com.misyulya.rssnewsapplication.business;

import android.content.Context;
import android.os.Environment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.misyulya.rssnewsapplication.database.DataProvider;
import com.misyulya.rssnewsapplication.exeption.DbException;
import com.misyulya.rssnewsapplication.model.RssItem;
import com.misyulya.rssnewsapplication.model.RssResponse;
import com.misyulya.rssnewsapplication.rest.RestFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 1 on 02.06.2016.
 */
public class RssBusiness {
    private DataProvider mDataProvider;
    public static final String FILE_NAME = "rss.json";


    public RssBusiness() {
        this.mDataProvider = new DataProvider();
    }

    public List<RssItem> getRss() {
        List<RssItem> rssItemsList = mDataProvider.getRss();
        return rssItemsList;
    }

    public void requestRss(final Callback<RssResponse> callback) {
        RestFactory.get().getRSS().enqueue(new Callback<RssResponse>() {
        @Override
        public void onResponse (Call < RssResponse > call, Response < RssResponse > response){
            List<RssItem> rssItems;
            rssItems = response.body().getRssData();
            try {
                mDataProvider.setRss(rssItems);
            } catch (DbException e) {
                callback.onFailure(call, e);
            }
            callback.onResponse(call, response);
        }

        @Override
        public void onFailure (Call < RssResponse > call, Throwable t){
            callback.onFailure(call, t);
        }
    }

    );
}

    public void saveRssToDB(List<RssItem> rssItems) throws DbException {
        mDataProvider.setRss(rssItems);
    }

    public void delete(RssItem item) {
        mDataProvider.removeRss(item);
    }

    //     Checks if external storage is available for read and write
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    //    Checks if external storage is available to at least read
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public static void writeToFile(String json) {
        File root = android.os.Environment.getExternalStorageDirectory();
        String rootPath = root.getAbsolutePath();
        File file = new File(root, FILE_NAME);
        try {
            FileOutputStream f = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(f);
            pw.println(json);
            pw.flush();
            pw.close();
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFromFile() {
        final File file = new File(Environment.getExternalStorageDirectory(), FILE_NAME);
        BufferedReader br = null;
        String response = null;

        try {

            StringBuffer output = new StringBuffer();
            br = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = br.readLine()) != null) {
                output.append(line + "n");
            }
            response = output.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return response;
    }

    public String serializeToJson(List rssItem) {
        Gson gson = new Gson();
        String json = gson.toJson(rssItem);
        return json;
    }

    public static List<RssItem> getRssListFromJsonFile (String json){
        Gson gson = new Gson();
        Type rssItemListType = new TypeToken<ArrayList<RssItem>>(){}.getType();
        List<RssItem> rssItems = gson.fromJson(json, rssItemListType);
        return rssItems;
    }
}
