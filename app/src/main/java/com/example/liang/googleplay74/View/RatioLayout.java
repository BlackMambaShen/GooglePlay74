package com.example.liang.googleplay74.View;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/*
自定义控件 按照比例来决定布局高度
 */
public class RatioLayout extends FrameLayout {
    private  float ratio;

    public RatioLayout(@NonNull Context context) {
        super(context);
    }

    public RatioLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //获取属性值
         ratio = attrs.getAttributeFloatValue("dayi","radio",1);
    }

    public RatioLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /*
        1.获取宽度
        2.根据宽度和ratio,计算控件的高度
        3.重新测量控件
         */
        //MeasureSpec.AT_MOST:至多模式,控件有多大显示多大,wrap_content
       // MeasureSpec.EXACTLY:确定模式，宽高写死 match_parent
       // MeasureSpec.UNSPECIFIED:未指定模式
        int width = MeasureSpec.getSize(widthMeasureSpec);//获取宽度值
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);//获取宽度的模式
        int height = MeasureSpec.getSize(heightMeasureSpec);//获取高度值
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);//获取高度的模式
        //宽度确定，高度不确定,radio合法才计算高度值
        if (widthMode==MeasureSpec.EXACTLY&&heightMode!=MeasureSpec.EXACTLY&&ratio>0){
            //图片宽度=控件宽度-左侧内边距-右侧内边距
            int imageWidth = width - getPaddingLeft() - getPaddingRight();
            int imageHeight= (int) (imageWidth/ratio);
            //控件高度=图片高度+上下侧内边距
            height=imageHeight+getPaddingTop()+getPaddingBottom();
            //根据最新的高度来重新生成(高度模式是确定模式)
            heightMeasureSpec=MeasureSpec.makeMeasureSpec(height,MeasureSpec.EXACTLY);
        }
        //按照最新的高度来测量控件
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
