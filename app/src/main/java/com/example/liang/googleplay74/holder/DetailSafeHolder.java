package com.example.liang.googleplay74.holder;


import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.liang.googleplay74.R;
import com.example.liang.googleplay74.domain.AppInfo;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;

import Utils.BitmapHelper;
import Utils.UIUtils;

/*
安全详情页面
 */
public class DetailSafeHolder extends BaseHolder<AppInfo> {
    private ImageView[] safeIcons;//安全标示图片
    private ImageView[] desIcons;//安全描述图片
    private TextView[]safeDes;//安全描述文字
    private LinearLayout[]safeDesBar;//安全描述条目
    private BitmapUtils bitmapUtils;
    private RelativeLayout rlDesRoot;
    private LinearLayout ll_des_root;
    private int measuredHeight;
    private LinearLayout.LayoutParams params;
    private ImageView ivArrow;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.layout_detail_safeinfo);
        safeIcons=new ImageView[4];
        safeIcons[0]=(ImageView) view.findViewById(R.id.iv_safe1);
        safeIcons[1]=(ImageView) view.findViewById(R.id.iv_safe2);
        safeIcons[2]=(ImageView) view.findViewById(R.id.iv_safe3);
        safeIcons[3]=(ImageView) view.findViewById(R.id.iv_safe4);

        desIcons=new ImageView[4];
        desIcons[0]=(ImageView) view.findViewById(R.id.iv_des1);
        desIcons[1]=(ImageView) view.findViewById(R.id.iv_des2);
        desIcons[2]=(ImageView) view.findViewById(R.id.iv_des3);
        desIcons[3]=(ImageView) view.findViewById(R.id.iv_des4);

        safeDes=new TextView[4];
        safeDes[0]=(TextView) view.findViewById(R.id.tv_des1);
        safeDes[1]=(TextView) view.findViewById(R.id.tv_des2);
        safeDes[2]=(TextView) view.findViewById(R.id.tv_des3);
        safeDes[3]=(TextView) view.findViewById(R.id.tv_des4);

        safeDesBar=new LinearLayout[4];
        safeDesBar[0]=(LinearLayout) view.findViewById(R.id.ll_des1);
        safeDesBar[1]=(LinearLayout) view.findViewById(R.id.ll_des2);
        safeDesBar[2]=(LinearLayout) view.findViewById(R.id.ll_des3);
        safeDesBar[3]=(LinearLayout) view.findViewById(R.id.ll_des4);

        rlDesRoot=(RelativeLayout) view.findViewById(R.id.rl_des_root);
        rlDesRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });
         bitmapUtils = BitmapHelper.getBitmapUtils();
         ll_des_root = (LinearLayout)view.findViewById(R.id.ll_des_root);
        ivArrow = (ImageView) view.findViewById(R.id.iv_arrow);
        return view;
    }

    private boolean isOpen=false;//安全标记开关状态，默认关
    //打开或者关闭安全描述信息
    private void toggle() {
        ValueAnimator animator=null;
        if (isOpen){
            //关闭
            isOpen=false;
            //属性动画
             animator = ValueAnimator.ofInt(measuredHeight, 0);//从某个值变化到某个值
        }else {
            //开启
            isOpen=true;
            //属性动画
             animator = ValueAnimator.ofInt(0, measuredHeight);
        }
        //动画更新的监听
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            //启动动画之后，不断回调此方法
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //获取最新的高度值
                Integer height= (Integer) animation.getAnimatedValue();
                //重新修改布局高度
                params.height=height;
                ll_des_root.setLayoutParams(params);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //动画结束事件
                //更新小箭头方向
                if (isOpen){
                    ivArrow.setImageResource(R.drawable.arrow_up);
                }else {
                    ivArrow.setImageResource(R.drawable.arrow_down);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.setDuration(200);//动画时间
        animator.start();
    }

    @Override
    public void refreshView(AppInfo data) {
        ArrayList<AppInfo.SafeInfo> safe = data.safe;
        for (int i = 0; i <4 ; i++) {
            if (i<safe.size()){
                //安全标识图片
                AppInfo.SafeInfo safeInfo = safe.get(i);
                bitmapUtils.display(safeIcons[i],UIUtils.getIpConfig()+safeInfo.safeUrl);
                //安全描述文字
                safeDes[i].setText(safeInfo.safeDes);
                //安全描述图片
                bitmapUtils.display(desIcons[i],UIUtils.getIpConfig()+safeInfo.safeDesUrl);
            }else {
                //剩下不该显示的图片
                safeIcons[i].setVisibility(View.GONE);
                safeDesBar[i].setVisibility(View.GONE);
            }
        }
        //获取安全描述完整高度
        ll_des_root.measure(0,0);
         measuredHeight = ll_des_root.getMeasuredHeight();
         //修改安全描述布局高度为0，达到隐藏效果
         params= (LinearLayout.LayoutParams) ll_des_root.getLayoutParams();
         params.height=0;
         ll_des_root.setLayoutParams(params);
    }
}
