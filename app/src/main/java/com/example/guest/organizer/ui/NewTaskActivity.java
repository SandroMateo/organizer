package com.example.guest.organizer.ui;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.guest.organizer.Constants;
import com.example.guest.organizer.R;
import com.example.guest.organizer.models.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewTaskActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.typeSpinner) Spinner mTypeSpinner;
    @Bind(R.id.priorityCheckBox) CheckBox mPriorityCheckBox;
    @Bind(R.id.exploreCheckBox) CheckBox mExploreCheckBox;
    @Bind(R.id.inspirationCheckBox) CheckBox mInspirationCheckBox;
    @Bind(R.id.collectionsSpinner) Spinner mCollectionsSpinner;
    @Bind(R.id.editText2) EditText mNewCollectionEditText;
    @Bind(R.id.editText) EditText mTaskDetail;
    @Bind(R.id.button) Button mAddTask;

    private String mDate;
    private DatabaseReference mTaskRef;
    private FirebaseUser mUser;
    private String[] mTypes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        ButterKnife.bind(this);

        Resources res = getResources();
        mDate = getIntent().getStringExtra("date");
        mAddTask.setOnClickListener(this);

        mTypes = res.getStringArray(R.array.types);
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(NewTaskActivity.this, android.R.layout.simple_spinner_item, mTypes);
        mTypeSpinner.setAdapter(mAdapter);
    }
    @Override
    public void onClick(View v){
        if (v == mAddTask){
            mUser = FirebaseAuth.getInstance().getCurrentUser();
            String uid = mUser.getUid();
            String taskDetail = mTaskDetail.getText().toString();
            String type = mTypeSpinner.getSelectedItem().toString();
            isValid(taskDetail, mTaskDetail);
            Task newTask = new Task(taskDetail, type, mDate);
            String collection = "None";
            if(mCollectionsSpinner.getSelectedItem() != null) {
                collection = mCollectionsSpinner.getSelectedItem().toString();
            }
            String newCollection = mNewCollectionEditText.getText().toString();
            if (mPriorityCheckBox.isSelected()){
                String priority = mPriorityCheckBox.getText().toString();
                newTask.addSignifier(priority);
            }
            if (mExploreCheckBox.isSelected()){
                String explore = mExploreCheckBox.getText().toString();
                newTask.addSignifier(explore);
            }
            if (mInspirationCheckBox.isSelected()){
                String inspiration = mInspirationCheckBox.getText().toString();
                newTask.addSignifier(inspiration);
            }
            if(!collection.equals("None")){
                newTask.setCollection(collection);
            } else if (collection.equals("None") && !newCollection.equals("")){
                newTask.setCollection(newCollection);
                DatabaseReference mCollectionRef = FirebaseDatabase
                        .getInstance()
                        .getReference(Constants.FIREBASE_CHILD_COLLECTIONS)
                        .child(uid);

                DatabaseReference tempRef = mCollectionRef.push();
                String tempId = tempRef.getKey();
                newTask.setPushId(tempId);
                tempRef.setValue(newTask);

            } else if (!collection.equals("None") && !newCollection.equals("")){
                mNewCollectionEditText.setError("Select only one option");
            }


            mTaskRef = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_DAYS).child(uid).child(mDate);

            DatabaseReference pushRef = mTaskRef.push();
            String pushId = pushRef.getKey();
            newTask.setPushId(pushId);
            pushRef.setValue(newTask);

            Intent intent = new Intent(NewTaskActivity.this, MainActivity.class);
            intent.putExtra("date", mDate);
            startActivity(intent);
        }
    }
    private boolean isValid(String text, EditText editText){
        if (text.equals("")) {
            editText.setError("Field cannot be blank");
            return false;
        }
        return true;
    }
}
