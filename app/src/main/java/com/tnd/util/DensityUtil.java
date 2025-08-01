package com.tnd.util;

import android.content.Context;

public class DensityUtil {  
	  
    /** 
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素) 
     * 
     *  像素 = dp * 屏幕密度 再四舍五入
     */  
    public static int dip2px(Context context, float dpValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  // 获得屏幕密度
        return (int) (dpValue * scale + 0.5f);  
    }  
  
    /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */  
    public static int px2dip(Context context, float pxValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }  
}  