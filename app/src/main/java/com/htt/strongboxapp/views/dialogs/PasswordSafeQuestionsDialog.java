package com.htt.strongboxapp.views.dialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.htt.strongboxapp.R;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by Administrator on 2016/3/28.
 */
public class PasswordSafeQuestionsDialog {
    private MaterialDialog dialog;
    private ListView lvQuestions;
    private String[] questions;

    public PasswordSafeQuestionsDialog(Context context){
        dialog=new MaterialDialog(context);
        View view=LayoutInflater.from(context).inflate(R.layout.dialog_password_safe_questions,null);
        lvQuestions= (ListView) view.findViewById(R.id.lv_questions);
        questions=context.getResources().getStringArray(R.array.passowrd_safe_question);
        PasswordSafeQuestionsAdapter adapter=new PasswordSafeQuestionsAdapter(questions);
        lvQuestions.setAdapter(adapter);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
    }

    public void show(){
        dialog.show();
    }

    public void dismisss(){
        dialog.dismiss();
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
