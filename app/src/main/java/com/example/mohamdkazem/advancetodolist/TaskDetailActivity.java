package com.example.mohamdkazem.advancetodolist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mohamdkazem.advancetodolist.Model.Task;

import java.io.Serializable;
import java.util.UUID;

public class TaskDetailActivity extends AppCompatActivity {
    private static final String TASK_RESIVE ="com.example.mohamdkazem.advancetodolist.task" ;
    private Task mTask;


    public static Intent newIntent(Context context, Task task){
        Intent intent=new Intent(context,TaskDetailActivity.class);
        intent.putExtra(TASK_RESIVE, (Serializable) task);

        return intent;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        mTask= (Task) getIntent().getSerializableExtra(TASK_RESIVE);
        Long id=mTask.getUserId();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.task_detail_activity,TaskDetailFragment.newInstance(id)).commit();

    }
}
