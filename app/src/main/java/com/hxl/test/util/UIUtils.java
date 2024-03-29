package com.hxl.test.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import com.hxl.test.Application;
import com.hxl.test.R;


/**
 * Created by Huaxl on 2016/10/31.
 * Update by JackYang
 */

public class UIUtils {

    public static final String RED = "red";
    public static final String BLUE = "blue";

    /**
     * 根据资源名称获取对应的资源ID
     */
    public static int getResourcesFromName(String drawableName) {
        int id = Application.mApplication.getResources().getIdentifier(drawableName, "drawable",
                Application.mApplication.getPackageName());
        return id;
    }

    /**
     * 资源名称转Drawable
     */
    public static Drawable getDrawableFromName(String drawableName) {
        return UIUtils.getDrawable(UIUtils.getResourcesFromName(drawableName));
    }

    /**
     * 根据资源ID转Drawable
     */
    public static Drawable getDrawable(int id) {
        return Application.mApplication.getResources().getDrawable(id);
    }

    /**
     * 获取字符串
     */
    public static String getString(int id) {
        return Application.mApplication.getResources().getString(id);
    }

    /**
     * 获取颜色
     */
    public static int getColor(int id) {
        return Application.mApplication.getResources().getColor(id);
    }

    /**
     * drawable 转 bitmap
     * 注意！此方法转换的出的 Bitmap 为 565 格式，没有透明度。
     *
     * @param drawableId 资源 id
     * @return bitmap
     */
    public static Bitmap drawableToBitmap(int drawableId) {
        Drawable drawable = Application.mApplication.getResources().getDrawable(drawableId);
        return drawableToBitmap(drawable);
    }

    /**
     * drawable 转 bitmap
     * 注意！此方法转换的出的 Bitmap 为 565 格式，没有透明度。
     *
     * @param drawable Drawable
     * @return bitmap
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.RGB_565
                        : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        //canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 删除父view
     *
     * @param view
     */
    public static void stripView(View view) {
        ViewParent parent = view.getParent();
        if (parent != null && parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(view);
        }
    }

    /**
     * 设置一个TextView多个超链接
     *
     * @param content  内容
     * @param start    可点击文本的开始角标
     * @param end      可点击文本的结束角标
     * @param listener 超链接点击监听
     */
    public static void setTextViewHyperlink(final TextView textView, final String content, final int textColor, final int start, final int end, final OnTextViewhyperlinkOnClickListener listener) {
        SpannableStringBuilder builder = new SpannableStringBuilder(content);
        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                if (listener != null) {
                    listener.onClick(content.substring(start, end));
                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(textColor);
                ds.setUnderlineText(false); // 设置下划线
            }
        }, start, end, 0);
        textView.setText(builder);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        //
        textView.setHighlightColor(UIUtils.getColor(R.color.transparent));
    }

    /**
     * 设置一个TextView多个超链接
     *
     * @param content  内容
     * @param start1   可点击文本1的开始角标
     * @param end1     可点击文本1的结束角标
     * @param start2   可点击文本2的开始角标
     * @param end2     可点击文本2的结束角标
     * @param listener 超链接点击监听
     */
    public static void setTextViewHyperlink(final TextView textView, final String content, final int textColor, final int start1, final int end1, final int start2, final int end2, final OnTextViewhyperlinkOnClickListener listener) {
        SpannableStringBuilder builder = new SpannableStringBuilder(content);
        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                if (listener != null) {
                    listener.onClick(content.substring(start1, end1));
                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(textColor);
                ds.setUnderlineText(false); // 设置下划线
            }
        }, start1, end1, 0);

        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                if (listener != null) {
                    listener.onClick(content.substring(start2, end2));
                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(textColor);
                ds.setUnderlineText(false); // 设置下划线
            }
        }, start2, end2, 0);


        textView.setText(builder);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setHighlightColor(UIUtils.getColor(R.color.transparent));
    }

    public static int dip2px(float dipValue) {
        float scale = Application.mApplication.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5F);
    }

    /***
     * 超文本连接点击监听
     *
     * @author Administrator
     */
    public interface OnTextViewhyperlinkOnClickListener {
        /***
         * 根据正则匹配的超文本点击监听
         *
         * @param text
         */
         void onClick(String text);
    }
}
