package com.xingjiezheng.uidemo.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by xj
 * on 2016/3/25.
 */
public class MaskView extends View {

    private int width;
    private int height;
    private Paint paint;
    private PorterDuffXfermode porterDuffXfermode;

    private IMaskShape iMaskShape;

    private int maskBackgroundColor;
    private Bitmap maskBitmap;
    private Bitmap shapeBitmap;
    private Bitmap allBitmap;

    public MaskView(Context context) {
        this(context, null);
    }

    public MaskView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaskView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }

    private void initData() {
        paint = new Paint();
        paint.setAntiAlias(true);
        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT);
        maskBackgroundColor = 0xaa000000;
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = getWidth();
        height = getHeight();
        drawMask(canvas);
    }

    private void drawMask(Canvas canvas) {
        if (allBitmap == null) {
            allBitmap = getAllBitmap();
        }
        if (allBitmap == null) {
            return;
        }
        canvas.drawBitmap(allBitmap, 0, 0, this.paint);
    }

    private Bitmap getAllBitmap() {
        if (iMaskShape == null || iMaskShape.getRect() == null || width == 0 || height == 0) {
            return null;
        }
        Bitmap target = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        //draw high light shape
        iMaskShape.draw(canvas);
        //draw background mask
        paint.setXfermode(porterDuffXfermode);
        if (maskBitmap == null) {
            maskBitmap = getMaskBitmap();
        }
        canvas.drawBitmap(maskBitmap, 0, 0, paint);
        return target;
    }

    private Bitmap getMaskBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(maskBackgroundColor);
        return bitmap;
    }


    public void setShape(IMaskShape iMaskShape) {
        this.iMaskShape = iMaskShape;
        clear();
        invalidate();
    }

    private void clear() {
        if (allBitmap != null && !allBitmap.isRecycled()) {
            allBitmap.recycle();
        }
        allBitmap = null;
        if (shapeBitmap != null && !shapeBitmap.isRecycled()) {
            shapeBitmap.recycle();
        }
        shapeBitmap = null;
    }

    public void recycle() {
        clear();
        if (maskBitmap != null && !maskBitmap.isRecycled()) {
            maskBitmap.recycle();
        }
        maskBitmap = null;
    }

    public interface OnShapeClickListener {
        void onclick(int index);
    }

    private OnShapeClickListener onShapeClickListener;

    public void setOnShapeClickListener(OnShapeClickListener onShapeClickListener) {
        this.onShapeClickListener = onShapeClickListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int size;
        SparseArray<Rect> rectArray;
        Rect rectClick;
        if (event.getAction() == MotionEvent.ACTION_DOWN
                && iMaskShape != null
                && iMaskShape.getRect() != null
                && onShapeClickListener != null
                && (rectArray = iMaskShape.getIndexList()) != null
                && (size = rectArray.size()) > 0) {
            for (int i = 0; i < size; i++) {
                int key = rectArray.keyAt(i);
                rectClick = rectArray.valueAt(i);
                if (rectClick.left < event.getX() && rectClick.right > event.getX() && rectClick.top < event.getY() && rectClick.bottom > event.getY()) {
                    onShapeClickListener.onclick(key);
                    return true;
                }
            }
        }
        return super.onTouchEvent(event);
    }
}