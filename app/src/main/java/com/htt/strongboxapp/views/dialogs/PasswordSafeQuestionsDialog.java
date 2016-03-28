package com.htt.strongboxapp.views.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.htt.strongboxapp.R;


/**
 * Created by Administrator on 2016/3/28.
 */
public class PasswordSafeQuestionsDialog {
    private ListView lvQuestions;
    private String[] questions;
    private AlertDialog mAlertDialog;
    private Window mAlertDialogWindow;
    private Context context;

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

        lvQuestions= (ListView) contentView.findViewById(R.id.lv_questions);
        questions=context.getResources().getStringArray(R.array.passowrd_safe_question);
        PasswordSafeQuestionsAdapter adapter=new PasswordSafeQuestionsAdapter(questions);
        lvQuestions.setAdapter(adapter);
    }

    public void show(){
        if(!mAlertDialog.isShowing()){
            mAlertDialog.show();
        }
    }

    public void dismisss(){
        mAlertDialog.dismiss();
    }

    public static final class PasswordSafeQuestionsAdapter extends BaseAdapter{
        private String[] questions;

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

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            PasswordSafeQuestionsViewHolder viewHolder=null;
            if(convertView==null){
                viewHolder=new PasswordSafeQuestionsViewHolder();
                convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_password_safe_question_item,parent,false);
                viewHolder.tvQuestionContent=(TextView)convertView.findViewById(R.id.tv_question_content);
                convertView.setTag(viewHolder);
            }else{
                viewHolder= (PasswordSafeQuestionsViewHolder) convertView.getTag();
            }
            viewHolder.tvQuestionContent.setText(questions[position]);
            return convertView;
        }

        public static final class PasswordSafeQuestionsViewHolder{
            TextView tvQuestionContent;
        }
    }
}
