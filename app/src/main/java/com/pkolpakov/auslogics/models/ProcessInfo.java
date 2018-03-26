package com.pkolpakov.auslogics.models;

/**
 * Created by Павел on 26.03.2018.
 */

public class ProcessInfo {
    private String processName;
    private String processPackage;

    public ProcessInfo(String processName, String processPackage) {
        this.processName = processName;
        this.processPackage = processPackage;
    }

    public String getProcessName() {
        return processName;
    }

    public String getProcessPackage() {
        return processPackage;
    }
}
