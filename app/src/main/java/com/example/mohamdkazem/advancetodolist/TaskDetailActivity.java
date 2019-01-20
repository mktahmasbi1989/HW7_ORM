package com.example.mohamdkazem.advancetodolist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mohamdkazem.advancetodolist.Model.Task;
import com.example.mohamdkazem.advancetodolist.Model.TasksRepository;

import java.io.Serializable;
import java.util.UUID;

public class TaskDetailActivity extends AppCompatActivity {
    private static final String TASK_RESIVE ="com.example.mohamdkazem.advancetodolist.task" ;
    private Task mTask;
    private Long userId;


    public static Intent newIntent(Context context, Long id){
        Intent intent=new Intent(context,TaskDetailActivity.class);
        intent.putExtra(TASK_RESIVE, id);
        return intent;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        userId=getIntent().getLongExtra(TASK_RESIVE,0);
//        userId=setUsersId.getUserId();
        mTask= TasksRepository.getInstance(getApplicationContext()).getTaskORm(userId);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.task_detail_activity,TaskDetailFragment.newInstance(userId)).commit();

    }
}
