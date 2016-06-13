package com.xingjiezheng.uidemo.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
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
    private RelativeLayout layoutTips;
    private ImageView imageArrow;
    private TextView tvTipsContent;
    private int distance;

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
        findViews();
        setListeners();
    }

    private void init() {
        // TODO: 2016/4/25
        distance = 10;

    }

    private void findViews() {
        LayoutInflater.from(context).inflate(R.layout.mask_and_tips_view, this, true);
        maskView = (MaskView) findViewById(R.id.maskView);
        layoutTips = (RelativeLayout) findViewById(R.id.layoutTips);
        imageArrow = (ImageView) findViewById(R.id.imageArrow);
        tvTipsContent = (TextView) findViewById(R.id.tvTipsContent);
    }

    private void setListeners() {
        maskView.setOnShapeClickListener(new MaskView.OnShapeClickListener() {
            @Override
            public void onclick(int index) {
                Log.e("ShapeClick", "index:" + index);
            }
        });
    }

    public void showDisLikeMask(Rect rect, int radius) {
        maskView.setShape(MaskViewFactory.createACycleMask(rect, radius));
        setTipsViewParams(false, rect.bottom + distance, this.getWidth() - (rect.right - radius), "点此代表不喜欢，你不会喜欢Ta\n我们也不会告诉Ta");
    }

    public void showLikeMask(Rect rect, int radius) {
        maskView.setShape(MaskViewFactory.createACycleMask(rect, radius));
        setTipsViewParams(false, rect.bottom + distance, this.getWidth() - (rect.right - radius), "点此代表喜欢，如果Ta也喜欢你\\n你们就可以成为好友");
//        setTipsViewParams(true, rect.bottom + distance, rect.left + radius, "点此代表喜欢，如果Ta也喜欢你\n你们就可以成为好友");
    }

    public void showLikeAndDislikeMask(Rect rect, int radius) {
        maskView.setShape(MaskViewFactory.createACycleMask(rect, radius));
        setTipsViewParams(false, rect.bottom + distance, this.getWidth() - (rect.right - radius), "\u3000\u3000现在开始表态吧！\u3000\u3000\n\u3000\u3000祝你好运");
    }

    private void setTipsViewParams(final boolean isLeft, int tipsLocationY, final int arrowDistance, String tvContent) {
        layoutTips.setY(tipsLocationY);
        tvTipsContent.setText(tvContent);
        layoutTips.post(new Runnable() {
            @Override
            public void run() {
                removeView(layoutTips);
                layoutParams = (RelativeLayout.LayoutParams) layoutTips.getLayoutParams();
                if (isLeft) {
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    imageArrow.setX(arrowDistance - (imageArrow.getWidth() >> 1));
                } else {
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    imageArrow.setX(layoutTips.getWidth() - arrowDistance - (imageArrow.getWidth() >> 1));
                }
                addView(layoutTips, layoutParams);
            }
        });
    }
}
