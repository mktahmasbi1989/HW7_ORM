package com.example.mohamdkazem.advancetodolist;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mohamdkazem.advancetodolist.Model.Task;
import com.example.mohamdkazem.advancetodolist.Model.TasksRepository;
import com.example.mohamdkazem.advancetodolist.Model.Users;

import java.util.List;


/**
 * A simple {@link } subclass.
 */
public class AllTasksFragment extends Fragment {

    private static final String TAG_DIALOG_DETAIL ="com.example.mohamdkazem.advancetodolist.task detail" ;
    private RecyclerView mRecyclerView;
    private JobAdaptor mJobAdaptor;
    private FloatingActionButton mFloatingActionButton;
    private TextView mTextViewNoTask;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public AllTasksFragment() {
        // Required empty public constructor
    }

    public static AllTasksFragment newInstance() {

        Bundle args = new Bundle();
        AllTasksFragment fragment = new AllTasksFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_all_tasks, container, false);
        init(view);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddTaskFragment addTaskFragment=AddTaskFragment.newInstance();
                addTaskFragment.show(getFragmentManager(),TAG_DIALOG_DETAIL);
            }
        });
        upDateUI();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.delete_all_menu:
                deleteAllDialog();

                return  true;
            case R.id.exit_menu:
                ExitDialog();
                return  true;
            default:
                return  super.onOptionsItemSelected(item);

        }
    }

    private void deleteAllDialog() {
        AlertDialog alertDialog=new AlertDialog.Builder(getActivity()).setTitle(getString(R.string.deleteAll))
                .setMessage("مطمنی میخوای همو را حذف کنی؟")
                .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TasksRepository.getInstance(getActivity()).deleteAllTasks();
                        upDateUI();
                        getActivity().getSupportFragmentManager().getFragments().get(1).onActivityResult(1,Activity.RESULT_OK,new Intent());
                    }
                }).setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();
        alertDialog.show();
    }

    private void ExitDialog() {
        AlertDialog alertDialog=new AlertDialog.Builder(getActivity()).setTitle(getString(R.string.Exit))
                .setMessage("مطمئنی میخوای خارج بشی؟")
                .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        exit();
                        getActivity().finish();
                    }
                }).setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();
        alertDialog.show();
    }

    private void exit() {
        Users users=TasksRepository.getInstance(getActivity()).getUser(getString(R.string.fakeUsername),getString(R.string.fakePassWord));
        if (users.getUserId()==ToDoListActivity.mId){
            TasksRepository.getInstance(getActivity()).deleteAllTasks();
            getActivity().finish();
        }else getActivity().finish();
    }


    private void init(View view) {
        mRecyclerView=view.findViewById(R.id.recycleView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mFloatingActionButton=view.findViewById(R.id.btn_action);
        mTextViewNoTask=view.findViewById(R.id.no_task_textview);
    }

    private void upDateUI() {

            List<Task> mListTask = TasksRepository.getInstance(getActivity()).getTaskListORM();
            if (mListTask.size() == 0) {
                mTextViewNoTask.setVisibility(View.VISIBLE);
            }
            if (mJobAdaptor == null) {
                mJobAdaptor = new JobAdaptor(mListTask);
                mRecyclerView.setAdapter(mJobAdaptor);
            } else
                mJobAdaptor.setTasks(mListTask);
            mJobAdaptor.notifyDataSetChanged();

    }

    private class JobAdaptor extends RecyclerView.Adapter<JobHolder>{

        private List<Task> mTasks;
        JobAdaptor(List<Task> mListTask) {
            mTasks = mListTask;
        }
        public void setTasks(List<Task> tasks) {
            mTasks= tasks;
        }

        @NonNull
        @Override
        public JobHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater=LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.task_holder, viewGroup, false);
            return new JobHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull JobHolder jobHolder, int i) {
            Task task = mTasks.get(i);
            jobHolder.bind(task);
            mTextViewNoTask.setVisibility(View.GONE);

        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }
    }

    private class JobHolder extends RecyclerView.ViewHolder{
        private TextView mTitle,mDetail,mFirstChar;
        private Task mTask;
        JobHolder(@NonNull final View itemView) {
            super(itemView);
            mTitle=itemView.findViewById(R.id.job_title_holder);
            mDetail=itemView.findViewById(R.id.job_detail_holder);
            mFirstChar=itemView.findViewById(R.id.firstChar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowTAskDetailDialog showTAskDetailDialog=ShowTAskDetailDialog.newInstance(mTask);
                    showTAskDetailDialog.show(getFragmentManager(),TAG_DIALOG_DETAIL);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==0){
            upDateUI();
        }
    }


}
