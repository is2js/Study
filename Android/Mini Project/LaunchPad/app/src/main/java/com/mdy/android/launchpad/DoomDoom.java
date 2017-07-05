package com.mdy.android.launchpad;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioTrack;
import android.media.SoundPool;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by MDY on 2017-07-05.
 */

public class DoomDoom extends View {

    public static int NUMB_OF_ROW = 4;
    public static int NUMB_OF_COL = 4;
    Rect[] pads = new Rect[NUMB_OF_COL*NUMB_OF_ROW];
    Paint padPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    SoundPool mSoundPool;


    public DoomDoom(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        for (int i = 0; i < pads.length; i++) {
            pads[i] = new Rect();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        prepareSoundPool();
        preparePaint();
    }

    private void preparePaint() {
        padPaint.setColor(Color.CYAN);
    }

    private void prepareSoundPool() {
        mSoundPool = new SoundPool(8, AudioTrack.MODE_STREAM, 0);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int pad_width = getWidth()/NUMB_OF_COL;
        int pad_height = getHeight()/NUMB_OF_ROW;

        int col_idx = 0;
        int row_idx = 0;

        for (int i = 0; i < pads.length; i++) {
            col_idx = i % NUMB_OF_COL;
            row_idx = i / NUMB_OF_ROW;
            pads[i].set(
                    col_idx * pad_width,
                    row_idx * pad_height,
                    (col_idx + 1) * pad_width,
                    (row_idx + 1) * pad_height
            );
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Rect pad : pads){
            canvas.drawRect(pad, padPaint);
        }

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }


}
