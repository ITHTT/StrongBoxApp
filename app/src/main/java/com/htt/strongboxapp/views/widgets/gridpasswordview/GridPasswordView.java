package com.htt.strongboxapp.views.widgets.gridpasswordview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.htt.strongboxapp.R;
import com.htt.strongboxapp.utils.DensityUtil;


public class GridPasswordView extends LinearLayout implements PasswordViewInterface,View.OnFocusChangeListener{
    private static final int DEFAULT_PASSWORDLENGTH = 6;
    private static final int DEFAULT_TEXTSIZE = 16;
    private static final String DEFAULT_TRANSFORMATION = "●";
    private static final int DEFAULT_LINECOLOR = 0xaa888888;
    private static final int DEFAULT_GRIDCOLOR = 0xffffffff;

    private ColorStateList textColor;
    private int textSize = DEFAULT_TEXTSIZE;

    private int lineWidth;
    private int lineColor;
    private int gridColor;
    private Drawable lineDrawable;
    private Drawable outerLineDrawable;

    private int passwordLength;
    //单字符
    private String passwordTransformation;
    private int passwordType;

    private ImeDelBugFixedEditText inputView;

    private String[] passwordArr;
    private TextView[] viewArr;

    private int inputItemWidth;//输入单元的宽度
    private int inputItemHeight;//输入单元的高度
    /**是否可编辑*/
    private boolean isEditable=true;

    private OnPasswordChangedListener listener;

    private PasswordTransformationMethod transformationMethod;

    public GridPasswordView(Context context) {
        this(context, null);
    }

    public GridPasswordView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GridPasswordView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs, defStyleAttr);
        initViews(context);
    }

    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.GridPasswordView, defStyleAttr, 0);

        textColor = ta.getColorStateList(R.styleable.GridPasswordView_textColor);
        if (textColor == null)
            textColor = ColorStateList.valueOf(getResources().getColor(android.R.color.primary_text_light));
        int textSize = ta.getDimensionPixelSize(R.styleable.GridPasswordView_textSize, -1);
        if (textSize != -1) {
            this.textSize = (int) DensityUtil.px2sp(context, textSize);
        }

        lineWidth = (int) ta.getDimension(R.styleable.GridPasswordView_lineWidth, DensityUtil.dip2px(getContext(), 1));
        lineColor = ta.getColor(R.styleable.GridPasswordView_lineColor, DEFAULT_LINECOLOR);
        gridColor = ta.getColor(R.styleable.GridPasswordView_gridColor, DEFAULT_GRIDCOLOR);
        lineDrawable = ta.getDrawable(R.styleable.GridPasswordView_lineColor);

        inputItemWidth=(int)ta.getDimension(R.styleable.GridPasswordView_inputItemWith,DensityUtil.dip2px(getContext(),50));
        inputItemHeight=(int)ta.getDimension(R.styleable.GridPasswordView_inputItemHeight,DensityUtil.dip2px(getContext(),50));

        isEditable=ta.getBoolean(R.styleable.GridPasswordView_isEditable,true);
        if (lineDrawable == null)
            lineDrawable = new ColorDrawable(lineColor);
        outerLineDrawable = generateBackgroundDrawable();

        passwordLength = ta.getInt(R.styleable.GridPasswordView_passwordLength, DEFAULT_PASSWORDLENGTH);
        passwordTransformation = ta.getString(R.styleable.GridPasswordView_passwordTransformation);
        if (TextUtils.isEmpty(passwordTransformation))
            passwordTransformation = DEFAULT_TRANSFORMATION;


        passwordType = ta.getInt(R.styleable.GridPasswordView_passwordType, 0);

        ta.recycle();

        passwordArr = new String[passwordLength];
        viewArr = new TextView[passwordLength];
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void initViews(Context context) {
        super.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.bg_input_text_default));
        setShowDividers(SHOW_DIVIDER_NONE);
        setOrientation(HORIZONTAL);

        transformationMethod = new CustomPasswordTransformationMethod(passwordTransformation);
        inflaterViews(context);
        this.setClickable(true);
    }

    private void inflaterViews(Context context) {

        inputView=new ImeDelBugFixedEditText(getContext());
        inputView.setGravity(Gravity.CENTER);
        inputView.setSingleLine(true);
        inputView.setCursorVisible(false);
        inputView.setBackgroundResource(0);
        inputView.setEnabled(isEditable);

        inputView.setMaxEms(passwordLength);
        inputView.addTextChangedListener(textWatcher);
        inputView.setDelKeyEventListener(onDelKeyEventListener);
        if(isEditable) {
            inputView.setOnFocusChangeListener(this);
        }

        setCustomAttr(inputView);
        LayoutParams layoutParams=new LayoutParams(inputItemWidth,inputItemHeight);
        addView(inputView,layoutParams);
        viewArr[0] = inputView;

        int index = 1;
        while (index < passwordLength) {
            View dividerView = LayoutInflater.from(getContext()).inflate(R.layout.layout_divider_line, null);
            LayoutParams dividerParams = new LayoutParams(lineWidth, LayoutParams.MATCH_PARENT);
            dividerView.setBackgroundDrawable(lineDrawable);
            addView(dividerView, dividerParams);

            TextView textView = new TextView(getContext());
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundResource(0);
            setCustomAttr(textView);
            LayoutParams textViewParams = new LayoutParams(inputItemWidth, inputItemWidth);
            addView(textView, textViewParams);

            viewArr[index] = textView;
            index++;
        }

        setOnClickListener(onClickListener);
    }

    private void setCustomAttr(TextView view) {
        if (textColor != null)
            view.setTextColor(textColor);
        view.setTextSize(textSize);

        int inputType = InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD;
        switch (passwordType) {

            case 1:
                inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
                break;

            case 2:
                inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;
                break;

            case 3:
                inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD;
                break;
        }
        view.setInputType(inputType);
        view.setTransformationMethod(transformationMethod);
    }

    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if(isEditable) {
                forceInputViewGetFocus();
            }
        }
    };

    private Drawable generateBackgroundDrawable() {
       /*GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(gridColor);
        drawable.setStroke(lineWidth, lineColor);*/

        Drawable drawable=getContext().getResources().getDrawable(R.drawable.bg_input_text);

        return drawable;
    }

    private void forceInputViewGetFocus() {
        if(isEditable) {
            inputView.setFocusable(true);
            inputView.setFocusableInTouchMode(true);
            inputView.requestFocus();
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(inputView, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    private ImeDelBugFixedEditText.OnDelKeyEventListener onDelKeyEventListener = new ImeDelBugFixedEditText.OnDelKeyEventListener() {

        @Override
        public void onDeleteClick() {
            for (int i = passwordArr.length - 1; i >= 0; i--) {
                if (passwordArr[i] != null) {
                    passwordArr[i] = null;
                    viewArr[i].setText(null);
                    notifyTextChanged();
                    break;
                } else {
                    viewArr[i].setText(null);
                }
            }
        }
    };

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s == null) {
                return;
            }

            String newStr = s.toString();
            if (newStr.length() == 1) {
                passwordArr[0] = newStr;
                notifyTextChanged();
            } else if (newStr.length() == 2) {
                String newNum = newStr.substring(1);
                for (int i = 0; i < passwordArr.length; i++) {
                    if (passwordArr[i] == null) {
                        passwordArr[i] = newNum;
                        viewArr[i].setText(newNum);
                        notifyTextChanged();
                        break;
                    }
                }
                inputView.removeTextChangedListener(this);
                inputView.setText(passwordArr[0]);
                inputView.setSelection(1);
                inputView.addTextChangedListener(this);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Deprecated
    private OnKeyListener onKeyListener = new OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                onDelKeyEventListener.onDeleteClick();
                return true;
            }
            return false;
        }
    };

    private void notifyTextChanged() {
        if (listener == null)
            return;

        String currentPsw = getPassWord();
        listener.onChanged(currentPsw);

        if (currentPsw.length() == passwordLength)
            listener.onMaxLength(currentPsw);

    }

    public int getPasswordLength(){
        return passwordLength;
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putStringArray("passwordArr", passwordArr);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            passwordArr = bundle.getStringArray("passwordArr");
            state = bundle.getParcelable("instanceState");
            inputView.removeTextChangedListener(textWatcher);
            setPassword(getPassWord());
            inputView.addTextChangedListener(textWatcher);
        }
        super.onRestoreInstanceState(state);
    }

    //TODO
    //@Override
    private void setError(String error) {
        inputView.setError(error);
    }

    /**
     * Return the text the PasswordView is displaying.
     */
    @Override
    public String getPassWord() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < passwordArr.length; i++) {
            if (passwordArr[i] != null)
                sb.append(passwordArr[i]);
        }
        return sb.toString();
    }

    /**
     * Clear the passwrod the PasswordView is displaying.
     */
    @Override
    public void clearPassword() {
        for (int i = 0; i < passwordArr.length; i++) {
            passwordArr[i] = null;
            viewArr[i].setText(null);
        }
    }

    /**
     * Sets the string value of the PasswordView.
     */
    @Override
    public void setPassword(String password) {
        clearPassword();

        if (TextUtils.isEmpty(password))
            return;

        char[] pswArr = password.toCharArray();
        for (int i = 0; i < pswArr.length; i++) {
            if (i < passwordArr.length) {
                passwordArr[i] = pswArr[i] + "";
                viewArr[i].setText(passwordArr[i]);
            }
        }
    }

    /**
     * Set the enabled state of this view.
     */
    @Override
    public void setPasswordVisibility(boolean visible) {
        for (TextView textView : viewArr) {
            textView.setTransformationMethod(visible ? null : transformationMethod);
            if (textView instanceof EditText) {
                EditText et = (EditText) textView;
                et.setSelection(et.getText().length());
            }
        }
    }

    /**
     * Toggle the enabled state of this view.
     */
    @Override
    public void togglePasswordVisibility() {
        boolean currentVisible = getPassWordVisibility();
        setPasswordVisibility(!currentVisible);
    }

    /**
     * Get the visibility of this view.
     */
    private boolean getPassWordVisibility() {
        return viewArr[0].getTransformationMethod() == null;
    }

    /**
     * Register a callback to be invoked when password changed.
     */
    @Override
    public void setOnPasswordChangedListener(OnPasswordChangedListener listener) {
        this.listener = listener;
    }

    @Override
    public void setPasswordType(PasswordType passwordType) {
        boolean visible = getPassWordVisibility();
        int inputType = InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD;
        switch (passwordType) {

            case TEXT:
                inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
                break;

            case TEXTVISIBLE:
                inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;
                break;

            case TEXTWEB:
                inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD;
                break;
        }

        for (TextView textView : viewArr)
            textView.setInputType(inputType);

        setPasswordVisibility(visible);
    }

    @Override
    public void setBackground(Drawable background) {
    }

    @Override
    public void setBackgroundColor(int color) {
    }

    @Override
    public void setBackgroundResource(int resid) {
    }

    @Override
    public void setBackgroundDrawable(Drawable background) {
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            // 此处为得到焦点时的处理内容
            super.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.bg_input_text_selected));
        } else {
            // 此处为失去焦点时的处理内容
            super.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.bg_input_text_default));
        }
    }

    /**
     * Interface definition for a callback to be invoked when the password changed or is at the maximum length.
     */
    public interface OnPasswordChangedListener {

        /**
         * Invoked when the password changed.
         */
        public void onChanged(String psw);

        /**
         * Invoked when the password is at the maximum length.
         */
        public void onMaxLength(String psw);

    }

}
