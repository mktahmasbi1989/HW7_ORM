package com.example.mohamdkazem.advancetodolist;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.mohamdkazem.advancetodolist.Model.Task;
import com.example.mohamdkazem.advancetodolist.Model.TasksRepository;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DoneTasksFragment extends Fragment {
    private static final String TAG_DIALOG = "com.example.mohamdkazem.advancetodolist.dialog_detail";
    private RecyclerView mRecyclerView;
    private DonJobAdaptor mDonJobAdaptor;

    public DoneTasksFragment() {
        // Required empty public constructor
    }

    public static DoneTasksFragment newInstance() {

        Bundle args = new Bundle();
        DoneTasksFragment fragment = new DoneTasksFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_tasks, container, false);
        mRecyclerView = view.findViewById(R.id.recycleView);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.btn_action);
        floatingActionButton.setVisibility(View.INVISIBLE);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        upDateUI();
        return view;
    }


    private void upDateUI() {

            List<Task> mListTask = TasksRepository.getInstance(getActivity()).getDoneTaskListORM(setUsersId.getUserId());
            if (mDonJobAdaptor == null) {
                mDonJobAdaptor = new DonJobAdaptor(mListTask);
                mRecyclerView.setAdapter(mDonJobAdaptor);
            } else
                mDonJobAdaptor.setTasks(mListTask);
            mDonJobAdaptor.notifyDataSetChanged();


    }

    @Override
    public void onResume() {
        super.onResume();
        upDateUI();
    }

    private class DonJobAdaptor extends RecyclerView.Adapter<DonJobHolder> {
        private List<Task> mTasks;

        DonJobAdaptor(List<Task> tasks) {
            mTasks = tasks;
        }
        void setTasks(List<Task> tasks) {
            mTasks= tasks;
        }

        @NonNull
        @Override
        public DonJobHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.task_holder, viewGroup, false);
            return new DonJobHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull DonJobHolder donJobHolder, int i) {
            Task task = mTasks.get(i);
            donJobHolder.bind(task);

        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }
    }

    private class DonJobHolder extends RecyclerView.ViewHolder {
        private TextView mTitle;
        private TextView mDetail;
        private TextView mFirstChar;
        private Task mTask;

        DonJobHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.job_title_holder);
            mDetail = itemView.findViewById(R.id.job_detail_holder);
            mFirstChar = itemView.findViewById(R.id.firstChar);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowTaskDetailDialog showTaskDetailDialog =ShowTaskDetailDialog.newInstance(setUsersId.getUserId());
                    showTaskDetailDialog.show(getFragmentManager(),TAG_DIALOG);
                }
            });
        }

        public void bind(Task task) {
            mTask = task;
            mTitle.setText(mTask.getMTitle());
            mDetail.setText(mTask.getMDetail());
            char first = mTask.getMTitle().charAt(0);
            String firsChar = String.valueOf(first);
            mFirstChar.setText(firsChar);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1)
                upDateUI();
        }
    }

}
