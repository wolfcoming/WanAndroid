package com.czy.lib_ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.czy.lib_log.HiLog;

public class RotatingRing extends View {


    private Paint mPaint;
    int angle;
    int startAngle = 90; // 圆环初始角度 3点钟方向为 0°
    int sweepAngle = 270; // 圆环扫过的角度
    private RectF rectF;

    public RotatingRing(Context context) {
        super(context);
        init();
    }


    public RotatingRing(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RotatingRing(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public RotatingRing(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(12);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setShader(new SweepGradient(centerX, centerY,
                new int[]{Color.GRAY, Color.BLUE},
                new float[]{startAngle / 360f * 1.0f, sweepAngle / 360f * 1.0f}));
    }

    int width;
    int radius;//半径
    int centerX, centerY;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int measuredHeight = getMeasuredHeight();
        int measuredWidth = getMeasuredWidth();
        width = Math.min(measuredHeight, measuredWidth);
        radius = (int) (width / 2 - mPaint.getStrokeWidth() / 2);
        rectF = new RectF(-radius, -radius, radius, radius);
        centerX = measuredWidth / 2;
        centerY = measuredHeight / 2;
        HiLog.log(width);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制一个圆
        canvas.save();
        canvas.translate(centerX, centerY);
        canvas.rotate(angle + startAngle);
        canvas.drawArc(rectF, 0 + width / 2f, sweepAngle, false, mPaint);
        canvas.restore();
    }

    ValueAnimator animator;

    public void startAnimal() {
        if (animator != null && animator.isRunning()) {
            return;
        }
        if (animator == null) {
            animator = ValueAnimator.ofInt(0, 360);
        }
        animator.addUpdateListener(animation -> {
            angle = (int) animation.getAnimatedValue();
            invalidate();
        });
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setDuration(500);
        animator.start();
    }

    public void stopAnimal() {
        if (animator == null) return;
        animator.cancel();
        animator.end();
        angle = 0;
        animator = null;

    }
}
