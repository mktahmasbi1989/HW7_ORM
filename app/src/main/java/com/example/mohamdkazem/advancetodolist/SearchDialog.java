package com.example.mohamdkazem.advancetodolist;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohamdkazem.advancetodolist.Model.Task;
import com.example.mohamdkazem.advancetodolist.Model.TasksRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class SearchDialog extends DialogFragment {

    private String TAG_DIALOG_DETAIL="detail";

    private RecyclerView mRecyclerView;
    private EditText editTextSearch;
    protected TaskAdaptor taskAdaptor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public SearchDialog() {
        // Required empty public constructor
    }

    public static SearchDialog newInstance() {
        Bundle args = new Bundle();
        SearchDialog fragment = new SearchDialog();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.search_layout, container, false);
        editTextSearch=view.findViewById(R.id.edit_search);
        mRecyclerView=view.findViewById(R.id.recycleView_search);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<Task> list = TasksRepository.getInstance(getContext()).getUserTaskListORM(setUsersId.getUserId());
                List<Task> searchTaskList = new ArrayList<>();
                if (s != null || !s.equals("")) {

                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getMTitle().contains(s) || list.get(i).getMDetail().contains(s)) {
                            searchTaskList.add(list.get(i));
                        }

                    }
                    taskAdaptor.setTasks(searchTaskList);
                    taskAdaptor.notifyDataSetChanged();

                } else
                {

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        upDateUI();

        return view;
    }
    private void upDateUI() {

        List<Task> mListTask = TasksRepository.getInstance(getActivity()).getUserTaskListORM((setUsersId.getUserId()));

        if (taskAdaptor == null) {
            taskAdaptor = new TaskAdaptor(mListTask);
            mRecyclerView.setAdapter(taskAdaptor);
        } else {
            taskAdaptor.setTasks(mListTask);
            taskAdaptor.notifyDataSetChanged();
        }
    }


    private class TaskAdaptor extends RecyclerView.Adapter<JobHolder>{

        private List<Task> mTasks;

        TaskAdaptor(List<Task> mListTask) {
            mTasks = mListTask;
        }
        public void setTasks(List<Task> tasks) {
            mTasks= tasks;
        }
        @NonNull
        @Override
        public JobHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater=LayoutInflater.from(viewGroup.getContext());
            View view = inflater.inflate(R.layout.task_holder, viewGroup, false);
            return new JobHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull JobHolder jobHolder, int i) {
            Task task = mTasks.get(i);
            jobHolder.bind(task);
        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }
    }


    private class JobHolder extends RecyclerView.ViewHolder{

        private TextView mTitle,mDetail,mFirstChar;
        private Task mTask;
        private Button mBtnShare;

        JobHolder(@NonNull final View itemView) {
            super(itemView);
            mTitle=itemView.findViewById(R.id.job_title_holder);
            mDetail=itemView.findViewById(R.id.job_detail_holder);
            mFirstChar=itemView.findViewById(R.id.firstChar);
            mBtnShare=itemView.findViewById(R.id.btn_share);

            mBtnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent reportIntent = new Intent(Intent.ACTION_SEND);
                    reportIntent.setType("text/plain");

                    String dateString = new SimpleDateFormat("yyyy/MM/dd").format(mTask.getMDate());
                    String doneTask = mTask.getMDone() ? ("Is Done") : ("Is Not Done");
                    String shareContent=mTask.getMTitle()+"  "+ mTask.getMDetail()+"  "+dateString+"  " +doneTask;
                    reportIntent.putExtra(Intent.EXTRA_TEXT, shareContent);
                    reportIntent.putExtra(Intent.EXTRA_SUBJECT, "my Task");
                    startActivity(Intent.createChooser(reportIntent, "Share Whit"));

                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s= String.valueOf(mTask.getTaskId());
                    Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();
                    ShowTaskDetailDialog showTaskDetailDialog =ShowTaskDetailDialog.newInstance(mTask.getTaskId());
                    showTaskDetailDialog.show(getFragmentManager(),TAG_DIALOG_DETAIL);
                }
            });
        }
        public void bind(Task task){
            mTask = task;
            mTitle.setText(mTask.getMTitle());
            mDetail.setText(mTask.getMDetail());
            char first= mTask.getMTitle().charAt(0);
            String firsChar= String.valueOf(first);
            mFirstChar.setText(firsChar);

        }
    }

}
