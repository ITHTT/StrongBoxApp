package com.htt.strongboxapp.views.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.htt.strongboxapp.R;
import com.htt.strongboxapp.utils.ToastMsgUtil;
import com.htt.strongboxapp.views.widgets.SmoothCheckBox;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/3/28.
 */
public class PasswordSafeQuestionsDialog implements AdapterView.OnItemClickListener,View.OnClickListener{
    private ListView lvQuestions;
    private String[] questions;
    private AlertDialog mAlertDialog;
    private Window mAlertDialogWindow;
    private Context context;
    private TextView tvCancel;
    private TextView tvOk;
    private String currentSelectedQuestion;
    private String selectedQuestion;
    private List<String> selectedQuestions;
    private PasswordSafeQuestionsAdapter adapter;
    private OnSelecteQuestionListener onSelecteQuestionListener;

    public PasswordSafeQuestionsDialog(Context context){
        this.context=context;
        mAlertDialog=new AlertDialog.Builder(context).create();
        mAlertDialog.show();
        mAlertDialog.getWindow()
                .clearFlags(
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        mAlertDialog.getWindow()
                .setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_MASK_STATE);

        mAlertDialogWindow = mAlertDialog.getWindow();

        View contentView = LayoutInflater.from(context)
                .inflate(
                        R.layout.dialog_password_safe_questions,
                        null);
        contentView.setFocusable(true);
        contentView.setFocusableInTouchMode(true);

        mAlertDialogWindow.setBackgroundDrawableResource(
                R.drawable.dialog_window_background);
        mAlertDialog.setContentView(contentView);

        selectedQuestions=new ArrayList<String>(3);
        lvQuestions= (ListView) contentView.findViewById(R.id.lv_questions);
        questions=context.getResources().getStringArray(R.array.passowrd_safe_question);
        adapter=new PasswordSafeQuestionsAdapter(questions);
        adapter.setSelectedQuestions(selectedQuestions);
        lvQuestions.setAdapter(adapter);
        lvQuestions.setOnItemClickListener(this);


        tvCancel=(TextView)contentView.findViewById(R.id.tv_cancel);
        tvOk=(TextView)contentView.findViewById(R.id.tv_ok);

        tvCancel.setOnClickListener(this);
        tvOk.setOnClickListener(this);

        setDialogWindowAttrs();
    }

    public void show(){
        if(!mAlertDialog.isShowing()){
            mAlertDialog.show();
        }
        if(adapter!=null){
            adapter.notifyDataSetChanged();
        }
    }

    public void dismisss(){
        mAlertDialog.dismiss();
    }

    protected void setDialogWindowAttrs() {
        // TODO Auto-generated method stub

        Activity activity= (Activity) context;
        WindowManager windowManager=activity.getWindowManager();
        Display d = windowManager.getDefaultDisplay();
        mAlertDialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams p=mAlertDialogWindow.getAttributes();
        //p.height = (int) (d.getHeight() * 0.5); // 高度设置为屏幕的0.4
        p.width = (int) (0.9*d.getWidth());
        mAlertDialogWindow.setAttributes(p);
    }

    public void setCurrentSelectedQuestion(String question){
        currentSelectedQuestion=question;
        selectedQuestion=currentSelectedQuestion;
        adapter.setCurrentSelectedQuestion(question);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(!adapter.isSelectedQuestion(questions[position])) {
            selectedQuestion=questions[position];
            adapter.setCurrentSelectedQuestion(selectedQuestion);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.tv_cancel:
                dismisss();
                break;
            case R.id.tv_ok:
                if(!TextUtils.isEmpty(selectedQuestion)){
                    if(onSelecteQuestionListener!=null){
                        if(TextUtils.isEmpty(currentSelectedQuestion)){
                            selectedQuestions.add(selectedQuestion);
                        }else if(!selectedQuestion.equals(currentSelectedQuestion)){
                            selectedQuestions.remove(currentSelectedQuestion);
                            selectedQuestions.add(selectedQuestion);
                        }
                        onSelecteQuestionListener.onSelectedQuestion(selectedQuestion);
                    }
                    dismisss();
                }else{
                    ToastMsgUtil.toastMsg(context,"请选择密保问题");
                }
                break;
        }
    }

    public void setOnSelecteQuestionListener(OnSelecteQuestionListener l){
        this.onSelecteQuestionListener=l;
    }

    public interface OnSelecteQuestionListener{
        void onSelectedQuestion(String question);
    }

    public static final class PasswordSafeQuestionsAdapter extends BaseAdapter{
        /**所有问题列表*/
        private String[] questions;
        /**已选择的问题*/
        private List<String> selectedQuestions;
        /**当前选则的问题*/
        private String currentSeletedQuestion;

        public PasswordSafeQuestionsAdapter(String[] args){
            questions=args;
        }

        @Override
        public int getCount() {
            return questions.length;
        }

        @Override
        public Object getItem(int position) {
            return questions[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void setSelectedQuestions(List<String>questions){
            this.selectedQuestions=questions;
        }

        public void setCurrentSelectedQuestion(String question){
            this.currentSeletedQuestion=question;
            notifyDataSetChanged();
        }


        public boolean isSelectedQuestion(String question){
            if(selectedQuestions!=null&&selectedQuestions.size()>0&&selectedQuestions.contains(question)){
                return true;
            }
            return false;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            PasswordSafeQuestionsViewHolder viewHolder=null;
            if(convertView==null){
                viewHolder=new PasswordSafeQuestionsViewHolder();
                convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_password_safe_question_item,parent,false);
                viewHolder.tvQuestionContent=(TextView)convertView.findViewById(R.id.tv_question_content);
                viewHolder.smoothCheckBox=(SmoothCheckBox)convertView.findViewById(R.id.scb);
                convertView.setTag(viewHolder);
            }else{
                viewHolder= (PasswordSafeQuestionsViewHolder) convertView.getTag();
            }
            viewHolder.tvQuestionContent.setText(questions[position]);
            viewHolder.smoothCheckBox.setEnabled(false);
            viewHolder.smoothCheckBox.setCheckedColor(Color.parseColor("#00ccff"));
            if(selectedQuestions!=null&&selectedQuestions.contains(questions[position])){
                viewHolder.smoothCheckBox.setCheckedColor(Color.parseColor("#ff0000"));
                viewHolder.smoothCheckBox.setChecked(true);
            }else{
                viewHolder.smoothCheckBox.setChecked(false);
            }
            if(!TextUtils.isEmpty(currentSeletedQuestion)&&currentSeletedQuestion.equals(questions[position])){
                viewHolder.smoothCheckBox.setCheckedColor(Color.parseColor("#00ccff"));
                viewHolder.smoothCheckBox.setChecked(true);
            }
            return convertView;
        }

        public static final class PasswordSafeQuestionsViewHolder{
            TextView tvQuestionContent;
            SmoothCheckBox smoothCheckBox;
        }
    }
}
