package com.xingjiezheng.uidemo.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xingjiezheng.uidemo.R;

/**
 * Created by xj
 * on 2016/4/25.
 */
public class MaskAndTipsView extends RelativeLayout {

    private Context context;
    private MaskView maskView;
    private TextView textView;
    private int textViewPaddingLeft;
    private int textViewPaddingTop;
    private int textColor;
    private int textSize;
    private int distance;
    private int textLineSpace;

    private RelativeLayout.LayoutParams layoutParams;

    public MaskAndTipsView(Context context) {
        this(context, null, 0);
    }

    public MaskAndTipsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaskAndTipsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        // TODO: 2016/4/25
        textViewPaddingLeft = 20;
        textViewPaddingTop = 15;
        textColor = 0xff6b0cad;
        textSize = 12;
        distance = 10;
        textLineSpace = 5;


        maskView = new MaskView(context);
        maskView.setOnShapeClickListener(new MaskView.OnShapeClickListener() {
            @Override
            public void onclick(int index) {
                Log.e("ShapeClick", "index:" + index);
            }
        });
        addView(maskView);

        textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(textViewPaddingLeft, textViewPaddingTop, textViewPaddingLeft, textViewPaddingTop);
        textView.setTextColor(textColor);
        textView.setTextSize(textSize);
        textView.setBackgroundResource(R.drawable.bg_help);
        textView.setLineSpacing(textLineSpace, 0);
//        addView(textView, layoutParams);
    }

    public void showDisLikeMask(Rect rect, int radius) {
        maskView.setShape(MaskViewFactory.createACycleMask(rect, radius));

        removeView(textView);
        textView.setText("现在开始表态吧！\n祝你好运");
        textView.setY(rect.bottom + distance);
        layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        addView(textView, layoutParams);
    }

}
