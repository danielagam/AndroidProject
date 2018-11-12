package com.danielshimon.android_project.model.model.backend;

import com.danielshimon.android_project.model.model.datasource.Firebase_DBManager;

public final class BackendFactory {
    //for the friends class
    public static final class Friend {private Friend(){}}

    private static Backend backend = null;

    public static Backend getBackend() {
        if (backend == null)
            backend = new Firebase_DBManager(new Friend());
        return backend;
    }
}
