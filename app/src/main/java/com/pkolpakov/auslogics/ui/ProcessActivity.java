package com.pkolpakov.auslogics.ui;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.pkolpakov.auslogics.models.ProcessInfo;
import com.pkolpakov.auslogics.R;
import com.pkolpakov.auslogics.ui.fragments.ProcessFragment;
import com.pkolpakov.auslogics.ui.fragments.ProcessListFragment;

public class ProcessActivity extends AppCompatActivity implements ProcessNavigator {
    private static final String PROCESS_LIST_TAG = "process_list";
    private static final String PROCESS_TAG = "process";

    private FragmentManager.OnBackStackChangedListener onBackStackChangedListener = () ->{
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(getSupportFragmentManager().getBackStackEntryCount() > 0);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_list);
        if(getSupportFragmentManager().getFragments().size() < 1){
            getSupportFragmentManager().beginTransaction().replace(R.id.container,new ProcessListFragment(),PROCESS_LIST_TAG).commit();
        }
        getSupportFragmentManager().addOnBackStackChangedListener(onBackStackChangedListener);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(getSupportFragmentManager().getBackStackEntryCount() > 0);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getSupportFragmentManager().popBackStack();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSupportFragmentManager().removeOnBackStackChangedListener(onBackStackChangedListener);
    }

    public void showProcessFragment(ProcessInfo processInfo){
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, ProcessFragment.newInstance(processInfo),PROCESS_TAG).
                addToBackStack(PROCESS_TAG).
                commit();
    }
}
