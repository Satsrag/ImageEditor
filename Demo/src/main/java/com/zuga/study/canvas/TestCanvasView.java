package com.zuga.study.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author saqrag
 * @version 1.0
 * @see null
 * 26/10/2017
 * @since 1.0
 **/

public class TestCanvasView extends View {
    private Paint mPaint;
    public TestCanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.scale(2f,2f);
        canvas.save();
        canvas.scale(2f,2f);
        canvas.drawRect(0,0,100,100,mPaint);
        canvas.restore();
        canvas.drawRect(300,300,400,400,mPaint);
    }
}
