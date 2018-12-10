package cn.innovativest.entertainment.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import cn.innovativest.entertainment.R;

public class MyProgressBar extends ProgressBar {

    private float maxProgress = 100F;
    private float currentProgress = 0F;
    private int bgColor = 0X000000;
    private int progressColor = 0XFF0000;

    private Paint paint;


    public MyProgressBar(Context context) {
        this(context, null);
    }

    public MyProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.MyProgressBar);
        this.maxProgress = mTypedArray.getFloat(R.styleable.MyProgressBar_maxProgress, 100);
        this.currentProgress = mTypedArray.getFloat(R.styleable.MyProgressBar_currentProgress, 0);
        this.bgColor = mTypedArray.getColor(R.styleable.MyProgressBar_backgroundColor, 0X000000);
        this.progressColor = mTypedArray.getColor(R.styleable.MyProgressBar_progressColor, 0XFF0000);
        mTypedArray.recycle();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
    }

    public void setMyProgress(float progress) {
        this.currentProgress = progress;
        invalidate();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(bgColor);
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
        //canvas.drawRoundRect(0, 0, getWidth(), getHeight(),500,500, paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(progressColor);
        float startX = (getWidth() - (currentProgress / maxProgress) * getWidth()) / 2;
        float endX = (getWidth() + (currentProgress / maxProgress) * getWidth()) / 2;
        canvas.drawRect(startX, 0, endX, getHeight(), paint);
        //canvas.drawRoundRect(startX, 0, endX, getHeight(),500,500, paint);
    }
}
