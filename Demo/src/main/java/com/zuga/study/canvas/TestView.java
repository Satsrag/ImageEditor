package com.zuga.study.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.zuga.log.LogSa;

/**
 * @author saqrag
 * @version 1.0
 * @see null
 * 15/06/2017
 * @since 1.0
 **/

public class TestView extends View {

    public TestView(Context context) {
        super(context);
        init();
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LogSa.d("init");
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        LogSa.d("onAttachedToWindow");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        LogSa.d("onDetachedFromWindow");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        LogSa.d("onDraw");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        LogSa.d("onMeasure");
    }
}
