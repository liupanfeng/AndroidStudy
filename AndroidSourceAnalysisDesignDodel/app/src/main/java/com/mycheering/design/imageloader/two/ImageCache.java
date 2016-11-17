package com.mycheering.design.imageloader.two;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * Created by lpf on 2016/10/28.
 * 图片缓存类
 */
public class ImageCache {

    //图片缓存
    LruCache<String,Bitmap> mImageCache;

    public ImageCache() {
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

    public void put(String url,Bitmap bitmap){
        mImageCache.put(url,bitmap);
    }

    public Bitmap get(String url){
        return mImageCache.get(url);
    }
}
