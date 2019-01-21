package com.example.mohamdkazem.advancetodolist;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mohamdkazem.advancetodolist.Model.Task;
import com.example.mohamdkazem.advancetodolist.Model.TasksRepository;
import com.example.mohamdkazem.advancetodolist.utils.PictureUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ShowTaskDetailDialog extends DialogFragment {
    private static final String ARG_TASK ="com.example.mohamdkazem.advancetodolist.task" ;
    private static final int REQ_DETAIL =10 ;
    private Task mTask;
    private TextView mTextTitle,mTextTime,mTextDetail,mTextDate;
    private Button mBtnEdit;
    private Long taskId;
    private File mPhotoFile;
    private ImageView mImgTask;



    public static ShowTaskDetailDialog newInstance(Long taskId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TASK, taskId);
        ShowTaskDetailDialog fragment = new ShowTaskDetailDialog();
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        taskId=getArguments().getLong(ARG_TASK);
        mTask= TasksRepository.getInstance(getActivity()).getTaskORm(taskId);
        mPhotoFile = TasksRepository.getInstance(getActivity()).getPhotoFile(mTask);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.task_detail_dialog,container,false);
        initDialog(view);
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mImgTask.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(
                    mPhotoFile.getPath(),
                    getActivity());

            mImgTask.setImageBitmap(bitmap);
        }

        mTextTitle.setText(mTask.getMTitle());
        mTextDetail.setText(mTask.getMDetail());

//        set Date TextView
        Date date= mTask.getMDate();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String formatDate=simpleDateFormat.format(date);
        mTextDate.setText(formatDate);

//      set Time TextView
        SimpleDateFormat simpleDateFormat1=new SimpleDateFormat( "h:mm a");
        String formatTime=simpleDateFormat1.format(date);
        mTextTime.setText(formatTime);

        mBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                Intent intent=TaskDetailActivity.newIntent(getActivity(),taskId);
//                startActivity(intent);
                startActivityForResult(intent,REQ_DETAIL);
            }
        });



        return view;


    }

    private void initDialog(View view) {
        mTextTitle=view.findViewById(R.id.text_title_dialog);
        mTextDetail=view.findViewById(R.id.text_detail_dialog);
        mTextDate=view.findViewById(R.id.text_date_dialog);
        mTextTime=view.findViewById(R.id.text_time_dialog);
        mBtnEdit=view.findViewById(R.id.btn_edit_dialog);
        mImgTask=view.findViewById(R.id.img_view_task);
    }
}
