package com.example.mohamdkazem.advancetodolist;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mohamdkazem.advancetodolist.Model.Task;
import com.example.mohamdkazem.advancetodolist.Model.TasksRepository;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskDetailFragment extends Fragment {

    private static final String ARG_JOB_ID = "com.example.mohamdkazem.advancetodolist.jobId";
    private static final String DATE_DIALOG ="com.example.mohamdkazem.advancetodolist.date dialog" ;
    private static final int REQ_COD_DATE = 11;
    private static final int REQ_COD_TIME =12 ;
    private static final String TIME_DIALOG ="com.example.mohamdkazem.advancetodolist.time dialog" ;
    private static final String ARG_USER_ID = "user_Id";

    private Button mBtnEdit,mBtnDelete,mBtnDone;
    private TextView mTextViewDate,mTextViewTime;
    private EditText mTextViewDescribtion,mTextTextViewTitle;
    private Task mTask;
    private  Long userId;



    public TaskDetailFragment() {
        // Required empty public constructor
    }

    public static TaskDetailFragment newInstance(Long id) {

        Bundle args = new Bundle();
        args.putLong(ARG_USER_ID,id);
        TaskDetailFragment fragment = new TaskDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        userId=getArguments().getLong(ARG_USER_ID);
        mTask=TasksRepository.getInstance(getActivity()).getTaskORm(userId);
//        UUID jobId= (UUID) getArguments().getSerializable(ARG_JOB_ID);
//            mTask = TasksRepository.getInstance(getActivity()).getTask(jobId);

    }


    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_task_detail, container, false);
        init(view);
        mTextTextViewTitle.setText(mTask.getMTitle());
        mTextViewDescribtion.setText(mTask.getMDetail());
        Date date= mTask.getMDate();
        setDateInTextView(date);

        setTimeInTextView(date);

        if (mTask.getMDone()){
            mBtnDone.setVisibility(View.INVISIBLE);
        }

//        show DatePicker Dialog To set Date
        mTextViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=DatePickerDialog.newInstance(mTask.getMDate());
                datePickerDialog.setTargetFragment(TaskDetailFragment.this,REQ_COD_DATE);
                datePickerDialog.show(getFragmentManager(),DATE_DIALOG);
            }
        });

//        show TimePicker Dialog To set Time
        mTextViewTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragment timePickerFragment=TimePickerFragment.newInstance(mTask.getMDate());
                timePickerFragment.setTargetFragment(TaskDetailFragment.this,REQ_COD_TIME);
                timePickerFragment.show(getFragmentManager(),TIME_DIALOG);
            }
        });

        mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog=new AlertDialog.Builder(getActivity()).setTitle(getString(R.string.delete))
                        .setMessage(getString(R.string.deleteAlla))
                        .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


//                                    TasksRepository.getInstance(getActivity()).delete(mTask);
                                    TasksRepository.getInstance(getActivity()).removeTask(mTask);
                                    Intent intent=new Intent(ToDoListActivity.newIntent(getActivity(), setUsersId.getUserId()));
                                    startActivity(intent);



                            }
                        }).setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).create();
                    alertDialog.show();

            }
        });
        mBtnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            mTask.setMDone(true);
            TasksRepository.getInstance(getActivity()).setDoneTaskORM(mTask);
            Intent intent=new Intent(ToDoListActivity.newIntent(getActivity(),setUsersId.getUserId()));
            startActivity(intent);


            }
        });

        mBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mTask.setDetail(mTextViewDescribtion.getText().toString());
//                TasksRepository.getInstance(getActivity()).upDateTask(mTask,mTask.getId());
//                updateFragments();
                mTask.setMTitle(mTextTextViewTitle.getText().toString());
                mTask.setMDetail(mTextViewDescribtion.getText().toString());

                    TasksRepository.getInstance(getActivity()).upDate(mTask);
                    Intent intent = new Intent(ToDoListActivity.newIntent(getActivity(), (long) ToDoListActivity.mId));
                    startActivity(intent);
//                getActivity().getSupportFragmentManager().getFragments().get(1).onActivityResult(1, Activity.RESULT_OK,new Intent());
//                getActivity().getSupportFragmentManager().getFragments().get(0).onActivityResult(0,Activity.RESULT_OK,new Intent());
//                getActivity().getSupportFragmentManager().popBackStack();
            }

        });
        return view;
    }

    private void updateFragments() {
        getActivity().getSupportFragmentManager().getFragments().get(1).onActivityResult(1, Activity.RESULT_OK,new Intent());
        getActivity().getSupportFragmentManager().getFragments().get(0).onActivityResult(0,Activity.RESULT_OK,new Intent());
    }


    private void init(View view) {
        mBtnDelete=view.findViewById(R.id.btn_delete);
        mBtnDone=view.findViewById(R.id.btn_done);
        mBtnEdit =view.findViewById(R.id.btn_edite);
        mTextViewDescribtion=view.findViewById(R.id.textView_description);
        mTextViewDate=view.findViewById(R.id.textView_date);
        mTextViewTime=view.findViewById(R.id.textView_time);
        mTextTextViewTitle=view.findViewById(R.id.textView_title_detail);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==REQ_COD_DATE){
            Date date= (Date) data.getSerializableExtra(DatePickerDialog.EXTRA_DATE);
            mTask.setMDate(date);
            setDateInTextView(date);
            TasksRepository.getInstance(getActivity()).upDate(mTask);
        }
        if (requestCode==REQ_COD_TIME){
            Date mdate= (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME_TAG);
            mTask.setMDate(mdate);
            setTimeInTextView(mdate);
            TasksRepository.getInstance(getActivity()).upDate(mTask);

        }

    }

    private void setTimeInTextView(Date mdate) {
        SimpleDateFormat simpleDateFormat1=new SimpleDateFormat( "h:mm a");
        String formatTime=simpleDateFormat1.format(mdate);
        mTextViewTime.setText(formatTime);
    }

    private void setDateInTextView(Date date) {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String formatDate=simpleDateFormat.format(date);
        mTextViewDate.setText(formatDate);
    }
}
