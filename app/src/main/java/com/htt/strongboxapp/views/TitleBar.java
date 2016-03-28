package com.htt.strongboxapp.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.htt.strongboxapp.R;
import com.htt.strongboxapp.utils.UnreadMsgUtils;
import com.htt.strongboxapp.views.widgets.roundview.RoundTextView;

import static com.htt.strongboxapp.R.color.colorPrimary;

/**
 * Created by Administrator on 2016/3/21.
 */
public class TitleBar extends RelativeLayout{
    private Context context;
    private TextView tvTitleBarBack;
    private RoundTextView tvTitleBarMsgTip;
    private TextView tvTitleBarTitle;
    private ViewGroup layoutTitleBarLeft;

    public TitleBar(Context context) {
        super(context);
        initViews(context);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initViews(context);
    }

    private void initViews(Context context){
        this.context=context;
        LayoutInflater.from(context).inflate(R.layout.layout_titlebar_content, this, true);
        this.setBackgroundResource(R.color.colorPrimary);
        this.tvTitleBarBack=(TextView)this.findViewById(R.id.tv_titlebar_left);
        this.tvTitleBarMsgTip=(RoundTextView)this.findViewById(R.id.tv_titlebar_left_msg_tip);
        this.tvTitleBarTitle=(TextView)this.findViewById(R.id.tv_titlebar_title);
        this.layoutTitleBarLeft=(ViewGroup)this.findViewById(R.id.layout_title_bar_left);
    }

    public void setTitle(String title){
        if(!TextUtils.isEmpty(title)&&this.tvTitleBarTitle!=null){
            tvTitleBarTitle.setText(title);
        }
    }

    public void setTitleBarLeftOnClickListener(OnClickListener listener){
        if(layoutTitleBarLeft!=null) {
            layoutTitleBarLeft.setOnClickListener(listener);
        }
    }

    public void setTitleBarLeftVisibility(int visibility){
        if(layoutTitleBarLeft!=null){
            layoutTitleBarLeft.setVisibility(visibility);
        }
    }
}
