package com.example.guest.organizer.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.guest.organizer.Constants;
import com.example.guest.organizer.R;
import com.example.guest.organizer.models.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskDetailFragment extends Fragment implements View.OnClickListener{
    @Bind(R.id.addTaskImageView) ImageView mAddTaskImageView;
    @Bind(R.id.taskDateTextView) TextView mTaskDateTextView;
    @Bind(R.id.taskListView) ListView mTaskListView;

    private String mDate;
    private FirebaseUser mUser;
    private DatabaseReference mTaskRef;

    public TaskDetailFragment() {
        // Required empty public constructor
    }

    public static TaskDetailFragment newInstance(String date) {
        TaskDetailFragment taskDetailFragment = new TaskDetailFragment();
        Bundle args = new Bundle();
        args.putString("date", date);
        taskDetailFragment.setArguments(args);
        return taskDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDate = getArguments().getString("date");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_detail, container, false);
        ButterKnife.bind(this, view);

        mTaskDateTextView.setText(mDate);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mTaskRef = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_DAYS).child(mUser.getUid()).child(mDate);

        mAddTaskImageView.setOnClickListener(this);

        mTaskRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> details = new ArrayList<String>();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String detail = snapshot.getValue(Task.class).getDetail();
                    details.add(detail);

                }

                ArrayAdapter<String> taskAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, details);
                mTaskListView.setAdapter(taskAdapter);
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v == mAddTaskImageView) {
            Intent intent = new Intent(getActivity(), NewTaskActivity.class);
            startActivity(intent);
        }
    }



}
