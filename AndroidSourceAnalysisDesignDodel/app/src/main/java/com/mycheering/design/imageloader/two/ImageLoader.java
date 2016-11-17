package com.mycheering.design.imageloader.two;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lpf on 2016/10/28.
 * 图片加载类  lpf第二版     ------通过单一职责原则就行了，重构
 * 优点：ImageLoader只负责加载图片 ImageCache只负责缓存，职责清晰了，结构也清晰了很多
 * 不足：缺少扩展性,当内存不足的时候，现在的缓存显然会受到限制，需要添加sd卡的缓存
 * 修改意见：当软件需要变化的时候，尽量通过扩展的方式进行扩展而不是修改原来的代码，要遵循开闭原则(OCP)。开：对扩展是开放的，闭：对于已经存在的已经实现的类的修改是封闭的
 */
public class ImageLoader {
    ImageCache mImageCache=new ImageCache();
    //线程池，线程数量为cpu的数量
    ExecutorService mExecutorService= Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    /**
     * 加载图片
     * @param url
     * @param imageView
     */
    public void disPlayImage(final String url, final ImageView imageView){
        Bitmap bitmap=mImageCache.get(url);
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
                if (imageView.getTag().equals(url)){
                    imageView.setImageBitmap(bitmap);
                }
                mImageCache.put(url,bitmap);
            }
        });
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
