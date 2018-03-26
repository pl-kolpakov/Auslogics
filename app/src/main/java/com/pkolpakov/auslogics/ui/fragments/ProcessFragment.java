package com.pkolpakov.auslogics.ui.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.pkolpakov.auslogics.models.ProcessInfo;
import com.pkolpakov.auslogics.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Павел on 22.03.2018.
 */

public class ProcessFragment extends Fragment {
    private static final String PROCESS_INFO_KEY = "process_info_key";
    @BindView(R.id.image_icon)
    ImageView imageViewIcon;
    @BindView(R.id.text_name)
    TextView textViewName;

    private ProcessInfo processInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_process,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        String processJson = null;
        if(getArguments() != null){
            processJson = getArguments().getString(PROCESS_INFO_KEY,null);
        }
        if(processJson != null){
            ProcessInfo processInfo = new Gson().fromJson(processJson,ProcessInfo.class);
            if(processInfo != null) {
                this.processInfo = processInfo;
                textViewName.setText(processInfo.getProcessName());
                try {
                    imageViewIcon.setImageDrawable(getContext().getApplicationContext().getPackageManager().
                            getApplicationIcon(processInfo.getProcessPackage()));
                    imageViewIcon.setVisibility(View.VISIBLE);
                } catch (PackageManager.NameNotFoundException e) {
                    imageViewIcon.setVisibility(View.GONE);
                }
            }
        }
    }

    @OnClick(R.id.button_info)
    void showInfo() {
        if (processInfo != null) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", processInfo.getProcessPackage(), null);
            intent.setData(uri);
            startActivity(intent);
        }
    }

    public static ProcessFragment newInstance(ProcessInfo processInfo) {
        ProcessFragment processFragment = new ProcessFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PROCESS_INFO_KEY, new Gson().toJson(processInfo));
        processFragment.setArguments(bundle);
        return processFragment;
    }
}
