package com.mycheering.design.imageloader.three;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lpf on 2016/10/28.
 */
public class ImageLoader {

    private boolean isDiskCache=false;
    ImageCache mImageCache=new ImageCache();
    DiskCache mDiskCache=new DiskCache();
    //线程池，线程数量为cpu的数量
    ExecutorService mExecutorService= Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    /**
     * 加载图片
     * @param url
     * @param imageView
     */
    public void disPlayImage(final String url, final ImageView imageView){
        Bitmap bitmap=isDiskCache?mDiskCache.get(url):mImageCache.get(url);
        if (bitmap!=null){
            imageView.setImageBitmap(bitmap);
            return;
        }
        imageView.setTag(url);
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap=downloadImage(url);
                if (bitmap==null){
                    return;
                }

                if (isDiskCache){
                    mDiskCache.put(url, bitmap) ;
                }else {
                    mImageCache.put(url, bitmap);
                }

                if (imageView.getTag().equals(url)){
                    imageView.setImageBitmap(bitmap);
                }

            }
        });
    }

    public void setDiskCache(boolean isDiskCaches){
        isDiskCache=isDiskCaches;
    }

    /**
     * 下载图片
     * @param imageUrl
     * @return
     */
    private Bitmap downloadImage(String imageUrl) {
        Bitmap bitmap=null;
        try{
            URL url=new URL(imageUrl);
            final HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            bitmap= BitmapFactory.decodeStream(conn.getInputStream());
            conn.disconnect();
        }catch (Exception e){

        }
        return bitmap;
    }

}
