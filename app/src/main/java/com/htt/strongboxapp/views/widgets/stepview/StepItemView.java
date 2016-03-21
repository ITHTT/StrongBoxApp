package com.htt.strongboxapp.views.widgets.stepview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/3/3.
 */
public class StepItemView extends View {
    private Context context;
    /**外形绘制画笔*/
    private Paint shapePaint;
    /**文章绘制画笔*/
    private Paint textPaint;

    private Path shapePath;

    private int height;
    private int width;
    /**控件外形颜色*/
    private int shapeColor;
    /**文字颜色*/
    private int textColor;
    /**步骤类型,分为开始步奏，中间步骤，结束步骤*/
    private int stepType=START_STEP;

    public static final int START_STEP=0x0001;
    public static final int MIDDLE_STEP=0x0002;
    public static final int END_STEP=0x0003;

    public StepItemView(Context context) {
        super(context);
    }

    public StepItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StepItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public StepItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initViews(Context context){
        this.context=context;
        shapePaint=new Paint();
        shapePaint.setStrokeWidth(1f);
        shapePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        shapePaint.setAntiAlias(true);
        shapePaint.setColor(Color.parseColor("#23a8ef"));

        textPaint=new Paint();
        textPaint.setStrokeWidth(1f);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.parseColor("#ffffff"));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.width=w;
        this.height=h;
        postInvalidate();
        calculateShapePath();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    /**
     * 计算控件外形的路径坐标
     */
    private void calculateShapePath(){
        if(shapePath==null){
            shapePath=new Path();
        }else{
            shapePath.reset();
        }

        shapePath.moveTo(0,0);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    /**
     *
     * @param canvas
     */
    private void drawShape(Canvas canvas){

    }
}
