package com.xingjiezheng.uidemo.activity;

import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.xingjiezheng.uidemo.R;
import com.xingjiezheng.uidemo.widget.DrawView;
import com.xingjiezheng.uidemo.widget.IMaskShape;
import com.xingjiezheng.uidemo.widget.MaskAndTipsView;
import com.xingjiezheng.uidemo.widget.MaskView;
import com.xingjiezheng.uidemo.widget.MaskViewFactory;

public class DrawActivity extends AppCompatActivity {

    private DrawView drawView;
    private boolean isExpand = true;
    private FloatingActionButton fab;
    private boolean isShow = true;

    private MaskView maskView;
    private MaskAndTipsView maskAndTipsView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        drawView = (DrawView) findViewById(R.id.drawView);
        drawView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpand) {
                    drawView.expand(true);
                } else {
                    drawView.collapse(true);
                }
                isExpand = !isExpand;
            }
        });

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShow) {
                    drawView.hide();
                } else {
                    drawView.show();
                }
                isShow = !isShow;
            }
        });

//        maskView = (MaskView) findViewById(R.id.maskView);
////        maskView.setShape(MaskViewFactory.createARoundRectMask(new Rect(100, 500, 900, 700), 100, 100));
////        maskView.setShape(MaskViewFactory.createDoubleSameCyclesMask(new Rect(100, 500, 900, 700), 100));
//        maskView.setShape(MaskViewFactory.createAIconMask(new Rect(100, 500, 900, 700),
//                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)));
//        maskView.setOnShapeClickListener(new MaskView.OnShapeClickListener() {
//            @Override
//            public void onclick(int index) {
//                Log.e("ShapeClick", "index:" + index);
//            }
//        });

        maskAndTipsView = (MaskAndTipsView) findViewById(R.id.maskAndTipsView);
        maskAndTipsView.showDisLikeMask(new Rect(400, 400, 600, 600), 100);
    }
}
