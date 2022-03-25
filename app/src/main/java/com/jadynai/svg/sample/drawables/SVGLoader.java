package com.jadynai.svg.sample.drawables;


import com.jadynai.kotlindiary.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.LongSparseArray;

import com.github.megatronking.svg.support.SVGDrawable;
import com.github.megatronking.svg.support.SVGHelper;

/**
 * AUTO-GENERATED FILE.  DO NOT MODIFY.
 * 
 * This class was automatically generated by the
 * SVG-Generator. It should not be modified by hand.<br><br>
 *
 * Call the follow in your Application: 
 *
 * <pre class="prettyprint">
 * public class MyApplication extends Application {
 *
 *    public void onCreate() {
 *        SVGLoader.load(this)
 *    }
 *
 * }
 * </pre>
 */
public class SVGLoader  {

    private static LongSparseArray<Drawable.ConstantState> sPreloadedDrawables;

    public static void load(Context context) {
        sPreloadedDrawables = SVGHelper.hackPreloadDrawables(context.getResources());
        if (sPreloadedDrawables == null) {
            return;
        }
        add(context, R.drawable.ic_ico_settings_selected, SVGDrawable.SVGDrawableConstantState.create(new ic_ico_settings_selected(context)));
        add(context, R.drawable.ic_arrow, SVGDrawable.SVGDrawableConstantState.create(new ic_arrow(context)));
    }

    private static void add(Context context, int resId, SVGDrawable.SVGDrawableConstantState state) {
        sPreloadedDrawables.put(SVGHelper.resKey(context, resId), state);
    }

}