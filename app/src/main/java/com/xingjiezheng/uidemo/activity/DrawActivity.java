package com.xingjiezheng.uidemo.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.xingjiezheng.uidemo.R;
import com.xingjiezheng.uidemo.widget.DrawView;
import com.xingjiezheng.uidemo.widget.MaskAndTipsView;
import com.xingjiezheng.uidemo.widget.MaskView;

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
        getSupportActionBar().hide();


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


        maskAndTipsView = (MaskAndTipsView) findViewById(R.id.maskAndTipsView);
        fab.post(new Runnable() {
            @Override
            public void run() {
                Log.i("fab", "left " + fab.getLeft() + " top " + fab.getTop() + " right " + fab.getRight() + " bottom " + fab.getBottom()
                        + " radius " + (fab.getRight() - fab.getLeft() >> 1));
                maskAndTipsView.showLikeAndDislikeMask(new Rect(fab.getLeft(), fab.getTop(), fab.getRight(), fab.getBottom()), (fab.getRight() - fab.getLeft()) >> 1);
            }
        });

    }
}
