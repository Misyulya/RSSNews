package com.misyulya.rssnews.business;

import android.os.Environment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.misyulya.rssnews.database.DataProvider;
import com.misyulya.rssnews.exeption.DbException;
import com.misyulya.rssnews.model.RssItem;
import com.misyulya.rssnews.model.RssResponse;
import com.misyulya.rssnews.rest.RestFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import retrofit2.Response;

/**
 * Created by 1 on 02.06.2016.
 */
public class RssBusiness {

    private DataProvider mDataProvider;
    public static final String FILE_NAME = "rss.json";

    public RssBusiness() {
        mDataProvider = new DataProvider();
    }

    public Collection<RssItem> getRss() {
        Collection<RssItem> rssItemsList = mDataProvider.getRss();
        return rssItemsList;
    }

    public Collection<RssItem> requestRss() throws DbException, IOException {
        Response<RssResponse> response = null;
        response = RestFactory.get().getRSS().execute();
        List<RssItem> rssItems = response.body().getRssData();
        mDataProvider.setRss(rssItems);
        return rssItems;
    }

    public void saveRssToDB(Collection<RssItem> rssItems) throws DbException {
        mDataProvider.setRss(rssItems);
    }

    public int delete(RssItem item) {
        return mDataProvider.removeRss(item);
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
        File file = new File(root, FILE_NAME);
        //AL_DM If you work with this resources, you must guarantee at 100% what you will free them.
        //Use try with resources or finally block! Check all  methods ho work with resources.
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

    public static String readFromFile() throws IOException {
        //AL_DM do not throws this exception yo need to handle it in this method.
        //If you work with this resources, you must guarantee at 100% what you will free them.
        final File file = new File(Environment.getExternalStorageDirectory(), FILE_NAME);
        BufferedReader br = null;
        String response = null;

        StringBuffer output = new StringBuffer();
        br = new BufferedReader(new FileReader(file));
        String line = "";
        while ((line = br.readLine()) != null) {
            output.append(line + "n");
        }
        response = output.toString();
        return response;
    }

    public String serializeToJson(Collection<RssItem> rssItem) {
        Gson gson = new Gson();
        String json = gson.toJson(rssItem);
        return json;
    }

    public static Collection<RssItem> getRssListFromJsonFile(String json) {
        JsonReader reader = new JsonReader(new StringReader(json));
        reader.setLenient(true);
        Gson gson = new Gson();
        Type rssItemListType = new TypeToken<Collection<RssItem>>() {
        }.getType();
        Collection<RssItem> rssItems = gson.fromJson(reader, rssItemListType);
        return rssItems;
    }
}
