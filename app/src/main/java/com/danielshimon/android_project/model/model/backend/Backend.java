package com.danielshimon.android_project.model.model.backend;

import android.content.Context;

import com.danielshimon.android_project.model.model.entities.Travel;

public interface Backend {
    void addRequest(Travel travel, final Context context);
}
