package com.mycheering.design.imageloader.three;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by lpf on 2016/10/28.
 */
public class DiskCache {

    private static String cacheDir="sdcard/cache/";

    /**
     * 将图片从缓存中取出来
     * @param url
     * @return
     */
    public Bitmap get(String url){
        return BitmapFactory.decodeFile(cacheDir+url);
    }

    public void put(String url,Bitmap bitmap){
        FileOutputStream fileOutputStream=null;
        try {
            fileOutputStream=new FileOutputStream(cacheDir+url);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if (fileOutputStream!=null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }



}
