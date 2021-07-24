package com.v24.recycle.Adopter;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

public class CustomListAdapter extends ArrayAdapter {
    public CustomListAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }
}
