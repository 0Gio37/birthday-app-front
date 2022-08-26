package com.georges.android.birthday_app_front.adaptater;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.georges.android.birthday_app_front.models.Birthday;

import java.util.List;

public class BirthdayAdaptater extends RecyclerView.Adapter<BirthdayAdaptater.ViewHolder> {

    private Context mContext;
    private List<Birthday> mBirthdays;

    public BirthdayAdaptater(Context mContext, List<Birthday> mBirthdays){
        this.mContext = mContext;
        this.mBirthdays = mBirthdays;
    }

    @NonNull
    @Override
    public BirthdayAdaptater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BirthdayAdaptater.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
