package com.xingjiezheng.uidemo.widget;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.xingjiezheng.uidemo.R;

/**
 * Created by XingjieZheng
 * on 2016/6/13.
 */
public class RemovableView extends RelativeLayout {

    private PointF start;

    public RemovableView(Context context) {
        this(context, null, 0);
    }

    public RemovableView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RemovableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        start = new PointF();
        LayoutInflater.from(getContext()).inflate(R.layout.layout_float_window_view, this, true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                start.set(event.getX(), event.getY());
            }
            break;
            case MotionEvent.ACTION_MOVE: {
                Log.e("event x y", event.getX() + ", " + event.getY());
                float locationX = event.getX() - start.x;
                float locationY = event.getY() - start.y;
//                setX(locationX);
//                setY(locationY);
                Log.i("location x y", locationX + ", " + locationY);
                if (onMoveListener != null) {
                    onMoveListener.onMove(locationX, locationY);
                }
            }
            break;
        }
        return true;
    }


    public interface OnMoveListener {
        void onMove(float x, float y);
    }

    private OnMoveListener onMoveListener;

    public void setOnMoveListener(OnMoveListener onMoveListener) {
        this.onMoveListener = onMoveListener;
    }
}
