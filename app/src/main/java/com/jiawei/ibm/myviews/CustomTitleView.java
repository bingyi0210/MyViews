package com.jiawei.ibm.myviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by ibm on 2016/12/12.
 */

public class CustomTitleView extends View {
    /**
     * 文本
     */
    private String mTitleText;

    /**
     *文本的颜色
     */
    private int titleTextColor;
    /**
     * 文本的大小
     */
    private int mTitleTextSize;

    private Paint mPaint;

    private Rect mBound;


    public CustomTitleView(Context context) {
        this(context,null);
    }
    public CustomTitleView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /**
         * 获取我们自定义的样式属性
         */
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,R.styleable.CustomTitleView,defStyleAttr,0);
        int n =a.getIndexCount();
        for (int i=0;i<n;i++){
            int attr = a.getIndex(i);
            switch (attr){
                case  R.styleable.CustomTitleView_titleText:
                    mTitleText =a.getString(attr);
                    break;
//                case R.styleable.CustomTitleView_titletextColor1:
//                    titleTextColor = a.getColor(attr, Color.BLACK);
//                    Log.e("color1",titleTextColor+"====");
//                    break;
                case R.styleable.CustomTitleView_titleTextSize:
                    mTitleTextSize = a.getDimensionPixelSize(attr, (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,16,getResources().getDisplayMetrics()));
                    break;
            }
        }
        a.recycle();
        /**
         * 获取文本的宽和高
         *
         */
        mPaint = new Paint();
        mPaint.setTextSize(mTitleTextSize);
        mPaint.setColor(titleTextColor);
        mBound = new Rect();
        mPaint.getTextBounds(mTitleText,0,mTitleText.length(),mBound);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mTitleText = randomText();
                postInvalidate();
            }

            private String randomText() {
                Random random = new Random();
                Set<Integer> set = new HashSet<Integer>();
                while (set.size()<4){
                    int randomInt = random.nextInt(10);
                    set.add(randomInt);
                }
                StringBuffer sb = new StringBuffer();
                for(Integer i : set){
                    sb.append(""+i);
                }
                return sb.toString();
            }
        });




    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY){
            width = widthSize;
        }else {
            mPaint.setTextSize(mTitleTextSize);
            mPaint.getTextBounds(mTitleText,0,mTitleText.length(),mBound);
            float textWidth = mBound.width();
            int desired = (int) (getPaddingLeft() + textWidth + getPaddingRight());
            width = desired;
        }
        if (heightMode == MeasureSpec.EXACTLY){
            height = heightSize;
        }else{
            mPaint.setTextSize(mTitleTextSize);
            mPaint.getTextBounds(mTitleText,0,mTitleText.length(),mBound);
            float textHeight =mBound.height();
            int desired = (int) (getPaddingTop() + textHeight + getPaddingBottom());
            height = desired;
        }
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),mPaint);
        
        mPaint.setColor(Color.BLUE);
        canvas.drawText(mTitleText,getWidth()/2 -mBound.width()/2,getHeight()/2+mBound.height()/2,mPaint);
    }
}
