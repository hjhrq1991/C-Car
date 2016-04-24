package com.hjhrq1991.commonview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hjhrq1991.commonview.R;

/**
 * @author hjhrq1991 created at 1/27/16 17 57.
 * @Package com.hjhrq1991.car.View
 * @Description:支持描边的textview
 */
public class StrokeTextView extends TextView {

    private int resourceId = -1;
    private TextView borderText = null;///用于描边的TextView

    public StrokeTextView(Context context) {
        super(context);
        borderText = new TextView(context);
    }

    public StrokeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        borderText = new TextView(context, attrs);
        parseAttributes(context.obtainStyledAttributes(attrs,
                R.styleable.TextStyle));
    }

    private void parseAttributes(TypedArray typedArray) {
        resourceId = typedArray.getColor(R.styleable.TextStyle_StrokeColor, Color.BLACK);
        float strokeWidth = typedArray.getDimension(R.styleable.TextStyle_StrokeWidth, 0f);

        if (strokeWidth > 0f) {
            TextPaint tp1 = borderText.getPaint();
            tp1.setStrokeWidth(strokeWidth);      //设置描边宽度
            tp1.setStyle(Paint.Style.STROKE);     //对文字只描边
            borderText.setTextColor(resourceId);  //设置描边颜色
            borderText.setGravity(getGravity());
        }
        typedArray.recycle();
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        super.setLayoutParams(params);
        borderText.setLayoutParams(params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        CharSequence tt = borderText.getText();

        //两个TextView上的文字必须一致
        if (tt == null || !tt.equals(this.getText())) {
            borderText.setText(getText());
            this.postInvalidate();
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        borderText.measure(widthMeasureSpec, heightMeasureSpec);
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        borderText.layout(left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        borderText.draw(canvas);
        super.onDraw(canvas);
    }
}
