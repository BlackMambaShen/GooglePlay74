package Utils;

import com.lidroid.xutils.BitmapUtils;

public class BitmapHelper {

    private  static BitmapUtils mBitmapUtils=null;
    //單列，懶漢式
    public static BitmapUtils getBitmapUtils(){
        if (mBitmapUtils==null){
            synchronized (BitmapHelper.class){
                if (mBitmapUtils==null){
                    mBitmapUtils=new BitmapUtils(UIUtils.getContext());
                }
            }
        }
        return mBitmapUtils;
    }
}
