package com.xingjiezheng.uidemo.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.xingjiezheng.uidemo.R;
import com.xingjiezheng.uidemo.util.UiUtils;

/**
 * Created by xj
 * on 2016/3/25.
 */
public class LimitedChatView extends View {

    public static final int STATE_COLLAPSE = 1;
    public static final int STATE_EXPAND = 2;
    private int state = 0;

    private static final int TEXT_SIZE_DP = 12;
    private int backgroundColor;
    private int foregroundColor;
    private Paint paint;
    private RectF rectF;
    private RectF bitmapRectF;

    private int width;
    private int height;
    private int radius;
    private int totalRectWidth;
    private int currentRectWidth;

    private int playBitmapWidth;
    private int playBitmapHeight;
    private Bitmap bitmapDefault;
    private Bitmap bitmapUnread;
    private int unreadCount;
    private int textUnreadBottom;

    private static final int CHANGE_COUNT = 10;
    private int widthChangeOnce;

    private String textLineUp;
    private String textLineDown;
    private int textUpBottom;
    private int textDownBottom;

    private boolean isAble2Show;
    private boolean isAble2Expand;

    public LimitedChatView(Context context) {
        super(context);
        initData();
    }

    public LimitedChatView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    public LimitedChatView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }


    private void initData() {
        rectF = new RectF();
        paint = new Paint();
        paint.setTextSize(UiUtils.dip2px(getContext(), TEXT_SIZE_DP));
        paint.setAntiAlias(true);
        bitmapRectF = new RectF();

        state = STATE_COLLAPSE;
        currentRectWidth = 0;

        bitmapDefault = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.icon_home_chat);
        bitmapUnread = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.icon_home_chat_unread);
        playBitmapWidth = bitmapDefault.getWidth();
        playBitmapHeight = bitmapDefault.getHeight();

        isAble2Show = true;
        isAble2Expand = true;

        backgroundColor = 0xffff6100;
        foregroundColor = 0xffffffff;
        textLineUp = "限时聊天";
        textLineDown = "20:00:05";

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        calculateParams();
        drawRect(canvas);
        drawBitmap(canvas);

    }

    private void calculateParams() {
        width = getWidth();
        height = getHeight();
        totalRectWidth = width - height;
        radius = height >> 1;
        int halfBitmapWidth = playBitmapWidth >> 1;
        bitmapRectF.set(width - radius - halfBitmapWidth,
                (height - playBitmapHeight) >> 1,
                width - radius + halfBitmapWidth,
                (height + playBitmapHeight) >> 1);

        widthChangeOnce = totalRectWidth / CHANGE_COUNT;

        //计算文字的下底
        if (textDownBottom == 0 || textUpBottom == 0 || textUnreadBottom == 0) {
            Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
            textDownBottom = radius + Math.abs(fontMetrics.ascent) + Math.abs(fontMetrics.descent);
            textUpBottom = radius - Math.abs(fontMetrics.descent);
            textUnreadBottom = radius + (Math.abs(fontMetrics.ascent) / 3);
        }
    }

    /**
     * 绘制背景轮廓和文字
     *
     * @param canvas 画布
     */
    private void drawRect(Canvas canvas) {

        switch (state) {
            case STATE_COLLAPSE: {
                if (haveAnimation) {
                    if (currentRectWidth > 0) {
                        currentRectWidth -= widthChangeOnce;
                        if (currentRectWidth < 0) {
                            currentRectWidth = 0;
                        }
                        postInvalidate();
                    }
                } else {
                    if (currentRectWidth != 0) {
                        currentRectWidth = 0;
                        postInvalidate();
                    }
                }
            }
            break;
            case STATE_EXPAND: {
                if (haveAnimation) {
                    if (currentRectWidth < totalRectWidth) {
                        currentRectWidth += widthChangeOnce;
                        if (currentRectWidth > totalRectWidth) {
                            currentRectWidth = totalRectWidth;
                        }
                        postInvalidate();
                    }
                } else {
                    if (currentRectWidth != totalRectWidth) {
                        currentRectWidth = totalRectWidth;
                        postInvalidate();
                    }
                }
            }
            break;
        }

        paint.setColor(backgroundColor);
        //画左半圆
        rectF.left = width - height - currentRectWidth;
        rectF.top = 0;
        rectF.right = width - currentRectWidth;
        rectF.bottom = height;
        canvas.drawArc(rectF, 90, 180, true, paint);

        //画矩形
        canvas.drawRect(width - radius - currentRectWidth, 0, width - radius, height,
                paint);

        //画文字
        if (currentRectWidth != 0) {
            paint.setColor(foregroundColor);
            paint.setFakeBoldText(false);
            int textUpWidth = (int) paint.measureText(textLineUp);
            paint.setFakeBoldText(true);
            int textDownWidth = (int) paint.measureText(textLineDown);
            int textUpWidthHalf = textUpWidth >> 1;
            int textDownWidthHalf = textDownWidth >> 1;
            if (textDownWidthHalf > textUpWidthHalf) {
                paint.setFakeBoldText(false);
                canvas.drawText(textLineUp,
                        width - height - textDownWidth + (totalRectWidth - currentRectWidth) + (textDownWidthHalf - textUpWidthHalf),
                        textUpBottom,
                        paint);
                paint.setFakeBoldText(true);
                canvas.drawText(textLineDown,
                        width - height - textDownWidth + (totalRectWidth - currentRectWidth),
                        textDownBottom,
                        paint);
            } else {
                paint.setFakeBoldText(false);
                canvas.drawText(textLineUp,
                        width - height - textUpWidth + (totalRectWidth - currentRectWidth),
                        textUpBottom,
                        paint);
                paint.setFakeBoldText(true);
                canvas.drawText(textLineDown,
                        width - height - textUpWidth + (totalRectWidth - currentRectWidth) + (textUpWidthHalf - textDownWidthHalf),
                        textDownBottom,
                        paint);
            }
        }

        //画右圆
        paint.setColor(backgroundColor);
        rectF.left = width - height;
        rectF.top = 0;
        rectF.right = width;
        rectF.bottom = height;
        canvas.drawArc(rectF, -90, 360, true, paint);


    }


    private void drawBitmap(Canvas canvas) {
        if (unreadCount > 0) {
            canvas.drawBitmap(bitmapUnread, bitmapRectF.left, bitmapRectF.top, paint);
            paint.setFakeBoldText(false);
            paint.setColor(backgroundColor);
            String textUnreadCount = String.valueOf(unreadCount);
            int textUnreadWidthHalf = (int) paint.measureText(textUnreadCount) >> 1;
            canvas.drawText(textUnreadCount,
                    width - radius - textUnreadWidthHalf,
                    textUnreadBottom,
                    paint);
            paint.setColor(0xffff0000);
        } else {
            canvas.drawBitmap(bitmapDefault, bitmapRectF.left, bitmapRectF.top, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (state == STATE_COLLAPSE) {
            return event.getX() > (width - height) && event.getX() < width && super.onTouchEvent(event);
        } else {
            return super.onTouchEvent(event);
        }
    }

    public void collapse(boolean haveAnimation) {
        if (state == STATE_COLLAPSE) {
            return;
        }
        this.haveAnimation = haveAnimation;
        state = STATE_COLLAPSE;
        postInvalidate();
    }

    public void expand(boolean haveAnimation) {
        if (state == STATE_EXPAND || !isAble2Expand) {
            return;
        }
        this.haveAnimation = haveAnimation;
        state = STATE_EXPAND;
        postInvalidate();
    }

    private boolean haveAnimation;

    public void setTextLineUp(String textLineUp) {
        this.textLineUp = textLineUp;
        postInvalidate();
    }

    public void setTextLineDown(String textLineDown) {
        this.textLineDown = textLineDown;
        postInvalidate();
    }

    public void show() {
        if (!isAble2Show) {
            return;
        }
        if (getVisibility() != View.VISIBLE) {
            setVisibility(View.VISIBLE);
        }
    }

    public void hide() {
        if (!isAble2Show) {
            return;
        }
        if (getVisibility() != View.GONE) {
            setVisibility(View.GONE);
        }
    }

    public void setIsAble2Show(boolean isAble2Show) {
        this.isAble2Show = isAble2Show;
    }

    public void setUnreadCount(int unreadCount) {
        if (unreadCount < 0) {
            unreadCount = 0;
        } else if (unreadCount > 99) {
            unreadCount = 99;
        }
        this.unreadCount = unreadCount;
        postInvalidate();
    }

    public void recycle() {
        if (bitmapDefault != null && !bitmapDefault.isRecycled()) {
            bitmapDefault.recycle();
        }
        if (bitmapUnread != null && !bitmapUnread.isRecycled()) {
            bitmapUnread.recycle();
        }
    }

    public boolean isShow() {
        return getVisibility() == View.VISIBLE;
    }

    public boolean isAble2Expand() {
        return isAble2Expand;
    }

    public void setIsAble2Expand(boolean isAble2Expand) {
        this.isAble2Expand = isAble2Expand;
    }


}