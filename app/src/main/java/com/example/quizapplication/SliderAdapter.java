package com.example.quizapplication;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater inflater;

    public int[] lst_images = {
            R.drawable.imgbackground
    };
    public SliderAdapter(Context context){
        this.context = context;
    }
    @Override
    public int getCount() {
        return slide_headings.length;
    }

    public String[] slide_headings = {
            "WELCOME TO CRIME MAPPING SYSTEM"
    };
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(RelativeLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view  = inflater.inflate(R.layout.activity_slider,container, false);
        ImageView imgslide = (ImageView) view.findViewById(R.id.imgBackground);
        imgslide.setImageResource(lst_images[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }
}
