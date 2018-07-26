package com.example.liang.googleplay74.holder;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.liang.googleplay74.R;
import com.example.liang.googleplay74.domain.AppInfo;


import Utils.UIUtils;

/*
详情页 应用描述
 */
public class DetailDesHolder extends BaseHolder<AppInfo> {
    private TextView tv_detail_des;
    private TextView tv_detail_author;
    private ImageView iv_arrow;
    private RelativeLayout rl_detail_toggle;
    private LinearLayout.LayoutParams params;


    @Override
    public View initView() {
        View view= UIUtils.inflate(R.layout.layout_detail_desinfo);
        tv_detail_des = (TextView)view.findViewById(R.id.tv_detail_des);
        tv_detail_author = (TextView)view.findViewById(R.id.tv_detail_author);
        iv_arrow = (ImageView)view.findViewById(R.id.iv_arrow);
         rl_detail_toggle = (RelativeLayout) view.findViewById(R.id.rl_detail_toggle);
         rl_detail_toggle.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 toggle();
             }
         });
        return view;
    }

    private boolean isOpen=false;
    private void toggle() {
        int shortHeight = getShortHeight();
        int longHeight = getLongHeight();
        ValueAnimator animator = null;
        if (isOpen){
            //关闭
            isOpen=false;
            if (longHeight>shortHeight){//只有描述信息大于7行，才启动动画
                animator=ValueAnimator.ofInt(longHeight,shortHeight);
            }
        }else {
            //开启
            isOpen=true;
            if (longHeight>shortHeight){
                animator=ValueAnimator.ofInt(shortHeight,longHeight);
            }
        }
        if (animator!=null){//只有描述信息大于7行，才启动动画
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    Integer height = (Integer) animation.getAnimatedValue();
                    params.height=height;
                    tv_detail_des.setLayoutParams(params);
                }
            });
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    //ScrollView要滑动到最底部
                    final ScrollView scrollView = getScrollView();
                    //为了运行更安全稳定，放到消息队列中
                    scrollView.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                        }
                    });
                    if (isOpen){
                        iv_arrow.setImageResource(R.drawable.arrow_up);
                    }else {
                        iv_arrow.setImageResource(R.drawable.arrow_down);
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            animator.setDuration(200);
            animator.start();
        }
    }

    @Override
    public void refreshView(AppInfo data) {
        tv_detail_des.setText(data.des);
        tv_detail_author.setText(data.author);
        //放在消息队列中运行
        tv_detail_des.post(new Runnable() {
            @Override
            public void run() {
                //默认展示7行的高度值
                int shortHeight = getShortHeight();
                params= (LinearLayout.LayoutParams) tv_detail_des.getLayoutParams();
                params.height=shortHeight;
                tv_detail_des.setLayoutParams(params);
            }
        });
    }

    /*
    获取7行textview的高度
     */
    private int getShortHeight(){
        //模拟一个textview,设置最大行数为7行，计算该虚拟tv的高度，从而知道tvDes在展示7行的时候多高
        int width = tv_detail_des.getMeasuredWidth();//宽度
        TextView view=new TextView(UIUtils.getContext());
        view.setText(getData().des);//设置文字
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);//文字大小一致
        view.setMaxLines(7);//最大7行
        int witdhMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);//宽不变，确定值.match
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(2000, View.MeasureSpec.AT_MOST);//高度包裹内容,参1表示最大值
        //开始测量
        view.measure(witdhMeasureSpec,heightMeasureSpec);
        return view.getMeasuredHeight();//返回测量后的高度
    }

    /*
获取完整textview的高度
 */
    private int getLongHeight(){
        //模拟一个textview,设置最大行数为7行，计算该虚拟tv的高度，从而知道tvDes在展示7行的时候多高
        int width = tv_detail_des.getMeasuredWidth();//宽度
        TextView view=new TextView(UIUtils.getContext());
        view.setText(getData().des);//设置文字
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);//文字大小一致
//        view.setMaxLines(7);//最大7行
        int witdhMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);//宽不变，确定值.match
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(2000, View.MeasureSpec.AT_MOST);//高度包裹内容,参1表示最大值
        //开始测量
        view.measure(witdhMeasureSpec,heightMeasureSpec);
        return view.getMeasuredHeight();//返回测量后的高度
    }

    //获取scrollview，一定要保证有scrollView否则死循环
    private ScrollView getScrollView(){
        ViewParent parent = tv_detail_des.getParent();
        while (!(parent instanceof ScrollView)){
            parent=parent.getParent();
        }
        return (ScrollView)parent;
    }
}
