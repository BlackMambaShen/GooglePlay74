package Utils;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

public class DrawableUtils {
    //获取形状
    public static GradientDrawable getGradientDrawable(int color, int radius){
        //Xml中定义的shape标签 对应此类
        GradientDrawable shape =new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);//矩形
        shape.setCornerRadius(radius);
        shape.setColor(color);
        return shape;
    }

    //获取状态选择器
    public static StateListDrawable getSelector(Drawable normal, Drawable press){
        StateListDrawable selector=new StateListDrawable();
        selector.addState(new int[]{android.R.attr.state_pressed},press);//按下图片
        selector.addState(new int[]{},normal);
        return selector;
    }
}
