package com.pkolpakov.auslogics.ui.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pkolpakov.auslogics.ui.ProcessAdapter;
import com.pkolpakov.auslogics.ui.ProcessNavigator;
import com.pkolpakov.auslogics.viewmodels.ProcessListViewModel;
import com.pkolpakov.auslogics.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Павел on 22.03.2018.
 */

public class ProcessListFragment extends Fragment {
    @BindView(R.id.recycler_process)
    RecyclerView recyclerProcess;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_process_list,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        ProcessListViewModel processListViewModel = ViewModelProviders.of(getActivity()).get(ProcessListViewModel.class);
        processListViewModel.getProcessListLiveData().observe(this, processList -> {
            ProcessAdapter processAdapter = new ProcessAdapter(processList);
            processAdapter.setOnProcessClickLister(processInfo -> {
            if(getActivity() instanceof ProcessNavigator){
                ((ProcessNavigator) getActivity()).showProcessFragment(processInfo);
            }
            });
            recyclerProcess.setAdapter(processAdapter);
        });

    }
}
