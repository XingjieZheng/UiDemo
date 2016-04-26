package com.xingjiezheng.uidemo.widget;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.SparseArray;

/**
 * Created by xj
 * on 2016/4/25.
 */
public class MaskViewFactory {

    private static Paint paint = new Paint(0xff000000);

    static {
        paint.setAntiAlias(true);
    }

    public static IMaskShape createARoundRectMask(Rect rect, int radiusX, int radiusY) {
        ARoundRectMask aRoundRectMask = new ARoundRectMask();
        aRoundRectMask.init(rect, radiusX, radiusY);
        return aRoundRectMask;
    }

    public static IMaskShape createDoubleSameCyclesMask(Rect rect, int radius) {
        DoubleSameCyclesMask doubleSameCyclesMask = new DoubleSameCyclesMask();
        doubleSameCyclesMask.init(rect, radius);
        return doubleSameCyclesMask;
    }

    public static IMaskShape createAIconMask(Rect rect, Bitmap bitmap) {
        AIconMask aIconMask = new AIconMask();
        aIconMask.init(rect, bitmap);
        return aIconMask;
    }

    public static IMaskShape createACycleMask(Rect rect, int radius) {
        ACycleMask aCycleMask = new ACycleMask();
        aCycleMask.init(rect, radius);
        return aCycleMask;
    }

    static abstract class MaskShape implements IMaskShape {
        private Rect rect;
        private SparseArray<Rect> rectArray;

        @Override
        public SparseArray<Rect> getIndexList() {
            return rectArray;
        }

        abstract void setRectArray();
    }

    static abstract class OneMaskShape extends MaskShape {
        @Override
        void setRectArray() {
            if (super.rectArray == null) {
                super.rectArray = new SparseArray<>();
            } else {
                super.rectArray.clear();
            }
            super.rectArray.append(INDEX_0, super.rect);
        }

        @Override
        public Rect getRect() {
            return super.rect;
        }

        public void setRect(Rect rect) {
            super.rect = rect;
        }
    }


    static class ARoundRectMask extends OneMaskShape {

        private RectF rectF;
        private int radiusX;
        private int radiusY;

        public void init(Rect rect, int radiusX, int radiusY) {
            super.setRect(rect);
            this.rectF = new RectF(rect.left, rect.top, rect.right, rect.bottom);
            this.radiusX = radiusX;
            this.radiusY = radiusY;
            setRectArray();
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawRoundRect(rectF, radiusX, radiusY, paint);
        }
    }

    static class ACycleMask extends OneMaskShape {

        private int radius;

        public void init(Rect rect, int radius) {
            super.setRect(rect);
            this.radius = radius;
            setRectArray();
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawCircle(super.getRect().left, super.getRect().top, radius, paint);
        }
    }


    static class AIconMask extends OneMaskShape {

        private Bitmap bitmap;

        public void init(Rect rect, Bitmap bitmap) {
            super.setRect(rect);
            this.bitmap = bitmap;
            setRectArray();
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawBitmap(bitmap, super.getRect().left, super.getRect().top, paint);
        }
    }

    static class DoubleSameCyclesMask extends MaskShape {

        private Rect rect;
        private int radius;


        public void init(Rect rect, int radius) {
            this.rect = rect;
            this.radius = radius;
            setRectArray();
        }

        @Override
        void setRectArray() {
            if (super.rectArray == null) {
                super.rectArray = new SparseArray<>();
            } else {
                super.rectArray.clear();
            }
            super.rectArray.append(INDEX_0, new Rect(rect.left, rect.top, rect.left + (radius << 1), rect.top + (radius << 1)));
            super.rectArray.append(INDEX_1, new Rect(rect.right - (radius << 1), rect.bottom - (radius << 1), rect.right, rect.bottom));
        }

        @Override
        public Rect getRect() {
            return rect;
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawCircle(rect.left + radius, rect.top + radius, radius, paint);
            canvas.drawCircle(rect.right - radius, rect.bottom - radius, radius, paint);
        }
    }
}
