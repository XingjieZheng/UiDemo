package com.xingjiezheng.uidemo.activity;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.xingjiezheng.uidemo.R;
import com.xingjiezheng.uidemo.widget.RemovableView;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class WindowManagerActivity extends AppCompatActivity {

    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;
    private RelativeLayout mFloatLayout;
    private RemovableView removableView;

    private int maxLocationX = 1080;
    private int maxLocationY = 1920;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_manager);
        ButterKnife.bind(this);
        createWindowView();
    }

    @OnClick(R.id.btnStart)
    void clickStartButton() {
        addWindowView();
    }

    @OnClick(R.id.btnEnd)
    void clickEndButton() {
        removeWindowView();
    }


    private void createWindowView() {
        windowManager = (WindowManager) getApplication().getSystemService(Context.WINDOW_SERVICE);
        layoutParams = new WindowManager.LayoutParams();
        layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        layoutParams.gravity = Gravity.START | Gravity.TOP;
        layoutParams.x = 0;
        layoutParams.y = 0;
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        LayoutInflater inflater = LayoutInflater.from(getApplication());
        mFloatLayout = (RelativeLayout) inflater.inflate(R.layout.layout_float_window, null);
        removableView = (RemovableView) mFloatLayout.findViewById(R.id.removableView);
        removableView.setOnMoveListener(new RemovableView.OnMoveListener() {
            @Override
            public void onMove(float x, float y) {
                layoutParams.x += (int) x;
                layoutParams.y += (int) y;
                if (layoutParams.x > maxLocationX) {
                    layoutParams.x = maxLocationX;
                }
                if (layoutParams.y > maxLocationY) {
                    layoutParams.y = maxLocationY;
                }
                updateWindowView();
            }
        });
    }

    private void addWindowView() {
        if (windowManager != null && mFloatLayout != null && layoutParams != null) {
            windowManager.addView(mFloatLayout, layoutParams);
        }
    }

    private void removeWindowView() {
        if (windowManager != null && mFloatLayout != null) {
            windowManager.removeView(mFloatLayout);
        }
    }

    private void updateWindowView() {
        if (windowManager != null && mFloatLayout != null && layoutParams != null) {
            windowManager.updateViewLayout(mFloatLayout, layoutParams);
        }
    }
}
