package com.example.guest.organizer.ui;

import android.content.Intent;
import android.provider.SyncStateContract;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.guest.organizer.Constants;
import com.example.guest.organizer.R;
import com.example.guest.organizer.adapters.DatePagerAdapter;
import com.example.guest.organizer.models.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    @Bind(R.id.viewPager) ViewPager mViewPager;
    @Bind(R.id.pagerHeader) PagerTabStrip mPagerHeader;

    private FirebaseUser mUser;
    private DatabaseReference mDatesRef;
    private DatePagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        final String today = dateFormat.format(date);
        final String tomorrow = dateFormat.format((date.getTime() + 86400000));

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatesRef = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_DAYS).child(mUser.getUid());

        mDatesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> days = new ArrayList<String>();

                Log.d("Main Activity", "inside datachange");
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String key = snapshot.getKey();
                    if (key.equals(today) || key.equals(tomorrow)){
                        days.add(key);
                    }

                }
                if(!days.contains(today)) {
                    days.add(today);
                }
                if(!days.contains(tomorrow)) {
                    days.add(tomorrow);
                }
                int startDate = days.indexOf(today);
                adapterViewPager = new DatePagerAdapter(getSupportFragmentManager(), days);
                mViewPager.setAdapter(adapterViewPager);
                mViewPager.setCurrentItem(startDate);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
