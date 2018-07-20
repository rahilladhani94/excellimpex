package com.excelimpex.application;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.excelimpex.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;



public class ExcelImpex extends Application {

    private static DisplayImageOptions displayImageOptions,displayImageOptions1,displayImageOptions2;
    private static ImageLoaderConfiguration config;
    private ImageLoader imageLoader;
    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader(getApplicationContext());
    }

    public static DisplayImageOptions getProductImageDisplayOption(Context context) {

        if (displayImageOptions == null) {
            displayImageOptions = new DisplayImageOptions.Builder()
                     .showImageForEmptyUri(R.mipmap.appicon)
                     .showImageOnFail(R.mipmap.appicon)
                    .showImageOnLoading(R.mipmap.appicon)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();


            return displayImageOptions;
        } else {
            return displayImageOptions;
        }
    }
    public static DisplayImageOptions getUserImageDisplayOption(Context context) {

        if (displayImageOptions1 == null) {
            displayImageOptions1 = new DisplayImageOptions.Builder()
                    .showImageForEmptyUri(R.mipmap.ic_user)
                    .showImageOnFail(R.mipmap.ic_user)
                    .showImageOnLoading(R.mipmap.ic_user)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();


            return displayImageOptions1;
        } else {
            return displayImageOptions1;
        }
    }
    public static DisplayImageOptions getExpanseImageDisplayOption(Context context) {

        if (displayImageOptions2 == null) {
            displayImageOptions2 = new DisplayImageOptions.Builder()
                    .showImageForEmptyUri(R.mipmap.picture)
                    .showImageOnFail(R.mipmap.picture)
                    .showImageOnLoading(R.mipmap.picture)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();


            return displayImageOptions2;
        } else {
            return displayImageOptions2;
        }
    }


    public static void initImageLoader(Context context) {
        if (config == null) {
            config = new ImageLoaderConfiguration.Builder(context)
                    .threadPriority(Thread.NORM_PRIORITY - 2)
                    .denyCacheImageMultipleSizesInMemory()
                    .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                    .diskCacheSize(100 * 1024 * 1024) // 100 Mb

                    .tasksProcessingOrder(QueueProcessingType.LIFO)
                    .writeDebugLogs() // Remove for release app
                    .build();
        }
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        System.gc();

    }

}
