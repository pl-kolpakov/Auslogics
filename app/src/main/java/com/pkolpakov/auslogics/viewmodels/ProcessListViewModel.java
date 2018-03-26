package com.pkolpakov.auslogics.viewmodels;

import android.app.ActivityManager;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import com.pkolpakov.auslogics.models.ProcessInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Павел on 22.03.2018.
 */

public class ProcessListViewModel extends AndroidViewModel {
    private ActivityManager activityManager;
    private MutableLiveData<List<ProcessInfo>> processListLiveData = new MutableLiveData<>();
    public ProcessListViewModel(@NonNull Application application) {
        super(application);
        activityManager = (ActivityManager) application.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
    }
    public LiveData<List<ProcessInfo>> getProcessListLiveData() {
        if (processListLiveData.getValue() == null) {
            List<ProcessInfo> processInfoList = new ArrayList<>();
            List<ActivityManager.RunningServiceInfo> runningServiceInfoList = activityManager.getRunningServices(Integer.MAX_VALUE);
            for(ActivityManager.RunningServiceInfo serviceInfo:runningServiceInfoList){
                processInfoList.add(new ProcessInfo(serviceInfo.process,serviceInfo.service.getPackageName()));
            }
            processListLiveData.setValue(processInfoList);
        }
        return processListLiveData;
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        activityManager = null;
    }
}
