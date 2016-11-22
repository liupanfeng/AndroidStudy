package com.mycheering.design;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

//import com.mycheering.design.imageloader.one.ImageLoader;
//import com.mycheering.design.imageloader.two.ImageLoader;
import com.mycheering.design.imageloader.three.ImageLoader;

public class MainActivity extends AppCompatActivity {
    private ImageView iv_show_pic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv_show_pic=(ImageView)findViewById(R.id.iv_show_pic);
        ImageLoader imageLoader=new ImageLoader();
        imageLoader.setDiskCache(true);
        imageLoader.disPlayImage("http://img2.imgtn.bdimg.com/it/u=395920684,863299018&fm=21&gp=0.jpg",iv_show_pic);
        System.out.println("lpf");
    }
}
