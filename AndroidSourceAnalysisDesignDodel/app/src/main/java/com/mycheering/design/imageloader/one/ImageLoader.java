package com.mycheering.design.imageloader.one;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lpf on 2016/8/25.
 * 图片加载类  lpf第一版
 *问题： 这个图片加载类，耦合严重，没有设计，不灵活，没有扩展性，随着功能增加，代码量会越来越大，图片加载系统就越来越脆弱。
 *修改意见：把ImageLoader进行拆分，把各个功能独立出来，让他们满足单一职责原则
 * 关键词：单一职责、功能拆分
 */
public class ImageLoader {
    //图片缓存
    LruCache<String,Bitmap> mImageCache;
    //线程池，线程数量为cpu的数量
    ExecutorService mExecutorService= Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public ImageLoader (){
        initImageCache();
    }
    private void initImageCache(){
        //计算计算机的可用最大内存
        final int maxMemory=(int)(Runtime.getRuntime().maxMemory()/1024);
        //取四分之一的可用内存做为缓存
        final  int cacheSize=maxMemory/4;

        mImageCache=new LruCache<String,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes()*bitmap.getHeight()/1024;
            }
        };
    }

    public void disPlayImage(final String url, final ImageView imageView){
        imageView.setTag(url);
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap=downloadImage(url);
                if (bitmap==null){
                    return;
                }
                if (imageView.getTag().equals(url)){
                    imageView.setImageBitmap(bitmap);
                }
                mImageCache.put(url,bitmap);
            }
        });
    }

    private Bitmap downloadImage(String iamgeUrl) {
        Bitmap bitmap=null;
        try{
            URL url=new URL(iamgeUrl);
            final HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            bitmap= BitmapFactory.decodeStream(conn.getInputStream());
            conn.disconnect();
        }catch (Exception e){

        }
        return bitmap;
    }

}
