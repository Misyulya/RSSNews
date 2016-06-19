package com.misyulya.rssnewsapplication.app;

import android.app.Application;
import android.content.Context;

import com.misyulya.rssnewsapplication.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.L;
import com.nostra13.universalimageloader.utils.StorageUtils;

/**
 * Created by 1 on 03.06.2016.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initializeImageLoader(this);
    }
    private void initializeImageLoader(Context context) {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        DisplayImageOptions options =
                new DisplayImageOptions.Builder()
                        .showImageOnFail(R.mipmap.ic_launcher)

                        .showImageOnLoading(R.mipmap.ic_launcher)
                        .cacheInMemory(true).cacheOnDisk(true).build();

        config.defaultDisplayImageOptions(options);
        config.threadPoolSize(5);

        config.diskCache(new UnlimitedDiskCache(StorageUtils.getCacheDirectory(context), null, new FileNameGenerator() {
            @Override
            public String generate(String imageUri) {
                String generate = String.valueOf(imageUri.hashCode());
                return generate;
            }
        }));

        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.threadPriority(Thread.NORM_PRIORITY - 2);

        ImageLoader.getInstance().init(config.build());
    }
}

